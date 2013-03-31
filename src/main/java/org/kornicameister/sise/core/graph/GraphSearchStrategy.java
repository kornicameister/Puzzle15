package org.kornicameister.sise.core.graph;

/**
 * GraphSearchStrategy is an interface
 * which shows how to traverse through
 * the graph. It can be easily swapped
 * in {@link Graph} class.
 * <p/>
 * Default search strategy includes two
 * method one tries to go trough the whole graph
 * and second one stops when the <strong>end node</strong>
 * is reached.
 *
 * @author kornicameister
 * @version 0.0.1
 * @see GraphNode
 * @since 0.0.1
 */
public interface GraphSearchStrategy {
    void traverse(GraphNode startNode);

    void traverse(GraphNode startNode, GraphNode endNode);
}
