package org.kornicameister.sise.puzzle.exception;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import java.util.Arrays;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleNotSolvableException extends RuntimeException {
    public PuzzleNotSolvableException(GraphNode graphNode) {
        super(String.format("Puzzle %s not solvable", Arrays.deepToString(((PuzzleNode) graphNode).getPuzzle())));
    }
}
