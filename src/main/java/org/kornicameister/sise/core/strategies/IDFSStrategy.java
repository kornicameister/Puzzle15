package org.kornicameister.sise.core.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kornicameister.sise.core.Edge;
import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import com.rits.cloning.Cloner;

public class IDFSStrategy  {
	int maxRecursionDepth=0;
	int visitedNodes=0;
	

	boolean success = false;
	private List<GraphNode> backupNodes;
	private List<GraphNode> path = new ArrayList<GraphNode>();
	private List<GraphEdge> visitedEdges = new ArrayList<GraphEdge>();

	public List<GraphNode> getPath() {
		return path;
	}

	public List<GraphEdge> getVisitedEdges() {
		return visitedEdges;
	}

	private String order;

	public void init(List<GraphNode> nodes) {
		this.backupNodes = new ArrayList<>();
		this.backupNodes = nodes;

	}

	public List<GraphNode> traverse(int startNode) {
		// nie jest mi to do niczego potrzebne
		return null;
	}

	public List<GraphNode> traverse(int startNode, int endNode) {
		// nie jest mi to do niczego potrzebne
		return null;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	
	
	
	
	public void traverse(GraphNode startNode, GraphNode endNode, int depth,
			int maxDepth) {

		if (!startNode.isVisited()) {
			
			startNode.setVisited(true);
			this.path.add(startNode);
			visitedNodes++;
			if( maxRecursionDepth<this.path.size())
				maxRecursionDepth=this.path.size();
//			if (compare(((PuzzleNode) startNode).getPuzzle(),
//					((PuzzleNode) endNode).getPuzzle())
//					&& depth==maxDepth) {
//				success = true;
//				return;
//
//			}
			if (startNode.equals(endNode)
					&& depth==maxDepth) {
				success = true;
				return;

			}
			generateNeighbours(startNode);

			for (GraphEdge e : startNode.getNeighbours()) {

				if (!e.getSuccessor().isVisited()) {

					
					if (depth <= maxDepth)
					{
						visitedEdges.add(e);
						traverse(e.getSuccessor(), endNode, depth + 1, maxDepth);
						if (success) {
							return;
						}
					}
					
						visitedEdges.remove(e);
					
				}
				

			}
			//visitedEdges.remove(visitedEdges.size() - 1);
			this.path.remove(findEqualElement(startNode, path));
			// System.out.println(path.size());

		}

		

	}

	public List<GraphNode> iDFS(GraphNode startNode, GraphNode endNode,
			int maxDepth, boolean isGenerator) {
		for (int i = 0; i <= maxDepth; i++) {
			startNode.setVisited(false);
			backupNodes.add(startNode);
			path.clear();
			visitedEdges.clear();
			traverse(startNode, endNode,0, i);
			if (success)
				return path;
			if (i==maxDepth && isGenerator)
				return backupNodes;
			backupNodes.clear();
			path.clear();
			visitedEdges.clear();
		}
		return null;
	}

	private List<GraphNode> cloneNodes(List<GraphNode> nodes) {
		List<GraphNode> nodeList = new LinkedList<>();
		Cloner cloner = new Cloner();
		for (GraphNode node : nodes) {
			nodeList.add(cloner.deepClone(node));
		}
		return nodeList;
	}

	private void generateNeighbours(GraphNode startnode) {
		//System.out.println("przed generowaniem:" + startnode.isVisited());
		List<Character> charlist = new ArrayList<>();
		if (order.length() == 1) {

			charlist.add('D');
			charlist.add('G');
			charlist.add('L');
			charlist.add('P');
			Collections.shuffle(charlist);
		} else {
			for (int i = 0; i < order.length(); i++)
				charlist.add(order.charAt(i));
		}
		//System.out.println(charlist.toString());

		for (int i = 0; i < charlist.size(); i++) {
			// System.out.println(order.length());
			// System.out.println(backupNodes.size());
			if (startnode instanceof PuzzleNode) {
				Integer[][] newStateArray = arrayCopy(((PuzzleNode) startnode)
						.getPuzzle());

				Integer[] positions = findBlankPosition(newStateArray);

				//System.out.println(positions[0] + "  " + positions[1]);

				char direction = charlist.get(i);
				switch (direction) {

				case 'G': {
					if (positions[0] > 0) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0] - 1][positions[1]];
						newStateArray[positions[0] - 1][positions[1]] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);

						int pos = findEqualElement(tempNode, backupNodes);
						if (pos == -1) {
							backupNodes.add(tempNode);
							startnode.addNeighbour(tempNode,
									new UnvisitedAccessibleStrategy(),
									direction);

						} else {
							startnode.addNeighbour(backupNodes.get(pos),
									new UnvisitedAccessibleStrategy(),
									direction);

						}

					}
					break;

				}
				case 'P': {
					if (positions[1] < newStateArray[0].length - 1) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0]][positions[1] + 1];
						newStateArray[positions[0]][positions[1] + 1] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
					
						int pos = findEqualElement(tempNode, backupNodes);
						if (pos == -1) {
							backupNodes.add(tempNode);
							startnode.addNeighbour(tempNode,
									new UnvisitedAccessibleStrategy(),
									direction);

						} else {
							startnode.addNeighbour(backupNodes.get(pos),
									new UnvisitedAccessibleStrategy(),
									direction);

						}
					}
					break;

				}
				case 'L': {
					if (positions[1] > 0) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0]][positions[1] - 1];
						newStateArray[positions[0]][positions[1] - 1] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
						
						int pos = findEqualElement(tempNode, backupNodes);
						if (pos == -1) {
							backupNodes.add(tempNode);
							startnode.addNeighbour(tempNode,
									new UnvisitedAccessibleStrategy(),
									direction);

						} else {
							startnode.addNeighbour(backupNodes.get(pos),
									new UnvisitedAccessibleStrategy(),
									direction);

						}

					}
					break;

				}
				case 'D': {
					if (positions[0] < newStateArray.length - 1) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0] + 1][positions[1]];
						newStateArray[positions[0] + 1][positions[1]] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
						int pos = findEqualElement(tempNode, backupNodes);
						if (pos == -1) {
							backupNodes.add(tempNode);
							startnode.addNeighbour(tempNode,
									new UnvisitedAccessibleStrategy(),
									direction);

						} else {
							startnode.addNeighbour(backupNodes.get(pos),
									new UnvisitedAccessibleStrategy(),
									direction);

						}

					}

				}
				}
			}
		}
		//System.out.println("Po generowaniu: " + startnode.isVisited());

	}

	private Integer[] findBlankPosition(Integer[][] arr) {
		Integer[] result = new Integer[2];
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr[0].length; j++) {
				if (arr[i][j] == 0) {
					result[0] = i;
					result[1] = j;
					return result;
				}
			}
		}
		return null;

	}

	boolean compare(Integer[][] a, Integer[][] b) {
		boolean result = true;
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				if (a[i][j] != b[i][j]) {
					return false;

				}
			}
		}
		return result;
	}

	public static Integer[][] arrayCopy(Integer[][] a) {
		Integer[][] result = new Integer[a.length][a[0].length];
		for (int i = 0; i < a.length; i++) {
			for (int j = 0; j < a[0].length; j++) {
				result[i][j] = a[i][j];
			}
		}
		return result;

	}

	public String getTurns() {
		StringBuilder str = new StringBuilder();
		for (GraphEdge e : visitedEdges) {
			str.append("");
			str.append(((Edge) e).getDirection());
		}
		return str.toString().trim();
	}

	int findEqualElement(GraphNode node, List<GraphNode> lista) {
		for (int i = 0; i < lista.size(); i++) {
			if (compare(((PuzzleNode) node).getPuzzle(),
					((PuzzleNode) lista.get(i)).getPuzzle()))
				return i;
		}
		return -1;
	}
	public String getReport()
	{
		return "Visited nodes="+Integer.toString(visitedNodes)+"\nMax Recursion Depth="+Integer.toString(maxRecursionDepth)+"\nProcessed states="+backupNodes.size()+"\nSolution Length="+Integer.toString(path.size());
	}
	

}


