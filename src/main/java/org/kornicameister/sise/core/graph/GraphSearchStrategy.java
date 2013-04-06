package org.kornicameister.sise.core.graph;

import java.util.List;

/**
 * GraphSearchStrategy is an interface
 * which shows how to traverse through
 * the graph. It can be easily swapped
 * in {@link org.kornicameister.sise.core.Graph} class.
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
    /**
     * Initialize strategy by providing list
     * of nodes. It will be copied to prevent
     * overriding of the nodes read from the input.
     *
     * @param nodes original list of nodes
     */
    void init(List<GraphNode> nodes);

    String getReport();

    GraphNode getNextAvailableNode(GraphNode node);

    List<GraphNode> traverse(GraphNode startNode);

    List<GraphNode> traverse(GraphNode startNode, GraphNode endNode);
}
