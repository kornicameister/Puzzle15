package org.kornicameister.sise.puzzle.stratagies;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;
import org.kornicameister.sise.puzzle.exception.PuzzleBlankFilledMissing;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.utilities.ArrayUtilities;

import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class servers two purposes:
 * <ol>
 * <li>validates graph's initial state and ensures that puzzle can be solved from it</li>
 * <li>validates each state of the puzzle graph by ensuring whether or not next graph
 * ic accessible</li>
 * </ol>
 * <p/>
 * For more details, visit this <a href="http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html">link</a>
 * about Puzzle15 being solvable or not theorem.
 *
 * @author kornicameister
 * @version 0.0.2
 * @since 0.0.1
 */
public class InversionAccessibleNodeStrategy implements NodeAccessibleStrategy {
    private static final Integer BLANK_FIELD = 0;
    private final Logger LOGGER = Logger.getLogger(InversionAccessibleNodeStrategy.class.getName());

    @Override
    public boolean isAccessible(GraphNode to) {
        try {
            return this.isSolvable(to);
        } catch (PuzzleBlankFilledMissing puzzleBlankFilledMissing) {
            LOGGER.log(Level.SEVERE, "Puzzle with no blank field, bad...", puzzleBlankFilledMissing);
        }
        return false;
    }

    @Override
    public boolean isAccessible(GraphNode from, GraphNode to) {
        return from.isVisited() && this.isAccessible(to);
    }

    /**
     * Examines provided puzzle node
     * to check whether or not, that node
     * can be next step on the path to oblivion or not.
     *
     * @param node to be examined
     * @return true of node can be a source of pleasure
     * @throws PuzzleBlankFilledMissing
     */
    public boolean isSolvable(GraphNode node) throws PuzzleBlankFilledMissing {
        PuzzleNode puzzleNode = (PuzzleNode) node;
        final int width = puzzleNode.getPuzzle().length;
        boolean widthEven = width % 2 == 0,
                solvable;
        Integer inversion = this.countInversions(puzzleNode),
                blankRowIndex = this.findBlankRow(puzzleNode);

        if (blankRowIndex < 0) {
            throw new PuzzleBlankFilledMissing(puzzleNode);
        }

        if (!widthEven) {
            LOGGER.info(String.format("Puzzle's width is not even, width = %d", width));
            solvable = inversion % 2 == 0;
        } else {
            LOGGER.info(String.format("Puzzle's width even, width=%d,blankRowIndex=%d", width, blankRowIndex));
            solvable = (blankRowIndex % 2 == 0) == (inversion % 2 != 0);
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

    /**
     * Goes, in reverse way, for each puzzle
     * row and checks whether or not that
     * particular row has a BLANK_FIELD within.
     * For more details, visit this <a href="http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html">link</a>
     * about Puzzle15 being solvable or not theorem.
     *
     * @param puzzleNode to be examined
     * @return index > 0 if row has been located, or index < 0 which causes big trouble
     */
    private int findBlankRow(PuzzleNode puzzleNode) {
        int index = -1;
        Integer[][] puzzle = puzzleNode.getPuzzle();
        for (int i = puzzle.length - 1; i >= 0; i--) {
            final Integer[] clone = puzzle[i].clone();
            Arrays.sort(clone);
            index = Arrays.binarySearch(clone, BLANK_FIELD);
            if (index >= 0) {
                index = i;
                break;
            }
        }
        return index;
    }

    /**
     * Counts the number of inversions for current puzzle node.
     * For more details, visit this <a href="http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html">link</a>
     * about Puzzle15 being solvable or not theorem.
     *
     * @param puzzleNode node to be examined
     * @return the number of inversions
     */
    private Integer countInversions(PuzzleNode puzzleNode) {
        Integer[] flatten = ArrayUtilities.flatten(puzzleNode.getPuzzle());
        int inversion = 0;
        for (int i = 0; i < flatten.length - 1; i++) {
            if (!flatten[i].equals(BLANK_FIELD)) {
                for (int k = i + 1; k < flatten.length; k++) {
                    if (flatten[i].compareTo(flatten[k]) > 0 && !flatten[k].equals(BLANK_FIELD)) {
                        inversion += 1;
                    } else if (flatten[k].equals(BLANK_FIELD) && Math.abs(i - k) == 1) {
                        inversion += 1;
                        break;
                    }
                }
            }
        }
        return inversion;
    }
}
