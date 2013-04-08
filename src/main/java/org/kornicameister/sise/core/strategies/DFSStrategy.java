package org.kornicameister.sise.core.strategies;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.kornicameister.sise.core.Edge;
import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import com.rits.cloning.Cloner;

public class DFSStrategy implements GraphSearchStrategy {
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

	private String order=null;

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

	public List<GraphNode> traverse(GraphNode startNode, GraphNode endNode) {
		if(order==null)
			order=((PuzzleNode)startNode).getOrder();
		

		if (!startNode.isVisited()) {
			
			startNode.setVisited(true);
			this.path.add(startNode);
			visitedNodes++;
			if( maxRecursionDepth<this.path.size())
				maxRecursionDepth=this.path.size();
//			if (compare(((PuzzleNode) startNode).getPuzzle(),
//					((PuzzleNode) endNode).getPuzzle())) {
//				success = true;
//				return;
//
//			}
			if (startNode.equals(endNode)) {
				success = true;
				return path;

			}
			
			generateNeighbours(startNode);

			for (GraphEdge e : startNode.getNeighbours()) {

				if (!e.getSuccessor().isVisited()) {
					visitedEdges.add(e);
					traverse(e.getSuccessor(), endNode);
					if (success) {
						return path;
					}
					visitedEdges.remove(e);
				}
				

			}
			
			this.path.remove(findEqualElement(startNode, path));

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

		for (int i = 0; i < charlist.size(); i++) {

			if (startnode instanceof PuzzleNode) {
				Integer[][] newStateArray = arrayCopy(((PuzzleNode) startnode)
						.getPuzzle());

				Integer[] positions = findBlankPosition(newStateArray);
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
	
	   @Override
	    public String getReport() {
	        StringBuilder sb = new StringBuilder();
	        sb.append(this.getClass().getSimpleName()).append("\n");
	        sb.append("Report").append("\n");
	        sb.append("Path length:\t\t").append(this.path.size()).append("\n");
	        sb.append("Visited nodes:\t\t").append(this.backupNodes.size()).append("\n");
	        sb.append("Max recursion depth:\t\t").append(this.maxRecursionDepth).append("\n");
	        return sb.toString();
	    }

	@Override
	public GraphNode getNextAvailableNode(GraphNode node) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<GraphNode> traverse(GraphNode startNode) {
		// TODO Auto-generated method stub
		return null;
	}
	

}
