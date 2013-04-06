package org.kornicameister.sise.puzzle.core;

import org.kornicameister.sise.core.graph.GraphNode;

import java.util.Map;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public interface PuzzleNodeBuilder {
    Map<Character, GraphNode> getPossibleNeighbours(GraphNode puzzleNode);
}
