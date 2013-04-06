package pl.com.prokopczyk.tests;

import java.util.ArrayList;
import java.util.List;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.strategies.DFSStrategy;
import org.kornicameister.sise.core.strategies.IDFSStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

public class StateGenerator {
	public static void main(String[] args) {

		
		
		Integer[][] start = { { 1, 2, 3,4 }, {  5, 6,7,8 }, { 9,10,11,12},{13,14,15,0 } };
		Integer[][] stop = { { 0,15,14,13 }, { 12,11,10,9 }, { 8,7,6,5 },{4,3,2,1} };
		
		PuzzleNode startowy = new PuzzleNode("", start);
		PuzzleNode koncowyy = new PuzzleNode("", stop);
		IDFSStrategy strat = new IDFSStrategy();
		List<GraphNode> nodes = new ArrayList<>();
		strat.init(nodes);
		strat.setOrder("DLPG");
		List<GraphNode> result = strat.iDFS(startowy, koncowyy, 1, true);
		System.out.println("Result size: "+result.size());
		for (GraphNode node:result)
		{
			//System.out.println(node.toString());
			//System.out.println(node.isVisited());
			startowy = new PuzzleNode("", start);
			node.setVisited(false);
			Integer [][] a=((PuzzleNode)node).getPuzzle();
			PuzzleNode temp=new PuzzleNode("",a);
			IDFSStrategy idfs = new IDFSStrategy();
			DFSStrategy dfs = new DFSStrategy();
			List<GraphNode> nodesForDFS = new ArrayList<>();
			List<GraphNode> nodesForIDFS = new ArrayList<>();
			nodesForDFS.add(node);
			dfs.init(nodesForDFS);
			dfs.setOrder("LDPG");
			idfs.init(nodesForIDFS);
			idfs.setOrder("LDPG");
			
			
			List<GraphNode> resultIDFS = idfs.iDFS(temp, startowy, 2,false);
			if (resultIDFS == null) {
				System.out.println("Nic nie znalazlem");
			} else {
				System.out.println(idfs.getTurns());
				System.out.println(idfs.getReport());
				System.out.println();
//				System.out.println("Turns length:" + idfs.getTurns().length());
//				System.out.println("Turns:" + idfs.getTurns());
//				System.out.println(result.size());

				

			}
//			startowy = new PuzzleNode("", start);
//			node.setVisited(false);
//			dfs.traverse(node,startowy);
//			List<GraphNode> listadfs = dfs.getPath();
//			if (listadfs == null) {
//				System.out.println("Nic nie znalazlem");
//			} else {
//				System.out.println(dfs.getReport());
//				System.out.println("Turns length:" + dfs.getTurns().length());
//				System.out.println("Turns:" + dfs.getTurns());
//				System.out.println(listadfs.size());
//
//				
//
//			}
			
			
			
			
			
		}
		
	}
		
	

}
