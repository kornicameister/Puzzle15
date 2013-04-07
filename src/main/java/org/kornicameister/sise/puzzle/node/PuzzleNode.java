package org.kornicameister.sise.puzzle.node;

import org.kornicameister.sise.core.Node;
import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.utilities.Point;

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
    private static final Integer BLANK_FIELD = 0;
    protected final Integer[][] puzzle;
    private Point blankFieldCords;
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
        this.calculateXY();
    }

    private void calculateXY() {
        final Integer[][] puzzle = this.puzzle;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                if (puzzle[i][j].equals(BLANK_FIELD)) {
                    this.blankFieldCords = new Point(i, j);
                }
            }
        }
    }

    public PuzzleNode(Integer id, String label, String order, Integer[][] puzzle, GraphEdge... neighbours) {
        this(label, order, puzzle, neighbours);
        this.id = id;
    }

    public int getX() {
        return this.blankFieldCords.getX();
    }

    public int getY() {
        return this.blankFieldCords.getY();
    }

    public Point getBlankFieldCords() {
        return this.blankFieldCords;
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
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + Arrays.deepHashCode(this.puzzle);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (!(o instanceof PuzzleNode)) {
            return false;
        }

        PuzzleNode that = (PuzzleNode) o;
        return Arrays.deepEquals(this.puzzle, that.puzzle);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PuzzleNode");
        sb.append("{super =").append(super.toString());
        sb.append("},");
        sb.append("{blankFieldCords=").append(blankFieldCords);
        sb.append(", order='").append(order).append('\'');
        sb.append(", puzzle=").append(puzzle == null ? "null" : Arrays.asList(puzzle).toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int compareTo(Object object) {
        int result = super.compareTo(object);
        if (result == 0) {
            PuzzleNode node = (PuzzleNode) object;
            Integer thisPuzzleHashCode = Arrays.deepHashCode(this.puzzle);
            Integer thatPuzzleHashCode = Arrays.deepHashCode(node.puzzle);
            result = thisPuzzleHashCode.compareTo(thatPuzzleHashCode);
        }
        return result;
    }


}
