package org.kornicameister.sise.puzzle.heuristic;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

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
            return (double) (Math.abs(startPuzzleNode.getX() - puzzleEndNode.getX()) +
                    Math.abs(startPuzzleNode.getY() - puzzleEndNode.getY()));
        }
        return 0.0;
    }
}
