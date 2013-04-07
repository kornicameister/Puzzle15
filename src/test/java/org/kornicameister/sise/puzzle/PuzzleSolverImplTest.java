package org.kornicameister.sise.puzzle;

import org.junit.Assert;
import org.junit.Test;
import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.puzzle.stratagies.BFSPuzzleStrategy;
import org.kornicameister.sise.puzzle.stratagies.InversionAccessibleNodeStrategy;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleSolverImplTest extends AbstractPuzzleTest {

    @Test
    public void testSolve() throws Exception {
        Graph graph = new Graph((new PuzzleNode("Init", "LPGD", AbstractPuzzleTest.SOLVABLE_PUZZLE_2)));
        graph.setStrategy(new BFSPuzzleStrategy());

        PuzzleSolverImpl puzzleSolverImpl = new PuzzleSolverImpl(graph);
        puzzleSolverImpl.setExamination(new InversionAccessibleNodeStrategy());

        Assert.assertThat(puzzleSolverImpl.isSolvable(), is(true));
        puzzleSolverImpl.solve();
        for (GraphNode node : graph.getPath()) {
            System.out.println(node);
        }
    }

    @Test
    public void testSolveFromFifteen() {
        Integer key = 0;
        int counter = 0;
        String[] order = {"L", "P", "G", "D"};
        while (!key.equals(8)) {                // BY-FAR currently acceptable computation time
            final List<Integer[][]> integers = this.puzzleMap.get(key);
            for (Integer[][] puzzle : integers) {

                // generate order for this pass
                Collections.shuffle(Arrays.asList(order));
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < order.length; i++) {
                    sb.append(order[i]);
                }

                System.out.println(String.format("Solving %s at [%d,%d,%s]", Arrays.deepToString(puzzle), key, counter++, sb.toString()));
                Graph graph = new Graph((new PuzzleNode("Init", sb.toString(), puzzle)));
                graph.setStrategy(new BFSPuzzleStrategy());

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
    public void testIsSolvable() throws Exception {
        System.out.println(String.format("Checkup for solvable %s", Arrays.deepToString(AbstractPuzzleTest.SOLVABLE_PUZZLE)));
        PuzzleSolverImpl graph = new PuzzleSolverImpl(new Graph(new PuzzleNode("Init", AbstractPuzzleTest.SOLVABLE_PUZZLE)));
        graph.setExamination(new InversionAccessibleNodeStrategy());
        Assert.assertThat(graph.isSolvable(), is(true));
    }

    @Test
    public void testIsNotSolvable() throws Exception {
        System.out.println(String.format("Checkup for non-solvable %s", Arrays.deepToString(AbstractPuzzleTest.NON_SOLVABLE_PUZZLE)));
        PuzzleSolverImpl graph = new PuzzleSolverImpl(new Graph(new PuzzleNode("Init", AbstractPuzzleTest.NON_SOLVABLE_PUZZLE)));
        graph.setExamination(new InversionAccessibleNodeStrategy());
        Assert.assertThat(graph.isSolvable(), is(false));
    }

    @Test
    public void testIsNotSolvable2() throws Exception {
        System.out.println(String.format("Checkup for non-solvable %s", Arrays.deepToString(AbstractPuzzleTest.NON_SOLVABLE_PUZZLE_2)));
        PuzzleSolverImpl graph = new PuzzleSolverImpl(new Graph(new PuzzleNode("Init", AbstractPuzzleTest.NON_SOLVABLE_PUZZLE_2)));
        graph.setExamination(new InversionAccessibleNodeStrategy());
        Assert.assertThat(graph.isSolvable(), is(false));
    }
}
