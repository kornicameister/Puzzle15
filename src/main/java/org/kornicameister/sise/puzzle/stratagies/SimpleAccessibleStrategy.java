package org.kornicameister.sise.puzzle.stratagies;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class SimpleAccessibleStrategy implements NodeAccessibleStrategy {
    @Override
    public boolean isAccessible(GraphNode to) {
        return true;
    }

    @Override
    public boolean isAccessible(GraphNode from, GraphNode to) {
        return true;
    }
}
