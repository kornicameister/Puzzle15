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
        return !to.isVisited() && this.isAccessible(to);
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
    protected boolean isSolvable(GraphNode node) throws PuzzleBlankFilledMissing {
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
            solvable = inversion % 2 == 0;
        } else {
            solvable = (blankRowIndex % 2 == 0) == (inversion % 2 != 0);
        }
        return solvable;
    }

    /**
     * Counts the number of inversions for current puzzle node.
     * For more details, visit this <a href="http://www.cs.bham.ac.uk/~mdr/teaching/modules04/java2/TilesSolvability.html">link</a>
     * about Puzzle15 being solvable or not theorem.
     *
     * @param puzzleNode node to be examined
     * @return the number of inversions
     */
    protected Integer countInversions(PuzzleNode puzzleNode) {
        Integer[] flatten = ArrayUtilities.flatten(puzzleNode.getPuzzle());
        int inversion = 0;
        int[] inversionStep = new int[flatten.length];
        for (int i = 0; i < flatten.length - 1; i++) {
            if (!flatten[i].equals(BLANK_FIELD)) {
                for (int k = i + 1; k < flatten.length; k++) {
                    if (flatten[i].compareTo(flatten[k]) > 0 && !flatten[k].equals(BLANK_FIELD)) {
                        inversion += 1;
                    }
                }
            }
            inversionStep[i] = inversion;
            inversion = 0;
        }
        inversion = 0;
        for (int anInversionStep : inversionStep) {
            inversion += anInversionStep;
        }
        return inversion;
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
    protected int findBlankRow(PuzzleNode puzzleNode) {
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
}
