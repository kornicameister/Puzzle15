package org.kornicameister.sise.puzzle.exception;

import org.kornicameister.sise.puzzle.node.PuzzleNode;

import java.util.Arrays;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleBlankFilledMissing extends Exception {
    public PuzzleBlankFilledMissing(PuzzleNode puzzleNode) {
        super(String.format("Puzzle %s has not blank field", Arrays.deepToString(puzzleNode.getPuzzle())));
    }
}
