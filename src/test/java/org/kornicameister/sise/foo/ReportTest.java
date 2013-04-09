package org.kornicameister.sise.foo;

import org.junit.Assert;
import org.junit.Test;
import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.puzzle.AbstractPuzzleTest;
import org.kornicameister.sise.puzzle.PuzzleSolverImpl;
import org.kornicameister.sise.puzzle.heuristic.PuzzleCountInvalidHeuristics;
import org.kornicameister.sise.puzzle.heuristic.PuzzleManhattanHeuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.puzzle.stratagies.AStarPuzzleStrategy;
import org.kornicameister.sise.puzzle.stratagies.InversionAccessibleNodeStrategy;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class ReportTest extends AbstractPuzzleTest {
    @Test
    public void testManhattan() throws Exception {
        Integer key = 1;
        String[] orders = {"LPGD", "DLPG", "PGDL", "DGLP"};
        while (!key.equals(7)) {

            final List<Integer[][]> integers = this.puzzleMap.get(key);
            Integer[][] puzzle = integers.get(new Random().nextInt(integers.size()));
            for (String order : orders) {
                System.out.println(String.format("Solution for distance %d and order %s,\npuzzle=%s",
                        key,
                        order,
                        Arrays.deepToString(puzzle))
                );
                Graph graph = new Graph((new PuzzleNode("Init", order, puzzle)));
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
            key++;
        }

    }

    @Test
    public void testManhattan2() throws Exception {
        Integer key = 7;
        final List<Integer[][]> integers = this.puzzleMap.get(key);
        for (int i = 0; i < 10; i++) {
            Integer[][] puzzle = integers.get(i);
            System.out.println(String.format("Solution %d for distance %d and order %s,\npuzzle=%s",
                    i + 1,
                    key,
                    "R",
                    Arrays.deepToString(puzzle))
            );
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
    }

    @Test
    public void testInvalid() throws Exception {
        Integer key = 1;
        String[] orders = {"LPGD", "DLPG", "PGDL", "DGLP"};
        while (!key.equals(7)) {

            final List<Integer[][]> integers = this.puzzleMap.get(key);
            Integer[][] puzzle = integers.get(new Random().nextInt(integers.size()));
            for (String order : orders) {
                System.out.println(String.format("Solution for distance %d and order %s,\npuzzle=%s",
                        key,
                        order,
                        Arrays.deepToString(puzzle))
                );
                Graph graph = new Graph((new PuzzleNode("Init", order, puzzle)));
                graph.setStrategy(new AStarPuzzleStrategy(new PuzzleCountInvalidHeuristics()));

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
            key++;
        }
    }

    @Test
    public void testInvalid2() throws Exception {
        Integer key = 7;
        final List<Integer[][]> integers = this.puzzleMap.get(key);
        for (int i = 0; i < 10; i++) {
            Integer[][] puzzle = integers.get(i);
            System.out.println(String.format("Solution %d for distance %d and order %s,\npuzzle=%s",
                    i + 1,
                    key,
                    "R",
                    Arrays.deepToString(puzzle))
            );
            Graph graph = new Graph((new PuzzleNode("Init", "R", puzzle)));
            graph.setStrategy(new AStarPuzzleStrategy(new PuzzleCountInvalidHeuristics()));

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
    }
}
