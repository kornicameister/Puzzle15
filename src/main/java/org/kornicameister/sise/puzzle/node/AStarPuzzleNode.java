package org.kornicameister.sise.puzzle.node;

import org.kornicameister.sise.core.graph.GraphEdge;
import org.kornicameister.sise.puzzle.edge.AStarEdge;
import org.kornicameister.sise.utilities.Point;

import java.util.ArrayList;
import java.util.List;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AStarPuzzleNode extends PuzzleNode {
    private static final Integer BLANK_FIELD = 0;
    private final List<AStarEdge> edgeList = new ArrayList<>();
    private Point blankFieldCords;

    public AStarPuzzleNode(Integer id, String label, String order, Integer[][] puzzle, GraphEdge... neighbours) {
        this(label, order, puzzle, neighbours);
        this.id = id;
    }

    public AStarPuzzleNode(String label, String order, Integer[][] puzzle, GraphEdge... neighbours) {
        this(label, puzzle, neighbours);
        this.setOrder(order);
    }

    public AStarPuzzleNode(String label, Integer[][] puzzle, GraphEdge... neighbours) {
        super(label, puzzle, neighbours);
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

    public boolean addStartNeighbour(AStarEdge graphEdge) {
        return this.edgeList.add(graphEdge);
    }

    public List<AStarEdge> getStarNeighbours() {
        return this.edgeList;
    }

    public int getY() {
        return this.blankFieldCords.getY();
    }

    public int getX() {
        return this.blankFieldCords.getX();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("AStarPuzzleNode");
        sb.append("{super =").append(super.toString());
        sb.append("},");
        sb.append("{blankFieldCords=").append(blankFieldCords);
        sb.append('}');
        return sb.toString();
    }
}
