package pl.com.prokopczyk.tests;

import java.util.ArrayList;
import java.util.List;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.strategies.DFSStrategy;
import org.kornicameister.sise.core.strategies.IDFSStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

public class StateGenerator {
    public static void main(String[] args) {


        Integer[][] stop = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 10, 11, 12}, {13, 14, 15, 0}};
        Integer[][] start = {{1, 2, 3, 4}, {5, 6, 7, 8}, {9, 11, 14, 12}, {13, 0, 10,15}};


//        List<List<GraphNode>> warstwy = new ArrayList<List<GraphNode>>();
//        String[] kolejnosc = {"LPGD", "DLPG", "PGDL", "DGLP"};
//        for (int i = 1; i < 7; i++) {
//            PuzzleNode startowy = new PuzzleNode("", start);
//            PuzzleNode koncowyy = new PuzzleNode("", stop);
//            IDFSStrategy strat = new IDFSStrategy();
//            List<GraphNode> nodes = new ArrayList<>();
//            strat.init(nodes);
//            strat.setOrder("DLPG");
//            List<GraphNode> result = strat.iDFS(startowy, koncowyy, i, true);
//            warstwy.add(result);
//            //System.out.println("Rozmiar warstw:"+warstwy.size());
//
//            if (i > 1) {
//                for (int j = 0; j < i - 1; j++)
//                    warstwy.get(i - 1).removeAll(warstwy.get(j));
//            }
//
//
//        }
//        warstwy.get(0).remove(0);
//        for (int j = 0; j < kolejnosc.length; j++) {
//            System.out.println("\\newline");
//            System.out.println("Kolejnosc przeszukiwania:" + kolejnosc[j]);
//            for (int i = 0; i < warstwy.size(); i++) {
//                int solutionLength = 0, visited = 0, maxDepth = 0, generatedNodes = 0;
//                List<GraphNode> result = warstwy.get(i);
//                for (GraphNode node : result) {
//                    PuzzleNode startowy = new PuzzleNode("", start);
//                    node.setVisited(false);
//                    Integer[][] a = ((PuzzleNode) node).getPuzzle();
//                    PuzzleNode temp = new PuzzleNode("", a);
//                    IDFSStrategy idfs = new IDFSStrategy();
//                    List<GraphNode> nodesForIDFS = new ArrayList<>();
//                    idfs.init(nodesForIDFS);
//                    idfs.setOrder("R");
//                    List<GraphNode> resultIDFS = idfs.iDFS(temp, startowy, i + 2, false);
//                    if (resultIDFS == null) {
//                        System.out.println("Nic nie znalazlem dla i=" + i);
//                    } else {
//                        solutionLength += idfs.getTurns().length();
//                        visited += idfs.getVisitedNodes();
//                        maxDepth += idfs.getMaxRecursionDepth();
//                        generatedNodes += idfs.getBackupNodes().size();
//
//
//                    }
//
//
//                }
//                System.out.println("Odleglosc rozwiazania: " + (i + 1));
//                System.out.println("\\begin{itemize}");
//                System.out.println("\\item Średnia dlugosc rozwiazania: " + ((double) solutionLength) / (double) result.size());
//                System.out.println("\\item Średnia ilosc odwiedzonych stanow: " + ((double) visited) / (double) result.size());
//                System.out.println("\\item Średnia maksymalna glebokosc rekursji: " + ((double) maxDepth) / (double) result.size());
//                System.out.println("\\item Średnia ilosc wygenerowanych stanow: " + ((double) generatedNodes) / (double) result.size());
//                System.out.println("\\end{itemize}");
//            }
//        }


        PuzzleNode startowy = new PuzzleNode("", start);
        PuzzleNode koncowyy = new PuzzleNode("", stop);
        DFSStrategy strat = new DFSStrategy();
        List<GraphNode> nodes = new ArrayList<>();
        nodes.add(startowy);
        strat.init(nodes);
        strat.setOrder("LDPG");
        //DGLP  LPDG GLDP
        List<GraphNode>res=strat.traverse(startowy, koncowyy);
        if(res!=null)
        {
        	
          int solutionLength = strat.getTurns().length();
         int visited= strat.getVisitedNodes();
         int maxDepth = strat.getMaxRecursionDepth();
          int generatedNodes = strat.getBackupNodes().size();
          System.out.println(strat.getTurns());


      


  
  System.out.println("Odleglosc rozwiazania: " + (strat.getPath().size()));
  System.out.println("\\begin{itemize}");
  System.out.println("\\item dlugosc rozwiazania: " + ((double) solutionLength) );
  System.out.println("\\item ilosc odwiedzonych stanow: " + ((double) visited) );
  System.out.println("\\item  maksymalna glebokosc rekursji: " + ((double) maxDepth));
  System.out.println("\\item  ilosc wygenerowanych stanow: " + ((double) generatedNodes) );
  System.out.println("\\end{itemize}");
        	
        }
        
        
        
        
        System.out.println(strat.getReport());
        //List<GraphNode> result = strat.iDFS(startowy, koncowyy, 9, true);


//		System.out.println("Result size: "+result.size());
//		for (GraphNode node:result)
//		{
//			//System.out.println(node.toString());
//			//System.out.println(node.isVisited());
//			startowy = new PuzzleNode("", start);
//			node.setVisited(false);
//			Integer [][] a=((PuzzleNode)node).getPuzzle();
//			PuzzleNode temp=new PuzzleNode("",a);
//			IDFSStrategy idfs = new IDFSStrategy();
//			DFSStrategy dfs = new DFSStrategy();
//			List<GraphNode> nodesForDFS = new ArrayList<>();
//			List<GraphNode> nodesForIDFS = new ArrayList<>();
//			nodesForDFS.add(node);
//			dfs.init(nodesForDFS);
//			dfs.setOrder("LDPG");
//			idfs.init(nodesForIDFS);
//			idfs.setOrder("LDPG");
//			
//			
//			List<GraphNode> resultIDFS = idfs.iDFS(temp, startowy, 2,false);
//			if (resultIDFS == null) {
//				System.out.println("Nic nie znalazlem");
//			} else {
//				System.out.println(idfs.getTurns());
//				System.out.println(idfs.getReport());
//				System.out.println();
////				System.out.println("Turns length:" + idfs.getTurns().length());
////				System.out.println("Turns:" + idfs.getTurns());
////				System.out.println(result.size());
//
//				
//
//			}
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
