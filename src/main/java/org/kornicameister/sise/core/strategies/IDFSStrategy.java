package org.kornicameister.sise.core.strategies;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;

import com.rits.cloning.Cloner;

public class IDFSStrategy  {

	private List<GraphNode> backupNodes;
    private List<GraphNode> path= new ArrayList<>();;
    
    public void init(List<GraphNode> nodes) {
        this.backupNodes = nodes;
    }

    
    public List<GraphNode> traverse(int startNode) {
        List<GraphNode> nodes = this.cloneNodes(this.backupNodes);
        for (GraphNode node : nodes) {
            if (node.equals(this.backupNodes.get(startNode))) {
                //return this.traverse(node);
            }
        }
        return null;
    }

    
    public List<GraphNode> traverse(int startNode, int endNode) {
        List<GraphNode> nodes = this.cloneNodes(this.backupNodes);
        GraphNode start = null, end = null;
        for (GraphNode node : nodes) {
            if (node.equals(this.backupNodes.get(startNode))) {
                start = node;
                break;
            }
        }
        for (GraphNode node : nodes) {
            if (node.equals(this.backupNodes.get(endNode))) {
                end = node;
                break;
            }
        }
        return this.traverse(start, end);
    }



    protected List<GraphNode> traverse(GraphNode startNode, GraphNode endNode) {
  

        startNode.setVisited(true);
        this.path.add(startNode);
        if (startNode.equals(endNode))
        {
        	return this.path;
        }
        for (GraphEdge e: startNode.getNeighbours())
        {
        	if (!e.getSuccessor().isVisited())
        	{
        		traverse(e.getSuccessor(),endNode);
        	}
        	if(e.getSuccessor().equals(endNode))
        		return this.path;
        	
        }
        this.path.remove(startNode);
        return this.path;
        

        
    }

    protected GraphNode getNextUnvisited(GraphNode node) {
        for (GraphEdge neighbour : node.getNeighbours()) {
            if (neighbour.isAccessible()) {
                return neighbour.getSuccessor();
            }
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

}


