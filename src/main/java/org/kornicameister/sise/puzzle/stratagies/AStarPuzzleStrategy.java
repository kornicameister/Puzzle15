package org.kornicameister.sise.puzzle.stratagies;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.HeuristicGraphSearchStrategy;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.puzzle.builder.PuzzleNeighborsBuilder;
import org.kornicameister.sise.puzzle.edge.AStarEdge;
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
    protected Integer generatedNodes = 0;
    protected Integer changedPath = 0;
    private PuzzleNeighborsBuilder nodeBuilder;
    private Heuristic heuristic;
    private List<GraphNode> backupNodes;
    private Integer pathLength;
    private Integer skippedNodes = 0;

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
        sb.append("Generated nodes:\t\t").append(this.nodeBuilder.getGeneratedNodes()).append("\n");
        sb.append("Skipped nodes:\t\t\t").append(this.skippedNodes).append("\n");
        sb.append("Visited nodes:\t\t\t").append(this.nodesVisited).append("\n");
        sb.append("Changed path:\t\t\t").append(this.changedPath).append("\n");
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
        PuzzleNode fromPuzzleNode = null;
        PuzzleNode toPuzzleNode = null;

        if (fromNode instanceof PuzzleNode) {
            fromPuzzleNode = (PuzzleNode) fromNode;
        }
        if (toNode instanceof PuzzleNode) {
            toPuzzleNode = (PuzzleNode) toNode;
        }

        if (fromPuzzleNode == null || toPuzzleNode == null) {
            throw new IllegalArgumentException("Passed nodes not type of AStarPuzzleNode");
        }

        PriorityQueue<AStarEdge> priorityQueue = new PriorityQueue<>(INITIAL_CAPACITY);
        AStarEdge goal = null;


        AStarEdge startEdge = new AStarEdge(
                fromPuzzleNode,
                0.0,
                this.heuristic.heuristicValue(fromPuzzleNode, toPuzzleNode)
        );

        openSet.put(fromPuzzleNode.getID(), startEdge);
        priorityQueue.add(startEdge);

        while (this.openSet.size() > 0) {

            AStarEdge x = priorityQueue.poll();
            openSet.remove(x.getID());

            if (x.getSuccessor().equals(toPuzzleNode)) {
                goal = x;
                this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                break;
            } else {
                this.closedSet.put(x.getID(), x);

                for (GraphEdge graphEdge : this.findNeighbours(x).getSuccessor().getNeighbours()) {
                    AStarEdge neighbour = (AStarEdge) graphEdge;
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
                            this.nodesVisited += 1;
                        } else if (newG < edge.getGCost()) {
                            edge.setGCost(newG);
                            edge.setHCost(this.heuristic.heuristicValue(edge.getSuccessor(), toPuzzleNode));
                            edge.setPredecessor(x);
                            this.changedPath += 1;
                        }

                    } else {
                        this.skippedNodes += 1;
                    }
                }

            }
        }


        if (goal != null) {
            Stack<PuzzleNode> stack = new Stack<>();
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

    public AStarEdge findNeighbours(AStarEdge edge) {
        PuzzleNode node = edge.getSuccessor();
        if (node.getNeighbours().size() == 0) {
            final Map<Character, GraphNode> possibleNeighbours = nodeBuilder.getPossibleNeighbours(node);
            if (possibleNeighbours.size() > 0) {
                this.generatedNodes += possibleNeighbours.size();
                for (Map.Entry<Character, GraphNode> pass : possibleNeighbours.entrySet()) {
                    GraphNode graphNode = pass.getValue();
                    if (graphNode instanceof PuzzleNode) {
                        node.addNeighbour(new AStarEdge(edge, (PuzzleNode) pass.getValue()));
                    }
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
