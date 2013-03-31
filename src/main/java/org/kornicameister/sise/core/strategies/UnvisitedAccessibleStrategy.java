package org.kornicameister.sise.core.strategies;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class UnvisitedAccessibleStrategy implements NodeAccessibleStrategy {
    @Override
    public boolean isAccessible(GraphNode to) {
        return !to.isVisited();
    }

    @Override
    public boolean isAccessible(GraphNode from, GraphNode to) {
        return from.isVisited() && !to.isVisited();
    }
}
