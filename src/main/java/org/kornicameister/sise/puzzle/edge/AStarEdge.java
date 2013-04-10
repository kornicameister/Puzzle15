package org.kornicameister.sise.puzzle.edge;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AStarEdge implements GraphEdge, Comparable<AStarEdge> {
    private AStarEdge predecessor;
    private PuzzleNode successor;
    private Double gCost;
    private Double hCost;
    private Character direction;

    public AStarEdge(PuzzleNode successor, Double gCost, Double hCost) {
        this(null, successor, gCost, hCost, '!');
    }

    public AStarEdge(AStarEdge predecessor, PuzzleNode successor, Double gCost, Double hCost, Character direction) {
        this.predecessor = predecessor;
        this.successor = successor;
        this.gCost = gCost;
        this.hCost = hCost;
        this.direction = direction;
    }

    public AStarEdge(AStarEdge predecessor, PuzzleNode successor, Character direction) {
        this(predecessor, successor, 0.0, 0.0, direction);
    }

    public AStarEdge(AStarEdge predecessor, PuzzleNode successor) {
        this(predecessor, successor, 0.0, 0.0, '!');
    }

    @Override
    public void setAccessibleStrategy(NodeAccessibleStrategy strategy) {

    }

    @Override
    public PuzzleNode getSuccessor() {
        return this.successor;
    }

    public void setSuccessor(PuzzleNode successor) {
        this.successor = successor;
    }

    @Override
    public boolean isAccessible() {
        return true;
    }

    public AStarEdge getPredecessor() {
        return this.predecessor;
    }

    public void setPredecessor(AStarEdge predecessor) {
        this.predecessor = predecessor;
    }

    public Integer getID() {
        return this.successor.getID();
    }

    public Double getGCost() {
        return gCost;
    }

    public void setGCost(Double gCost) {
        this.gCost = gCost;
    }

    public Double getHCost() {
        return hCost;
    }

    public void setHCost(Double hCost) {
        this.hCost = hCost;
    }

    @Override
    public int compareTo(AStarEdge aStarEdge) {
        return this.getFCost().compareTo(aStarEdge.getFCost());
    }

    public Double getFCost() {
        return this.gCost + this.hCost;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AStarEdge");
        sb.append("{id=").append(this.successor.getID());
        sb.append(", predecessor=").append(predecessor == null ? "none" : predecessor);
        sb.append(", successor=").append(successor);
        sb.append(", gCost=").append(gCost);
        sb.append(", hCost=").append(hCost);
        sb.append(", fCost=").append(this.getFCost());
        sb.append('}');
        return sb.toString();
    }

    public Character getDirection() {
        return direction;
    }
}
