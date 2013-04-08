package org.kornicameister.sise.puzzle.heuristic;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;


/**
 * Heuristic calculates invalid position between two Puzzle
 * return czount iinvalid position.
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
		double iloscNiepoprawnych = 0;
	
		for(int i=0;i<startPuzzleNode.getPuzzle().length;i++){
			for(int j=0;j<startPuzzleNode.getPuzzle()[i].length;j++){
				if(startPuzzleNode.getPuzzle()[i][j]!=puzzleEndNode.getPuzzle()[i][j]) iloscNiepoprawnych=iloscNiepoprawnych+1;
			}
		}
				

		return iloscNiepoprawnych;

		}
		return null;
	}

}
