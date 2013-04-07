package org.kornicameister.sise.core.graph;

import org.kornicameister.sise.core.heuristic.Heuristic;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface HeuristicGraphSearchStrategy extends GraphSearchStrategy {
    void setHeuristic(Heuristic heuristic);
}
