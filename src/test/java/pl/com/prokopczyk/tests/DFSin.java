package pl.com.prokopczyk.tests;

import java.util.ArrayList;
import java.util.List;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.strategies.DFSStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

public class DFSin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Integer [][]start={{1,0,2},{4,5,3},{7,8,6}};
		Integer [][]stop={{1,2,3},{4,5,6},{7,8,0}};
		Integer[][]kopia=DFSStrategy.arrayCopy(start);
		PuzzleNode startowy=new PuzzleNode("", start );
		PuzzleNode koncowyy=new PuzzleNode("", stop );
		PuzzleNode sp=new PuzzleNode("", kopia );
		DFSStrategy strat=new DFSStrategy();
		List<GraphNode>nodes=new ArrayList<>();
		nodes.add(startowy);
		strat.init(nodes);
		strat.setOrder("RDLU");
//		if (startowy.equals(sp))
//			System.out.println("taaaaak");
//		if (startowy.hashCode()==sp.hashCode())
//			System.out.println("tak2");
		List<GraphNode> result=strat.traverse(startowy, koncowyy);
		System.out.println("Turns:"+strat.getTurns());
		System.out.println(result.size());
//		
		
		

	}

}
