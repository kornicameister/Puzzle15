package pl.com.prokopczyk.tests;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.strategies.DFSStrategy;
import org.kornicameister.sise.core.strategies.IDFSStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

public class DFSin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		Integer[][] start = { { 1, 0, 2 }, { 4, 5, 3 }, { 7, 8, 6 } };
		Integer[][] stop = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		Integer[][] kopia = DFSStrategy.arrayCopy(start);
		PuzzleNode startowy = new PuzzleNode("", start);
		PuzzleNode koncowyy = new PuzzleNode("", stop);
		PuzzleNode sp = new PuzzleNode("", kopia);
		IDFSStrategy strat = new IDFSStrategy();
		DFSStrategy dfsStrategy = new DFSStrategy();
		List<GraphNode> nodes2 = new ArrayList<>();
		List<GraphNode> nodes = new ArrayList<>();
		nodes2.add(startowy);
		dfsStrategy.init(nodes2);
		dfsStrategy.setOrder("LDRU");
		strat.init(nodes);
		strat.setOrder("LDRU");
		// if (startowy.equals(sp))
		// System.out.println("taaaaak");
		// if (startowy.hashCode()==sp.hashCode())
		// System.out.println("tak2");
		// strat.traverse(startowy, koncowyy);
		List<GraphNode> result = strat.iDFS(startowy, koncowyy, 4);
		if (result == null) {
			System.out.println("Nic nie znalazlem");
		} else {
			System.out.println("Turns length:" + strat.getTurns().length());
			System.out.println("Turns:" + strat.getTurns());
			System.out.println(result.size());

			

		}
		startowy.setVisited(false);
		koncowyy.setVisited(false);
		dfsStrategy.traverse(startowy, koncowyy);
		List<GraphNode> listadfs = dfsStrategy.getPath();
		if (listadfs == null) {
			System.out.println("Nic nie znalazlem");
		} else {
			System.out.println("Turns length:" + dfsStrategy.getTurns().length());
			System.out.println("Turns:" + dfsStrategy.getTurns());
			System.out.println(listadfs.size());

			

		}

		//

	}

}
