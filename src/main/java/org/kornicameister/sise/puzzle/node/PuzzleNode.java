package org.kornicameister.sise.puzzle.node;

import org.kornicameister.sise.core.Node;
import org.kornicameister.sise.core.graph.GraphEdge;

import java.util.Arrays;

/**
 * This class represents puzzle node.
 * Each node represents particular
 * state of the puzzle by an 2D array.
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleNode extends Node {
    private final Integer[][] puzzle;
    private String order;

    public PuzzleNode(String label,
                      Integer[][] puzzle,
                      GraphEdge... neighbours) {
        this(label, "R", puzzle, neighbours);
    }

    public PuzzleNode(String label,
                      String order,
                      Integer[][] puzzle,
                      GraphEdge... neighbours) {
        super(label, neighbours);
        this.order = order;
        this.puzzle = puzzle;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer[][] getPuzzle() {
        return puzzle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PuzzleNode)) return false;
        if (!super.equals(o)) return false;

        PuzzleNode that = (PuzzleNode) o;

        return order.equals(that.order)
                && Arrays.deepEquals(this.puzzle, that.puzzle);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + order.hashCode();
        result = 31 * result + Arrays.deepHashCode(this.puzzle);
        return result;
    }

    @Override
    public int compareTo(Object object) {
        int result = super.compareTo(object);
        PuzzleNode node = (PuzzleNode) object;
        if (result == 0) {
            Integer thisPuzzleHashCode = Arrays.deepHashCode(this.puzzle);
            Integer thatPuzzleHashCode = Arrays.deepHashCode(node.puzzle);
            result = thisPuzzleHashCode.compareTo(thatPuzzleHashCode);
        }
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PuzzleNode");
        sb.append("{super =").append(super.toString());
        sb.append("},");
        sb.append("{order='").append(order).append('\'');
        sb.append(", puzzle=").append(puzzle == null ? "null" : Arrays.asList(puzzle).toString());
        sb.append('}');
        return sb.toString();
    }
}
