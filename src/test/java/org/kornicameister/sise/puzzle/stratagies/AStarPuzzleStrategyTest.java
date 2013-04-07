package org.kornicameister.sise.puzzle.stratagies;

import org.junit.Assert;
import org.junit.Test;
import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.puzzle.AbstractPuzzleTest;
import org.kornicameister.sise.puzzle.PuzzleSolverImpl;
import org.kornicameister.sise.puzzle.heuristic.PuzzleManhattanHeuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class AStarPuzzleStrategyTest extends AbstractPuzzleTest {

    @Test
    public void testAStrategyWithFixedOrder() {
        Integer key = -1;
        int counter = 0;
        String[] order = {"L", "P", "G", "D"};
        while (!key.equals(21)) {                // BY-FAR currently acceptable computation time
            final List<Integer[][]> integers = this.puzzleMap.get(key);
            for (Integer[][] puzzle : integers) {

                // generate order for this pass
                Collections.shuffle(Arrays.asList(order));
                StringBuilder sb = new StringBuilder();
                for (String anOrder : order) {
                    sb.append(anOrder);
                }

                System.out.println(String.format("Solving %s at [%d,%d,%s]", Arrays.deepToString(puzzle), key, counter++, sb.toString()));
                Graph graph = new Graph((new PuzzleNode("Init", sb.toString(), puzzle)));
                graph.setStrategy(new AStarPuzzleStrategy(new PuzzleManhattanHeuristic()));

                PuzzleSolverImpl puzzleSolverImpl = new PuzzleSolverImpl(graph);
                puzzleSolverImpl.setExamination(new InversionAccessibleNodeStrategy());

                Assert.assertThat(puzzleSolverImpl.isSolvable(), is(true));
                puzzleSolverImpl.solve();
                if (graph.getPath() == null) {
                    System.out.println("Failed to locate solution");
                } else {
                    System.out.println(graph.getStrategy().getReport());
                }
            }
            counter = 0;
            key++;
        }
    }

    @Test
    public void testAStrategyWithRandomOrder() {
        Integer key = 0;
        int counter = 0;
        while (!key.equals(11)) {                // BY-FAR currently acceptable computation time
            final List<Integer[][]> integers = this.puzzleMap.get(key);
            for (Integer[][] puzzle : integers) {

                System.out.println(String.format("Solving %s at [%d,%d,%s]", Arrays.deepToString(puzzle), key, counter++, "R"));
                Graph graph = new Graph((new PuzzleNode("Init", "R", puzzle)));
                graph.setStrategy(new AStarPuzzleStrategy(new PuzzleManhattanHeuristic()));

                PuzzleSolverImpl puzzleSolverImpl = new PuzzleSolverImpl(graph);
                puzzleSolverImpl.setExamination(new InversionAccessibleNodeStrategy());

                Assert.assertThat(puzzleSolverImpl.isSolvable(), is(true));
                puzzleSolverImpl.solve();
                if (graph.getPath() == null) {
                    System.out.println("Failed to locate solution");
                } else {
                    System.out.println(graph.getStrategy().getReport());
                }
            }
            counter = 0;
            key++;
        }
    }
}
