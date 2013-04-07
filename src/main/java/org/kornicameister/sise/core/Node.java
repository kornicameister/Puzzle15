package org.kornicameister.sise.core;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
public class Node implements GraphNode {
    private static Integer ID = 0;
    protected List<GraphEdge> neighbours = new ArrayList<>();
    protected Integer id;
    protected String label = "";
    protected boolean visited = false;

    public Node(final String label, GraphEdge... neighbours) {
        this.label = label;
        if (neighbours.length > 0)
            this.neighbours.addAll(Arrays.asList(neighbours));
        this.id = ID++;
    }

    public Integer getID() {
        return this.id;
    }

    public String getLabel() {
        return label;
    }

    @Override
    public void addNeighbour(GraphNode graphNode, NodeAccessibleStrategy strategy) {
        this.neighbours.add(new Edge(this, graphNode, strategy));
    }

    @Override
    public void addNeighbour(GraphNode graphNode, NodeAccessibleStrategy strategy, char direction) {
        this.neighbours.add(new Edge(this, graphNode, strategy, direction));
    }

    @Override
    public List<GraphEdge> getNeighbours() {
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
    public int hashCode() {
        int result = label.hashCode();
        result = 31 * result + neighbours.hashCode();
        result = 31 * result + (visited ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Node node = (Node) o;

        return label.equals(node.label)
                && new Integer(neighbours.size()).equals(new Integer(node.neighbours.size()));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Node");
        sb.append("{id=").append(id);
        sb.append(", label='").append(label).append('\'');
        sb.append(", visited=").append(visited);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object object) {
        if (object instanceof Node) {
            Node node = (Node) object;
            int result = Boolean.compare(this.visited, node.visited);
            return result * -1;
        }
        return 0;
    }
}
