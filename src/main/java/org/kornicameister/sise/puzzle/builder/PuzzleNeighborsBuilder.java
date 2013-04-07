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
    private List<GraphNode> generatedNodes = new ArrayList<>();

    public PuzzleNeighborsBuilder(List<GraphNode> originalNodes) {
        this.generatedNodes.addAll(originalNodes);
    }

    @Override
    public Map<Character, GraphNode> getPossibleNeighbours(GraphNode node) {
        Map<Character, GraphNode> puzzleNodeList = new HashMap<>();
        PuzzleNode puzzleNode = (PuzzleNode) node;
        Integer[][] puzzle = puzzleNode.getPuzzle();
        Point fromCords = puzzleNode.getBlankFieldCords();
        List<Character> moves = new ArrayList<>();
        final String order = puzzleNode.getOrder();

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

        for (Character direction : moves) {
            switch (direction) {
                case 'L': {    //move to the left
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
                break;
                case 'P': {   //move to the right
                    final Point toCords = fromCords.newPointByOffset(0, 1);
                    if (toCords.getY() < puzzle[0].length) {

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
                break;
                case 'G': {    //move up
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
                break;
                case 'D': {  //move down
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
                break;
            }
        }

        return puzzleNodeList;
    }

    public int getGeneratedNodes() {
        return generatedNodes.size();
    }

}
