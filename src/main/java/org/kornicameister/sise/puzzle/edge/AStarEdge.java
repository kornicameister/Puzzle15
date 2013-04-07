package org.kornicameister.sise.puzzle.edge;

import org.kornicameister.sise.puzzle.node.AStarPuzzleNode;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AStarEdge implements Comparable<AStarEdge> {
    private AStarEdge predecessor;
    private AStarPuzzleNode successor;
    private Double gCost;
    private Double hCost;

    public AStarEdge(AStarPuzzleNode successor, Double gCost, Double hCost) {
        this(null, successor, gCost, hCost);
    }

    public AStarEdge(AStarEdge predecessor, AStarPuzzleNode successor, Double gCost, Double hCost) {
        this.predecessor = predecessor;
        this.successor = successor;
        this.gCost = gCost;
        this.hCost = hCost;
    }

    public AStarEdge(AStarEdge predecessor, AStarPuzzleNode successor) {
        this(predecessor, successor, 0.0, 0.0);
    }

    public AStarPuzzleNode getSuccessor() {
        return this.successor;
    }

    public void setSuccessor(AStarPuzzleNode successor) {
        this.successor = successor;
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
        sb.append('}');
        return sb.toString();
    }
}
