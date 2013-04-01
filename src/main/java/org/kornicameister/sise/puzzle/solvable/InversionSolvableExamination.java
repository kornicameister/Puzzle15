package org.kornicameister.sise.puzzle.solvable;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.puzzle.exception.PuzzleBlankFilledMissing;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.utilities.ArrayUtilities;

import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class InversionSolvableExamination {
    private static final Integer BLANK_FIELD = 0;
    private final Logger LOGGER = Logger.getLogger(InversionSolvableExamination.class.getName());

    public boolean isSolvable(GraphNode node) throws PuzzleBlankFilledMissing {
        PuzzleNode puzzleNode = (PuzzleNode) node;
        final int width = puzzleNode.getPuzzle().length % 2;
        boolean widthEven = width != 0,
                solvable;
        Integer inversion = this.countInversions(puzzleNode);

        if (!widthEven) {
            LOGGER.info(String.format("Puzzle's width is not even, width = %d", width));
            solvable = inversion % 2 == 0;
        } else {
            final int blankRowIndex = this.findBlankRow(puzzleNode);
            LOGGER.info(String.format("Puzzle's width even, width=%d,blankRowIndex=%d", width, blankRowIndex));
            if (blankRowIndex > 0) {
                solvable = (blankRowIndex % 2 == 0) == (inversion % 2 != 0);
            } else {
                throw new PuzzleBlankFilledMissing(puzzleNode);
            }
        }

        if (solvable) {
            LOGGER.info(String.format("Puzzle is solvable at conditions [gridWithEven=%s,inversionsEven=%s]",
                    Boolean.toString(widthEven),
                    Boolean.toString(inversion % 2 == 0))
            );
        } else {
            LOGGER.warning("Puzzle is not solvable");
        }
        return solvable;
    }

    private int findBlankRow(PuzzleNode puzzleNode) {
        int index = -1;
        Integer[][] puzzle = puzzleNode.getPuzzle();
        for (int i = puzzle.length - 1; i >= 0; i++) {
            index = Arrays.binarySearch(puzzle[i], BLANK_FIELD);
            if (index > 0) {
                index = i;
                break;
            }
        }
        return index;
    }

    public Integer countInversions(PuzzleNode puzzleNode) {
        Integer[] flatten = ArrayUtilities.flatten(puzzleNode.getPuzzle());
        int inversion = 0;
        for (int i = 0; i < flatten.length - 1; i++) {
            inversion += (flatten[i] > flatten[i + 1] ? flatten[i] - flatten[i + 1] : 0.0);
        }
        return inversion;
    }

}
