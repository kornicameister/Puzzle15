package org.kornicameister.sise.puzzle.builder;

import com.rits.cloning.Cloner;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.puzzle.core.PuzzleNodeBuilder;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.utilities.ArrayUtilities;
import org.kornicameister.sise.utilities.Point;

import java.util.*;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleNeighborsBuilder implements PuzzleNodeBuilder {
    private static final String RANDOM_ORDER = "R";
    private static final Character[] MOVES = {'L', 'P', 'G', 'D'};
    private static Integer ID = 0;
    private final int originalNodesSize;
    private List<GraphNode> generatedNodes = new ArrayList<>();

    public PuzzleNeighborsBuilder(List<GraphNode> originalNodes) {
        this.generatedNodes.addAll(originalNodes);
        this.originalNodesSize = originalNodes.size();
    }

    @Override
    public Map<Character, GraphNode> getPossibleNeighbours(GraphNode node) {
        Map<Character, GraphNode> puzzleNodeList = new HashMap<>();
        PuzzleNode puzzleNode = (PuzzleNode) node;
        Integer[][] puzzle = puzzleNode.getPuzzle();
        Point fromCords = puzzleNode.getBlankFieldCords();
        final String order = puzzleNode.getOrder();

        for (Character direction : this.generateMoves(order)) {
            switch (direction) {
                case 'L':
                    this.moveToTheLeft(puzzleNodeList, puzzleNode, fromCords, order, direction);
                    break;
                case 'P':
                    this.moveToTheRight(puzzleNodeList, puzzleNode, puzzle[0], fromCords, order, direction);
                    break;
                case 'G':
                    this.moveUp(puzzleNodeList, puzzleNode, fromCords, order, direction);
                    break;
                case 'D':
                    this.moveDown(puzzleNodeList, puzzleNode, puzzle, fromCords, order, direction);
                    break;
            }
        }

        return puzzleNodeList;
    }

    private void moveUp(final Map<Character, GraphNode> puzzleNodeList, final PuzzleNode puzzleNode, final Point fromCords, final String order, final Character direction) {
        final Point toCords = fromCords.newPointByOffset(-1, 0);
        if (toCords.getX() >= 0) {

            Integer[][] puzzleCopy = ArrayUtilities.swapByIndex(
                    new Cloner().deepClone(puzzleNode).getPuzzle(),
                    fromCords,
                    toCords
            );

            final PuzzleNode newNode = new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy);

            if (this.generatedNodes.contains(newNode)) {
                puzzleNodeList.put(direction, this.generatedNodes.get(this.generatedNodes.indexOf(newNode)));
            } else {
                this.generatedNodes.add(newNode);
                puzzleNodeList.put(direction, newNode);
            }

        }
    }

    private void moveToTheLeft(final Map<Character, GraphNode> puzzleNodeList, final PuzzleNode puzzleNode, final Point fromCords, final String order, final Character direction) {
        final Point toCords = fromCords.newPointByOffset(0, -1);
        if (toCords.getY() >= 0) {

            Integer[][] puzzleCopy = ArrayUtilities.swapByIndex(
                    new Cloner().deepClone(puzzleNode).getPuzzle(),
                    fromCords,
                    toCords
            );

            final PuzzleNode newNode = new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy);

            if (this.generatedNodes.contains(newNode)) {
                puzzleNodeList.put(direction, this.generatedNodes.get(this.generatedNodes.indexOf(newNode)));
            } else {
                this.generatedNodes.add(newNode);
                puzzleNodeList.put(direction, newNode);
            }

        }
    }

    private void moveToTheRight(final Map<Character, GraphNode> puzzleNodeList, final PuzzleNode puzzleNode, final Integer[] integers, final Point fromCords, final String order, final Character direction) {
        final Point toCords = fromCords.newPointByOffset(0, 1);
        if (toCords.getY() < integers.length) {

            Integer[][] puzzleCopy = ArrayUtilities.swapByIndex(
                    new Cloner().deepClone(puzzleNode).getPuzzle(),
                    fromCords,
                    toCords
            );

            final PuzzleNode newNode = new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy);

            if (this.generatedNodes.contains(newNode)) {
                puzzleNodeList.put(direction, this.generatedNodes.get(this.generatedNodes.indexOf(newNode)));
            } else {
                this.generatedNodes.add(newNode);
                puzzleNodeList.put(direction, newNode);
            }

        }
    }

    private void moveDown(final Map<Character, GraphNode> puzzleNodeList, final PuzzleNode puzzleNode, final Integer[][] puzzle, final Point fromCords, final String order, final Character direction) {
        final Point toCords = fromCords.newPointByOffset(1, 0);
        if (toCords.getX() < puzzle.length) {

            Integer[][] puzzleCopy = ArrayUtilities.swapByIndex(
                    new Cloner().deepClone(puzzleNode).getPuzzle(),
                    fromCords,
                    toCords
            );

            final PuzzleNode newNode = new PuzzleNode(String.format("PuzzleNode-gen-%d", ID++), order, puzzleCopy);

            if (this.generatedNodes.contains(newNode)) {
                puzzleNodeList.put(direction, this.generatedNodes.get(this.generatedNodes.indexOf(newNode)));
            } else {
                this.generatedNodes.add(newNode);
                puzzleNodeList.put(direction, newNode);
            }

        }
    }

    private List<Character> generateMoves(final String order) {
        List<Character> moves = new ArrayList<>();
        if (order.equals(RANDOM_ORDER)) {
            Character[] movesRandom = MOVES.clone();
            Collections.shuffle(Arrays.asList(movesRandom));
            moves.addAll(Arrays.asList(MOVES).subList(0, 4));
        } else {
            moves.add(order.charAt(0));
            moves.add(order.charAt(1));
            moves.add(order.charAt(2));
            moves.add(order.charAt(3));
        }
        return moves;
    }

    public int getGeneratedNodes() {
        return generatedNodes.size() - this.originalNodesSize;
    }

}
