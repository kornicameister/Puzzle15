package org.kornicameister.sise.core.graph;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

/**
 * Node is class that describes functionality of
 * the node in the graph. This can be used in every kind of graphs
 * and extended if necessary to support different graph
 * searches or/and functionality
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public abstract class Node implements Comparable<Node>, GraphNode {
    private final String label;
    private final Set<GraphNode> neighbours;
    private boolean visited;

    public Node(final String label, GraphNode... neighbours) {
        this.label = label;
        this.neighbours = new TreeSet<GraphNode>(Arrays.asList(neighbours));
        this.visited = false;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public Set<GraphNode> getNeighbours() {
        return neighbours;
    }

    @Override
    public boolean isVisited() {
        return visited;
    }

    @Override
    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node that = (Node) o;

        return visited == that.visited
                && label.equals(that.label)
                && neighbours.equals(that.neighbours);
    }

    @Override
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + neighbours.hashCode();
        result = 31 * result + (visited ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Node");
        sb.append("{label='").append(label).append('\'');
        sb.append(", neighbours=").append(neighbours);
        sb.append(", visited=").append(visited);
        sb.append('}');
        return sb.toString();
    }
}
