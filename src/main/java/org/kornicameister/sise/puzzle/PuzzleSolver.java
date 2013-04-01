package org.kornicameister.sise.puzzle;

import org.kornicameister.sise.core.Graph;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PuzzleSolver {

    void setOrder(final String order);

    void solve();

    /**
     * Method checks for puzzle being solvable or not
     * by examining the initial node.
     *
     * @return true, if puzzle is solvable from initial node, false otherwise
     */
    boolean isSolvable();

    void setGraph(final Graph graph);
}
