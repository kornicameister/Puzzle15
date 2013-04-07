package org.kornicameister.sise.puzzle.heuristic;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.utilities.Point;

/**
 * Heuristic calculates distance between two nodes using
 * Manhattan distance. Distance is calculated from positions
 * of the blank field within the puzzle stored in the Node.
 * This class accepts {@link org.kornicameister.sise.puzzle.node.PuzzleNode} nodes.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleManhattanHeuristic implements Heuristic {

    @Override
    public Double heuristicValue(GraphNode start, GraphNode end) {
        if (start instanceof PuzzleNode && end instanceof PuzzleNode) {
            PuzzleNode startPuzzleNode = (PuzzleNode) start,
                    puzzleEndNode = (PuzzleNode) end;

            final Integer[][] sPuzzle = startPuzzleNode.getPuzzle(),
                    ePuzzle = puzzleEndNode.getPuzzle();

            double distance = 0.0;

            Point cordsInFinal;
            for (int i = 0; i < sPuzzle.length; i++) {
                for (int j = 0; j < sPuzzle.length; j++) {
                    if (!sPuzzle[i][j].equals(ePuzzle[i][j])) {
                        cordsInFinal = this.findCordsInFinal(sPuzzle[i][j], ePuzzle);
                        distance += Math.abs(i - cordsInFinal.getX()) + Math.abs(j - cordsInFinal.getY());
                    }
                }
            }

            return distance;
        }
        return 0.0;
    }

    private Point findCordsInFinal(final Integer value, final Integer[][] ePuzzle) {
        Point cordsInFinal = null;
        for (int i = 0; i < ePuzzle.length; i++) {
            for (int j = 0; j < ePuzzle.length; j++) {
                if (ePuzzle[i][j].equals(value)) {
                    cordsInFinal = new Point(i, j);
                }
            }
        }
        return cordsInFinal;
    }
}
