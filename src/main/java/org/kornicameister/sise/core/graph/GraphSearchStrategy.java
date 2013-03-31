package org.kornicameister.sise.core.graph;

import java.util.List;

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
    void init(List<GraphNode> nodes);

    List<GraphNode> traverse(int startNode);

    List<GraphNode> traverse(int startNode, int endNode);
}
