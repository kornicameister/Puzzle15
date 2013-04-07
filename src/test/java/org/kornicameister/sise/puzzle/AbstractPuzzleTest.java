package org.kornicameister.sise.puzzle;

import org.junit.Before;

import java.io.File;
import java.util.*;

public class AbstractPuzzleTest {
    protected final static String FIVETEEN = "src/main/resources/fifteen";
    protected final static Integer[][] SOLVABLE_PUZZLE = new Integer[][]{
            {12, 1, 10, 2},
            {7, 11, 4, 14},
            {5, 0, 9, 15},
            {8, 13, 6, 3}
    };
    protected final static Integer[][] SOLVABLE_PUZZLE_2 = new Integer[][]{
            {1, 2, 3},
            {4, 5, 6},
            {0, 7, 8}
    };
    protected final static Integer[][] NON_SOLVABLE_PUZZLE = new Integer[][]{
            {12, 1, 10, 2},
            {7, 11, 4, 0},
            {5, 14, 9, 3},
            {8, 13, 6, 15}
    };
    protected final static Integer[][] NON_SOLVABLE_PUZZLE_2 = new Integer[][]{
            {13, 10, 11, 6},
            {5, 7, 4, 8},
            {1, 12, 14, 9},
            {3, 15, 2, 0}
    };
    protected Map<Integer, List<Integer[][]>> puzzleMap = new HashMap<Integer, List<Integer[][]>>();

    public AbstractPuzzleTest() {
    }

    @Before
    public void setUp() throws Exception {
        File folder = new File(FIVETEEN);
        if (!folder.exists()) {
            System.err.println("FIVETEEN folder does not exist");
            return;
        }
        Map<Integer, List<Integer[][]>> puzzleMap = new HashMap<Integer, List<Integer[][]>>();
        Integer moves;
        Integer[][] puzzle;
        List<Integer[][]> puzzles;
        if (folder.isDirectory()) {
            for (File puzzleDir : folder.listFiles()) {

                moves = Integer.valueOf(puzzleDir.getName());
                puzzles = new ArrayList<Integer[][]>();

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
}