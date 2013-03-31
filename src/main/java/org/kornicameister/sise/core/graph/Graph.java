package org.kornicameister.sise.core.graph;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

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
    private final Set<GraphNode> nodes;
    private GraphSearchStrategy strategy;

    public Graph(GraphNode... nodes) {
        this.nodes = new TreeSet<GraphNode>(Arrays.asList(nodes));
    }

    public Graph(GraphSearchStrategy strategy, GraphNode... nodes) {
        this(nodes);
        this.strategy = strategy;
    }

    public void traverse(GraphNode startNode) {
        this.strategy.traverse(startNode);
    }

    public void traverse(GraphNode startNode, GraphNode endNode) {
        this.strategy.traverse(startNode, endNode);
    }

    public GraphSearchStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(GraphSearchStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Graph");
        sb.append("{nodes=").append(nodes);
        sb.append(", strategy=").append(strategy);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Iterator<GraphNode> iterator() {
        return this.nodes.iterator();
    }
}
