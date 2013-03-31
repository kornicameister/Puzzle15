package org.kornicameister.sise.core.graph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/**
 * Graph points to how graph
 * should work and which method should it implement
 * in order to work properly. For instance this
 * graph must have a set of nodes and the method
 * which would allow to traverse through it
 * with desired algorithm
 *
 * @author kornicameister
 * @since 0.0.1
 */
public class Graph implements Iterable<GraphNode> {
    private final List<GraphNode> nodes;
    private GraphSearchStrategy strategy;
    private List<GraphNode> path;

    public Graph(GraphNode... nodes) {
        this.nodes = new ArrayList<GraphNode>(Arrays.asList(nodes));
    }

    public Graph(GraphSearchStrategy strategy, GraphNode... nodes) {
        this(nodes);
        this.strategy = strategy;
    }

    public void traverse(int startNode) {
        this.strategy.init(this.nodes);
        this.path = this.strategy.traverse(startNode);
    }

    public void traverse(int startNode, int endNode) {
        this.strategy.init(this.nodes);
        this.path = this.strategy.traverse(startNode, endNode);
    }

    public GraphSearchStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GraphSearchStrategy strategy) {
        this.strategy = strategy;
    }

    public List<GraphNode> getPath() {
        return path;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Graph");
        sb.append("{nodes=").append(nodes.size());
        sb.append(", strategy=").append(strategy);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Iterator<GraphNode> iterator() {
        return this.nodes.iterator();
    }
}
