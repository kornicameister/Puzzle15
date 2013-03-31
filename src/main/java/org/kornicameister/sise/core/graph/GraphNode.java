package org.kornicameister.sise.core.graph;

import java.util.Set;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface GraphNode {
    Set<GraphNode> getNeighbours();

    boolean isVisited();

    void setVisited(boolean visited);
}
