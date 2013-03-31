package org.kornicameister.sise.puzzle;

import org.kornicameister.sise.core.graph.Graph;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PuzzleSolver {
    void setOrder(final String order);

    void setGraph(final Graph graph);
}
