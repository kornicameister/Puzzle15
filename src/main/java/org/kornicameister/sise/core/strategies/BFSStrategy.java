package org.kornicameister.sise.core.strategies;

import com.rits.cloning.Cloner;
import org.kornicameister.sise.core.Edge;
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
    protected List<GraphNode> backupNodes;
    protected List<GraphEdge> visitedEdges;

    @Override
    public void init(List<GraphNode> nodes) {
        this.backupNodes = nodes;
        this.visitedEdges = new LinkedList<>();
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

    @Override
    public String getTurns() {
        StringBuilder str = new StringBuilder();
        for (GraphEdge e : this.visitedEdges) {
            str.append("");
            str.append(((Edge) e).getDirection());
        }
        return str.toString().trim();
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
        Queue<GraphNode> queue = new ArrayDeque<>();
        startNode.setVisited(true);
        queue.add(startNode);


        GraphNode node;
        GraphEdge edge;

        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((edge = this.getNextAvailableNode(node)) != null) {
                edge.getSuccessor().setVisited(true);
                visitedNodes++;
                queue.add(edge.getSuccessor());
            }
        }
        this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
        this.pathLength = this.visitedEdges.size();
        this.nodesVisited = visitedNodes;
        return this.buildPath();
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
        startNode.setVisited(true);
        queue.add(startNode);


        GraphNode node;
        GraphEdge edge;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((edge = this.getNextAvailableNode(node)) != null) {

                edge.getSuccessor().setVisited(true);
                visitedNodes++;
                this.visitedEdges.add(edge);

                if (edge.getSuccessor().equals(endNode)) {

                    this.computationTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime);
                    this.pathLength = this.visitedEdges.size();
                    this.nodesVisited = visitedNodes;

                    queue.clear();
                    return this.buildPath();
                }
                queue.add(edge.getSuccessor());
            }
        }
        return null;
    }

    protected List<GraphNode> buildPath() {
        List<GraphNode> nodes = new LinkedList<>();
        for (GraphEdge edge : this.visitedEdges) {
            nodes.add(edge.getSuccessor());
        }
        return nodes;
    }

    @Override
    public GraphEdge getNextAvailableNode(GraphNode node) {
        for (GraphEdge neighbour : node.getNeighbours()) {
            if (neighbour.isAccessible()) {
                return neighbour;
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
