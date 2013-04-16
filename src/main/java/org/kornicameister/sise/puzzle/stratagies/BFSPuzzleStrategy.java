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

        Queue<BFSEdge> queue = new ArrayDeque<>();
        startNode.setVisited(true);
        queue.add(new BFSEdge(null, (PuzzleNode) startNode, Character.MAX_SURROGATE, this.strategy));


        BFSEdge node;
        BFSEdge edge;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((edge = this.getNextAvailableNode(node)) != null) {

                edge.getSuccessor().setVisited(true);
                visitedNodes++;

                if (edge.getSuccessor().equals(endNode)) {

                    this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    ;
                    this.nodesVisited = visitedNodes;

                    queue.clear();
                    this.visitedEdges = this.trimBySE(edge);
                    this.pathLength = this.visitedEdges.size() - 1;
                    return this.buildPath();
                }
                if (!queue.contains(edge)) {
                    queue.add(edge);
                }
            }
        }
        return null;
    }

    private List<GraphEdge> trimBySE(GraphEdge edge) {
        BFSEdge goal = (BFSEdge) edge;

        List<GraphEdge> visitedEdges = new ArrayList<>();
        visitedEdges.add(goal);
        BFSEdge parent = goal.getPredecessor();

        while (parent != null) {
            visitedEdges.add(parent);
            parent = parent.getPredecessor();
        }

        Collections.reverse(visitedEdges);

        return visitedEdges;
    }

    public BFSEdge getNextAvailableNode(BFSEdge edge) {
        PuzzleNode node = edge.getSuccessor();
        if (node.getNeighbours().size() == 0) {
            final Map<Character, GraphNode> possibleNeighbours = neighborsBuilder.getPossibleNeighbours(node);
            if (possibleNeighbours.size() > 0) {
                for (Map.Entry<Character, GraphNode> pass : possibleNeighbours.entrySet()) {
                    node.addNeighbour(new BFSEdge(edge, (PuzzleNode) pass.getValue(), pass.getKey(), this.strategy));
                }
            }
        }
        return (BFSEdge) super.getNextAvailableNode(node);
    }

}
