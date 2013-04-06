package org.kornicameister.sise.core.strategies;

import com.rits.cloning.Cloner;
import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * BFS Search strategy.
 * It is based on the iterative approach to search
 * graph per level.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BFSStrategy implements GraphSearchStrategy {
    protected Integer nodesVisited = 0;
    protected Long computationTime = 0l;
    protected Integer pathLength = 0;
    private List<GraphNode> backupNodes;

    @Override
    public void init(List<GraphNode> nodes) {
        this.backupNodes = nodes;
    }

    @Override
    public String getReport() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.getClass().getSimpleName()).append("\n");
        sb.append("Report").append("\n");
        sb.append("Time:\t\t\t\t").append(this.computationTime).append(" ms\n");
        sb.append("Path length:\t\t").append(this.pathLength).append("\n");
        sb.append("Visited nodes:\t\t").append(this.nodesVisited).append("\n");
        return sb.toString();
    }

    protected GraphNode getClonedNode(GraphNode startNode) {
        List<GraphNode> nodes = this.cloneNodes(this.backupNodes);
        for (GraphNode node : nodes) {
            if (node.equals(startNode)) {
                return node;
            }
        }
        return null;
    }

    @Override
    public List<GraphNode> traverse(GraphNode startNode) {
        Long startTime = System.nanoTime();
        int visitedNodes = 0;

        startNode = this.getClonedNode(startNode);
        List<GraphNode> path = new ArrayList<>();
        Queue<GraphNode> queue = new ArrayDeque<>();
        startNode.setVisited(true);
        path.add(startNode);
        queue.add(startNode);


        GraphNode node, node2;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((node2 = this.getNextAvailableNode(node)) != null) {
                node2.setVisited(true);
                visitedNodes++;
                path.add(node2);
                queue.add(node2);
            }
        }
        this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        this.pathLength = path.size();
        this.nodesVisited = visitedNodes;
        return path;
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

        List<GraphNode> path = new ArrayList<>();
        Queue<GraphNode> queue = new ArrayDeque<>();
        startNode.setVisited(true);
        path.add(startNode);
        queue.add(startNode);


        GraphNode node, node2;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((node2 = this.getNextAvailableNode(node)) != null) {

                node2.setVisited(true);
                visitedNodes++;
                path.add(node2);

                if (node2.equals(endNode)) {

                    this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    this.pathLength = path.size();
                    this.nodesVisited = visitedNodes;

                    queue.clear();
                    return path;
                }
                queue.add(node2);
            }
        }
        return null;
    }

    @Override
    public GraphNode getNextAvailableNode(GraphNode node) {
        for (GraphEdge neighbour : node.getNeighbours()) {
            if (neighbour.isAccessible()) {
                return neighbour.getSuccessor();
            }
        }
        return null;
    }

    protected List<GraphNode> cloneNodes(List<GraphNode> nodes) {
        List<GraphNode> nodeList = new LinkedList<>();
        Cloner cloner = new Cloner();
        for (GraphNode node : nodes) {
            nodeList.add(cloner.deepClone(node));
        }
        return nodeList;
    }
}
