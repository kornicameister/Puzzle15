package org.kornicameister.sise.core.strategies;

import java.util.ArrayList;
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

public class DFSStrategy {
	private Set<GraphNode> backupNodes;
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
		this.backupNodes = new HashSet<GraphNode>();
		this.backupNodes.addAll(nodes);

	}

	public List<GraphNode> traverse(int startNode) {
		//nie jest mi to do niczego potrzebne
		return null;
	}

	public List<GraphNode> traverse(int startNode, int endNode) {
		//nie jest mi to do niczego potrzebne
				return null;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public List<GraphNode> traverse(GraphNode startNode, GraphNode endNode) {

		startNode.setVisited(true);

		this.path.add(startNode);
		if (compare(((PuzzleNode) startNode).getPuzzle(),
				((PuzzleNode) endNode).getPuzzle())) {
			return this.path;

		} else {
			generateNeighbours(startNode);

			for (GraphEdge e : startNode.getNeighbours()) {

				if (!e.getSuccessor().isVisited()) {
					visitedEdges.add(e);
					return traverse(e.getSuccessor(), endNode);
				}

			}
		}
		visitedEdges.remove(visitedEdges.size() - 1);
		this.path.remove(startNode);
		System.out.println(path.size());
		return this.path;

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

		for (int i = 0; i < order.length(); i++) {
			// System.out.println(order.length());
			// System.out.println(backupNodes.size());
			if (startnode instanceof PuzzleNode) {
				Integer[][] newStateArray = arrayCopy(((PuzzleNode) startnode)
						.getPuzzle());
				Integer[] positions = findBlankPosition(newStateArray);

				// System.out.println(positions[0]+"  "+positions[1]);

				char direction = order.charAt(i);
				switch (direction) {

				case 'U': {
					if (positions[0] > 0) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0] - 1][positions[1]];
						newStateArray[positions[0] - 1][positions[1]] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
						backupNodes.add(tempNode);
						startnode.addNeighbour(tempNode,
								new UnvisitedAccessibleStrategy(), direction);
					}
					break;

				}
				case 'R': {
					if (positions[1] < newStateArray[0].length - 1) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0]][positions[1] + 1];
						newStateArray[positions[0]][positions[1] + 1] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
						backupNodes.add(tempNode);
						startnode.addNeighbour(tempNode,
								new UnvisitedAccessibleStrategy(), direction);

					}
					break;

				}
				case 'L': {
					if (positions[1] > 0) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0]][positions[1] - 1];
						newStateArray[positions[0]][positions[1] - 1] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
						backupNodes.add(tempNode);
						startnode.addNeighbour(tempNode,
								new UnvisitedAccessibleStrategy(), direction);

					}
					break;

				}
				case 'D': {
					if (positions[0] < newStateArray.length - 1) {
						newStateArray[positions[0]][positions[1]] = newStateArray[positions[0] + 1][positions[1]];
						newStateArray[positions[0] + 1][positions[1]] = 0;
						PuzzleNode tempNode = new PuzzleNode("", newStateArray);
						backupNodes.add(tempNode);
						startnode.addNeighbour(tempNode,
								new UnvisitedAccessibleStrategy(), direction);

					}
					break;

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
	public String getTurns()
	{
		StringBuilder str=new StringBuilder();
		for (GraphEdge e:visitedEdges)
		{
			str.append(" ");
			str.append(((Edge)e).getDirection());
		}
		return str.toString().trim();
	}

}
