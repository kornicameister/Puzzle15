package org.kornicameister.sise.puzzle.edge;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BFSEdge implements GraphEdge {
    private Character direction;
    private BFSEdge predecessor;
    private PuzzleNode successor;
    private NodeAccessibleStrategy strategy;

    public BFSEdge(final BFSEdge predecessor,
                   final PuzzleNode successor,
                   final Character direction,
                   NodeAccessibleStrategy strategy) {
        this.predecessor = predecessor;
        this.successor = successor;
        this.direction = direction;
        this.strategy = strategy;
    }

    @Override
    public void setAccessibleStrategy(final NodeAccessibleStrategy strategy) {
        this.strategy = strategy;
    }

    @Override
    public PuzzleNode getSuccessor() {
        return this.successor;
    }

    public void setSuccessor(final PuzzleNode successor) {
        this.successor = successor;
    }

    @Override
    public boolean isAccessible() {
        if (this.predecessor == null) {
            return this.strategy.isAccessible(this.successor);
        }
        return this.strategy.isAccessible(this.predecessor.getSuccessor(), this.successor);
    }

    public Character getDirection() {
        return direction;
    }

    public void setDirection(final Character direction) {
        this.direction = direction;
    }

    public BFSEdge getPredecessor() {
        return predecessor;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("BFSEdge");
        sb.append("{super =").append(super.toString());
        sb.append("},");
        sb.append("{direction=").append(direction);
        sb.append(", predecessor=").append(predecessor);
        sb.append(", strategy=").append(strategy);
        sb.append(", successor=").append(successor);
        sb.append('}');
        return sb.toString();
    }
}
