package org.kornicameister.sise.core.heuristic;

import org.kornicameister.sise.core.graph.GraphNode;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface Heuristic {
    /**
     * This method may return 1, if for instance
     * passed nodes do not match specific implementation
     *
     * @param start start node
     * @param end   end node
     * @return distance
     */
    Double heuristicValue(GraphNode start, GraphNode end);
}
