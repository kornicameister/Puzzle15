package org.kornicameister.sise.puzzle.stratagies;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;
import org.kornicameister.sise.core.strategies.BFSStrategy;
import org.kornicameister.sise.core.strategies.UnvisitedAccessibleStrategy;
import org.kornicameister.sise.puzzle.builder.PuzzleNeighborsBuilder;
import org.kornicameister.sise.puzzle.edge.BFSEdge;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BFSPuzzleStrategy extends BFSStrategy {
    private static final Logger LOGGER = Logger.getLogger(BFSPuzzleStrategy.class.getName());
    protected final NodeAccessibleStrategy strategy;
    protected PuzzleNeighborsBuilder neighborsBuilder;

    public BFSPuzzleStrategy() {
        LOGGER.setLevel(Level.INFO);
        this.strategy = new UnvisitedAccessibleStrategy();
    }

    @Override
    public void init(List<GraphNode> nodes) {
        super.init(nodes);
        this.neighborsBuilder = new PuzzleNeighborsBuilder(nodes);
    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder(super.getReport());
        sb.append("Generated nodes:\t").append(this.neighborsBuilder.getGeneratedNodes()).append("\n");
        return sb.toString();
    }

    @Override
    public List<GraphNode> traverse(GraphNode startNode, GraphNode endNode) {
        Long startTime = System.nanoTime();
        int visitedNodes = 0;

        startNode = this.getClonedNode(startNode);
        endNode = this.getClonedNode(endNode);

        if (startNode.equals(endNode)) {
            return new ArrayList<>();
        }

        Queue<GraphNode> queue = new ArrayDeque<>();
        Set<GraphEdge> visEdges = new HashSet<>();
        startNode.setVisited(true);
        queue.add(startNode);


        GraphNode node;
        GraphEdge edge;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((edge = this.getNextAvailableNode(node)) != null) {

                edge.getSuccessor().setVisited(true);
                visitedNodes++;
                visEdges.add(edge);

                if (edge.getSuccessor().equals(endNode)) {

                    this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    this.pathLength = this.visitedEdges.size();
                    this.nodesVisited = visitedNodes;

                    queue.clear();
                    return this.buildPath(visEdges);
                }
                if (!queue.contains(edge.getSuccessor())) {
                    queue.add(edge.getSuccessor());
                }
            }
        }
        return null;
    }

    @Override
    public GraphEdge getNextAvailableNode(GraphNode node) {
        if (node.getNeighbours().size() == 0) {
            if (node instanceof PuzzleNode) {
                PuzzleNode puzzleNode = (PuzzleNode) node;
                final Map<Character, GraphNode> possibleNeighbours = neighborsBuilder.getPossibleNeighbours(puzzleNode);
                if (possibleNeighbours.size() > 0) {
                    for (Map.Entry<Character, GraphNode> pass : possibleNeighbours.entrySet()) {
                        puzzleNode.addNeighbour(new BFSEdge(null, (PuzzleNode) pass.getValue(), pass.getKey(), this.strategy));
                    }
                }
            }
        }
        return super.getNextAvailableNode(node);
    }

    private List<GraphNode> buildPath(final Set<GraphEdge> visEdges) {
        List<GraphNode> nodes = new LinkedList<>();
        for (GraphEdge edge : visEdges) {
            nodes.add(edge.getSuccessor());
        }
        return nodes;
    }

}
