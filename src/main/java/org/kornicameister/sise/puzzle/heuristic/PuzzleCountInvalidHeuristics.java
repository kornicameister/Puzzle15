package org.kornicameister.sise.puzzle.heuristic;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;


/**
 * Heuristic calculates invalid position between two Puzzle
 * return czount iinvalid position.
 *
 * @author Seba
 * @version 0.0.1
 * @since 0.0.1
 */


public class PuzzleCountInvalidHeuristics implements Heuristic {

    @Override
    public Double heuristicValue(GraphNode start, GraphNode end) {
        if (start instanceof PuzzleNode && end instanceof PuzzleNode) {
            PuzzleNode startPuzzleNode = (PuzzleNode) start,
                    puzzleEndNode = (PuzzleNode) end;
            double invalidCount = 0;

            for (int i = 0; i < startPuzzleNode.getPuzzle().length; i++) {
                for (int j = 0; j < startPuzzleNode.getPuzzle()[i].length; j++) {
                    final Integer integer = startPuzzleNode.getPuzzle()[i][j];
                    final Integer integer1 = puzzleEndNode.getPuzzle()[i][j];
                    if (!integer.equals(integer1) && (!integer.equals(0) && integer1.equals(0))) {
                        invalidCount += 1.0;
                    }
                }
            }


            return invalidCount;

        }
        return null;
    }

}
