package org.kornicameister.sise.puzzle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.puzzle.stratagies.BFSPuzzleStrategy;
import org.kornicameister.sise.puzzle.stratagies.InversionAccessibleNodeStrategy;

import java.io.File;
import java.util.*;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleSolverImplTest {
    private final static String FIVETEEN = "src/main/resources/fifteen";
    private final static Integer[][] SOLVABLE_PUZZLE = new Integer[][]{
            {12, 1, 10, 2},
            {7, 11, 4, 14},
            {5, 0, 9, 15},
            {8, 13, 6, 3}
    };
    private final static Integer[][] SOLVABLE_PUZZLE_2 = new Integer[][]{
            {1, 2, 3},
            {4, 5, 6},
            {0, 7, 8}
    };
    private final static Integer[][] NON_SOLVABLE_PUZZLE = new Integer[][]{
            {12, 1, 10, 2},
            {7, 11, 4, 0},
            {5, 14, 9, 3},
            {8, 13, 6, 15}
    };
    private final static Integer[][] NON_SOLVABLE_PUZZLE_2 = new Integer[][]{
            {13, 10, 11, 6},
            {5, 7, 4, 8},
            {1, 12, 14, 9},
            {3, 15, 2, 0}
    };
    protected Map<Integer, List<Integer[][]>> puzzleMap = new HashMap<>();

    @Before
    public void setUp() throws Exception {
        File folder = new File(FIVETEEN);
        if (!folder.exists()) {
            System.err.println("FIVETEEN folder does not exist");
            return;
        }
        Map<Integer, List<Integer[][]>> puzzleMap = new HashMap<>();
        Integer moves;
        Integer[][] puzzle;
        List<Integer[][]> puzzles;
        if (folder.isDirectory()) {
            for (File puzzleDir : folder.listFiles()) {

                moves = Integer.valueOf(puzzleDir.getName());
                puzzles = new ArrayList<>();

                for (File puzzleArray : puzzleDir.listFiles()) {
                    Scanner scanner = new Scanner(puzzleArray);
                    int width = scanner.nextInt(),
                            height = scanner.nextInt();
                    puzzle = new Integer[width][height];
                    for (int i = 0; i < width; i++) {
                        for (int j = 0; j < height; j++) {
                            puzzle[i][j] = scanner.nextInt();
                        }
                    }
                    puzzles.add(puzzle);
                }
                puzzleMap.put(moves, puzzles);
            }
        }
        this.puzzleMap = puzzleMap;
    }

    @Test
    public void testSolve() throws Exception {
        Graph graph = new Graph((new PuzzleNode("Init", "LPGD", SOLVABLE_PUZZLE_2)));
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
        while (!key.equals(9)) {                // BY-FAR currently acceptable computation time
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
        System.out.println(String.format("Checkup for solvable %s", Arrays.deepToString(SOLVABLE_PUZZLE)));
        PuzzleSolverImpl graph = new PuzzleSolverImpl(new Graph(new PuzzleNode("Init", SOLVABLE_PUZZLE)));
        graph.setExamination(new InversionAccessibleNodeStrategy());
        Assert.assertThat(graph.isSolvable(), is(true));
    }

    @Test
    public void testIsNotSolvable() throws Exception {
        System.out.println(String.format("Checkup for non-solvable %s", Arrays.deepToString(NON_SOLVABLE_PUZZLE)));
        PuzzleSolverImpl graph = new PuzzleSolverImpl(new Graph(new PuzzleNode("Init", NON_SOLVABLE_PUZZLE)));
        graph.setExamination(new InversionAccessibleNodeStrategy());
        Assert.assertThat(graph.isSolvable(), is(false));
    }

    @Test
    public void testIsNotSolvable2() throws Exception {
        System.out.println(String.format("Checkup for non-solvable %s", Arrays.deepToString(NON_SOLVABLE_PUZZLE_2)));
        PuzzleSolverImpl graph = new PuzzleSolverImpl(new Graph(new PuzzleNode("Init", NON_SOLVABLE_PUZZLE_2)));
        graph.setExamination(new InversionAccessibleNodeStrategy());
        Assert.assertThat(graph.isSolvable(), is(false));
    }
}
