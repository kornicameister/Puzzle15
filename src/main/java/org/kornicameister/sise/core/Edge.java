package org.kornicameister.sise.core;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;

/**
 * Default implementation of graph edge
 * that connect two nodes.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Edge implements GraphEdge {
    protected GraphNode predecessor;
    protected GraphNode successor;
    private NodeAccessibleStrategy accessibilityStrategy;
    private Character direction;

    public Edge(GraphNode predecessor, GraphNode successor, NodeAccessibleStrategy strategy, char dir) {
        this(predecessor, successor, strategy);
        direction = dir;
    }

    public Edge(GraphNode predecessor, GraphNode successor, NodeAccessibleStrategy strategy) {
        this(predecessor, successor);
        this.accessibilityStrategy = strategy;
    }

    public Edge(GraphNode predecessor, GraphNode successor) {
        this.predecessor = predecessor;
        this.successor = successor;
    }

    public Character getDirection() {
        return direction;
    }

    @Override
    public void setAccessibleStrategy(NodeAccessibleStrategy strategy) {
        this.accessibilityStrategy = strategy;
    }

    @Override
    public GraphNode getSuccessor() {
        return successor;
    }

    public void setSuccessor(GraphNode successor) {
        this.successor = successor;
    }

    public GraphNode getPredecessor() {
        return predecessor;
    }

    @Override
    public boolean isAccessible() {
        return this.accessibilityStrategy.isAccessible(this.predecessor, this.successor);
    }

    public void setPredecessor(GraphNode predecessor) {
        this.predecessor = predecessor;
    }

    @Override
    public int hashCode() {
        int result = predecessor.hashCode();
        result = 31 * result + successor.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;

        return predecessor.equals(edge.predecessor)
                && successor.equals(edge.successor);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Edge");
        sb.append("{accessibilityStrategy=").append(accessibilityStrategy);
        sb.append(", direction=").append(direction);
        sb.append(", predecessor=").append(predecessor);
        sb.append(", successor=").append(successor);
        sb.append('}');
        return sb.toString();
    }
}
