package org.kornicameister.sise.puzzle.stratagies;

import com.rits.cloning.Cloner;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;
import org.kornicameister.sise.core.strategies.BFSStrategy;
import org.kornicameister.sise.puzzle.core.PuzzleNodeBuilder;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class BFSPuzzleStrategy extends BFSStrategy implements PuzzleNodeBuilder {
    private static final Logger LOGGER = Logger.getLogger(BFSPuzzleStrategy.class.getName());
    private static final String RANDOM_ORDER = "R";
    private static final Character[] MOVES = {'L', 'P', 'G', 'D'};
    private static Integer ID = 0;
    private final NodeAccessibleStrategy strategy;

    public BFSPuzzleStrategy() {
        LOGGER.setLevel(Level.INFO);
        this.strategy = new InversionAccessibleNodeStrategy();
    }

    @Override
    public GraphNode getNextAvailableNode(GraphNode node) {
        if (node.getNeighbours().size() == 0) {
            if (node instanceof PuzzleNode) {
                PuzzleNode puzzleNode = (PuzzleNode) node;
                final List<GraphNode> possibleNeighbours = this.getPossibleNeighbours(puzzleNode);
                if (possibleNeighbours.size() > 0) {
                    for (GraphNode puzzleNode1 : possibleNeighbours) {
                        puzzleNode.addNeighbour(puzzleNode1, this.strategy);
                    }
                }
            }
        }
        return super.getNextAvailableNode(node);
    }

    private Point findBlankField(PuzzleNode node) {
        Integer[][] puzzle = node.getPuzzle();
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle[0].length; j++) {
                if (puzzle[i][j].equals(0)) {
                    return new Point(i, j);
                }
            }
        }
        return null;
    }

    @Override
    public List<GraphNode> getPossibleNeighbours(GraphNode node) {
        List<GraphNode> puzzleNodeList = new ArrayList<>();
        PuzzleNode puzzleNode = (PuzzleNode) node;
        Integer[][] puzzle = puzzleNode.getPuzzle();
        Point fromCords = this.findBlankField(puzzleNode);
        Set<Character> moves = new HashSet<>();
        final String order = puzzleNode.getOrder();

        if (order.equals(RANDOM_ORDER)) {
            Random seed = new Random(System.nanoTime());
            for (int i = 0; i < 4; i++) {
                moves.add(MOVES[seed.nextInt(4)]);
            }
        } else {
            moves.add(order.charAt(0));
            moves.add(order.charAt(1));
            moves.add(order.charAt(2));
            moves.add(order.charAt(3));
        }

        LOGGER.fine(String.format("Generating new neighbours at moves=%s", moves));

        for (Character direction : moves) {
            switch (direction) {
                case 'L': {    //move to the left
                    final Point toCords = fromCords.newPointByOffset(0, -1);
                    LOGGER.fine(String.format("L -> trying to find neighbour from %s to %s", fromCords, toCords));
                    if (toCords.getY() >= 0) {

                        Integer[][] puzzleCopy = this.swapByIndex(
                                new Cloner().deepClone(puzzleNode).getPuzzle(),
                                fromCords,
                                toCords
                        );
                        puzzleNodeList.add(new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy));

                    } else {
                        LOGGER.fine(String.format("Another L move is impossible, index=[%s]", fromCords));
                    }
                }
                break;
                case 'P': {   //move to the right
                    final Point toCords = fromCords.newPointByOffset(0, 1);
                    LOGGER.fine(String.format("P -> trying to find neighbour from %s to %s", fromCords, toCords));
                    if (toCords.getY() < puzzle[0].length) {

                        Integer[][] puzzleCopy = this.swapByIndex(
                                new Cloner().deepClone(puzzleNode).getPuzzle(),
                                fromCords,
                                toCords
                        );
                        puzzleNodeList.add(new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy));

                    } else {
                        LOGGER.fine(String.format("Another P move is impossible, index=[%s]", fromCords));
                    }
                }
                break;
                case 'G': {    //move up
                    final Point toCords = fromCords.newPointByOffset(-1, 0);
                    LOGGER.fine(String.format("G -> trying to find neighbour from %s to %s", fromCords, toCords));
                    if (toCords.getX() >= 0) {

                        Integer[][] puzzleCopy = this.swapByIndex(
                                new Cloner().deepClone(puzzleNode).getPuzzle(),
                                fromCords,
                                toCords
                        );
                        puzzleNodeList.add(new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy));

                    } else {
                        LOGGER.fine(String.format("Another G move is impossible, index=[%s]", fromCords));
                    }
                }
                break;
                case 'D': {  //move down
                    final Point toCords = fromCords.newPointByOffset(1, 0);
                    LOGGER.fine(String.format("D -> trying to find neighbour from %s to %s", fromCords, toCords));
                    if (toCords.getX() < puzzle.length) {

                        Integer[][] puzzleCopy = this.swapByIndex(
                                new Cloner().deepClone(puzzleNode).getPuzzle(),
                                fromCords,
                                toCords
                        );
                        puzzleNodeList.add(new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy));

                    } else {
                        LOGGER.fine(String.format("Another D move is impossible, index=[%s]", fromCords));
                    }
                }
                break;
            }
        }
        return puzzleNodeList;
    }

    private Integer[][] swapByIndex(Integer[][] target, Point from, Point to) {
        int fX = from.getX(),
                fY = from.getY(),
                tX = to.getX(),
                tY = to.getY();

        Integer temp = target[fX][fY];
        target[fX][fY] = target[tX][tY];
        target[tX][tY] = temp;

        return target;
    }

    private class Point {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Point newPointByOffset(int x, int y) {
            return new Point(this.x + x, this.y + y);
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder();
            sb.append("Point");
            sb.append("{x=").append(x);
            sb.append(", y=").append(y);
            sb.append('}');
            return sb.toString();
        }
    }
}
