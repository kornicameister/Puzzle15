package org.kornicameister.sise.core.graph;

import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface GraphNode extends Comparable {
    void addNeighbour(GraphNode node);

    List<GraphNode> getNeighbours();

    boolean isVisited();

    void setVisited(boolean visited);
}
