package org.kornicameister.sise.core.strategies;

import com.rits.cloning.Cloner;
import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;

import java.util.*;

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
    private List<GraphNode> backupNodes;
    private List<GraphNode> path;

    @Override
    public void init(List<GraphNode> nodes) {
        this.backupNodes = nodes;
    }

    @Override
    public List<GraphNode> traverse(int startNode) {
        List<GraphNode> nodes = this.cloneNodes(this.backupNodes);
        for (GraphNode node : nodes) {
            if (node.equals(this.backupNodes.get(startNode))) {
                return this.traverse(node);
            }
        }
        return null;
    }

    @Override
    public List<GraphNode> traverse(int startNode, int endNode) {
        List<GraphNode> nodes = this.cloneNodes(this.backupNodes);
        GraphNode start = null, end = null;
        for (GraphNode node : nodes) {
            if (node.equals(this.backupNodes.get(startNode))) {
                start = node;
                break;
            }
        }
        for (GraphNode node : nodes) {
            if (node.equals(this.backupNodes.get(endNode))) {
                end = node;
                break;
            }
        }
        return this.traverse(start, end);
    }

    protected List<GraphNode> traverse(GraphNode startNode) {
        this.path = new ArrayList<>();
        Queue<GraphNode> queue = new ArrayDeque<>();
        startNode.setVisited(true);
        this.path.add(startNode);
        queue.add(startNode);


        GraphNode node, node2;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((node2 = this.getNextUnvisited(node)) != null) {
                node2.setVisited(true);
                this.path.add(node2);
                queue.add(node2);
            }
        }
        return this.path;
    }

    protected List<GraphNode> traverse(GraphNode startNode, GraphNode endNode) {
        this.path = new ArrayList<>();
        Queue<GraphNode> queue = new ArrayDeque<>();
        startNode.setVisited(true);
        this.path.add(startNode);
        queue.add(startNode);


        GraphNode node, node2;
        while (!queue.isEmpty()) {
            node = queue.remove();
            while ((node2 = this.getNextUnvisited(node)) != null) {
                node2.setVisited(true);
                this.path.add(node2);
                if (node2.equals(endNode)) {
                    return this.path;
                }
                queue.add(node2);
            }
        }
        return null;
    }

    protected GraphNode getNextUnvisited(GraphNode node) {
        for (GraphEdge neighbour : node.getNeighbours()) {
            if (neighbour.isAccessible()) {
                return neighbour.getSuccessor();
            }
        }
        return null;
    }

    private List<GraphNode> cloneNodes(List<GraphNode> nodes) {
        List<GraphNode> nodeList = new LinkedList<>();
        Cloner cloner = new Cloner();
        for (GraphNode node : nodes) {
            nodeList.add(cloner.deepClone(node));
        }
        return nodeList;
    }
}
