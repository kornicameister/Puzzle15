package org.kornicameister.sise.puzzle.stratagies;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.HeuristicGraphSearchStrategy;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.puzzle.builder.PuzzleNeighborsBuilder;
import org.kornicameister.sise.puzzle.core.PuzzleNodeBuilder;
import org.kornicameister.sise.puzzle.edge.AStarEdge;
import org.kornicameister.sise.puzzle.node.AStarPuzzleNode;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * This class implements A* graph's pathfinder algorithm.
 *
 * @author kornicameister
 */
public class AStarPuzzleStrategy implements HeuristicGraphSearchStrategy {
    private static final int INITIAL_CAPACITY = 20;
    private final Map<Integer, AStarEdge> openSet = new HashMap<>();
    private final Map<Integer, AStarEdge> closedSet = new HashMap<>();
    protected Long computationTime = 0l;
    protected Integer nodesVisited = 0;
    protected Integer newNodesVisited = 0;
    protected Integer revisitedNodes = 0;
    private PuzzleNodeBuilder nodeBuilder;
    private Heuristic heuristic;
    private List<GraphNode> backupNodes;
    private Integer pathLength;

    public AStarPuzzleStrategy(Heuristic heuristic) {
        this.heuristic = heuristic;
    }

    @Override
    public void init(List<GraphNode> nodes) {
        this.backupNodes = nodes;
        this.nodeBuilder = new PuzzleNeighborsBuilder(nodes);
    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n");
        sb.append("Report").append("\n");
        sb.append("Time:\t\t\t\t\t").append(this.computationTime).append(" ms\n");
        sb.append("Path length:\t\t\t").append(this.pathLength).append("\n");
        sb.append("All visited nodes:\t\t").append(this.nodesVisited).append("\n");
        sb.append("New visited nodes:\t\t").append(this.newNodesVisited).append("\n");
        sb.append("Re  visited nodes:\t\t").append(this.revisitedNodes).append("\n");
        return sb.toString();
    }

    @Override
    public GraphNode getNextAvailableNode(GraphNode node) {
        throw new NotImplementedException();
    }

    @Override
    public List<GraphNode> traverse(GraphNode startNode) {
        throw new NotImplementedException();
    }

    @Override
    public List<GraphNode> traverse(GraphNode fromNode, GraphNode toNode) {
        Long startTime = System.nanoTime();
        AStarPuzzleNode fromPuzzleNode = null;
        AStarPuzzleNode toPuzzleNode = null;

        if (!(fromNode instanceof AStarPuzzleNode) && fromNode instanceof PuzzleNode) {
            fromPuzzleNode = this.convertToAStartPuzzleNode(fromNode);
        } else if (fromNode instanceof AStarPuzzleNode) {
            fromPuzzleNode = (AStarPuzzleNode) fromNode;
        }

        if (!(toNode instanceof AStarPuzzleNode) && toNode instanceof PuzzleNode) {
            toPuzzleNode = this.convertToAStartPuzzleNode(toNode);
        } else if (toNode instanceof AStarPuzzleNode) {
            toPuzzleNode = (AStarPuzzleNode) toNode;
        }

        if (fromPuzzleNode == null || toPuzzleNode == null) {
            throw new IllegalArgumentException("Passed nodes not type of AStarPuzzleNode");
        }

        PriorityQueue<AStarEdge> priorityQueue = new PriorityQueue<>(INITIAL_CAPACITY);
        AStarEdge startEdge = new AStarEdge(
                fromPuzzleNode,
                0.0,
                this.heuristic.heuristicValue(fromPuzzleNode, toPuzzleNode)
        );

        openSet.put(fromPuzzleNode.getID(), startEdge);
        priorityQueue.add(startEdge);

        AStarEdge goal = null;

        while (this.openSet.size() > 0) {

            AStarEdge x = priorityQueue.poll();
            openSet.remove(x.getID());

            if (x.getSuccessor().equals(toPuzzleNode)) {
                goal = x;
                this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                break;
            } else {
                this.closedSet.put(x.getID(), x);

                for (AStarEdge neighbour : this.findNeighbours(x).getSuccessor().getStarNeighbours()) {
                    if (!this.closedSet.containsKey(neighbour.getID())) {

                        Double newG = x.getGCost() + this.heuristic.heuristicValue(
                                x.getSuccessor(),
                                neighbour.getSuccessor()
                        );

                        AStarEdge edge = this.openSet.get(neighbour.getID());

                        if (edge == null) {
                            edge = new AStarEdge(
                                    x,
                                    neighbour.getSuccessor(),
                                    newG,
                                    this.heuristic.heuristicValue(neighbour.getSuccessor(), toPuzzleNode)
                            );

                            this.openSet.put(edge.getID(), edge);
                            priorityQueue.add(edge);
                            this.newNodesVisited += 1;
                        } else if (newG < edge.getGCost()) {
                            edge.setGCost(newG);
                            edge.setHCost(this.heuristic.heuristicValue(edge.getSuccessor(), toPuzzleNode));
                            edge.setPredecessor(x);
                            this.revisitedNodes += 1;
                        }

                        this.nodesVisited += 1;
                    }
                }

            }
        }

        if (goal != null) {
            Stack<AStarPuzzleNode> stack = new Stack<>();
            List<GraphNode> list = new ArrayList<>();
            stack.push(goal.getSuccessor());
            AStarEdge parent = goal.getPredecessor();
            while (parent != null) {
                stack.push(parent.getSuccessor());
                parent = parent.getPredecessor();
            }
            while (stack.size() > 0) {
                list.add(stack.pop());
            }
            this.pathLength = list.size();
            return list;
        }

        return null;
    }

    private AStarPuzzleNode convertToAStartPuzzleNode(GraphNode node) {
        PuzzleNode puzzleNode = (PuzzleNode) node;
        return new AStarPuzzleNode(
                puzzleNode.getID(),
                puzzleNode.getLabel(),
                puzzleNode.getOrder(),
                puzzleNode.getPuzzle(),
                puzzleNode.getNeighbours().toArray(new GraphEdge[]{})
        );
    }

    public AStarEdge findNeighbours(AStarEdge edge) {
        AStarPuzzleNode node = edge.getSuccessor();
        if (node.getNeighbours().size() == 0) {
            final Map<Character, GraphNode> possibleNeighbours = nodeBuilder.getPossibleNeighbours(node);
            if (possibleNeighbours.size() > 0) {
                for (Map.Entry<Character, GraphNode> pass : possibleNeighbours.entrySet()) {
                    AStarPuzzleNode aStarPuzzleNode = this.convertToAStartPuzzleNode(pass.getValue());
                    node.addStartNeighbour(new AStarEdge(edge, aStarPuzzleNode));
                }
            }
        }
        edge.setSuccessor(node);
        return edge;
    }

    @Override
    public void setHeuristic(Heuristic heuristic) {
        this.heuristic = heuristic;
    }
}
