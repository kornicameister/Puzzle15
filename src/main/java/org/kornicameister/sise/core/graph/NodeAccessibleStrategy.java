package org.kornicameister.sise.core.graph;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface NodeAccessibleStrategy {
    boolean isAccessible(final GraphNode to);

    boolean isAccessible(final GraphNode from, final GraphNode to);
}
