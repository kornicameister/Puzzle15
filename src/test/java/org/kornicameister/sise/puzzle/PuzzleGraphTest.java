package org.kornicameister.sise.puzzle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleGraphTest {
    private final static Integer[][] SOLVABLE_PUZZLE = new Integer[][]{
            {12, 1, 10, 2},
            {7, 11, 4, 14},
            {5, 0, 9, 15},
            {8, 13, 6, 3}
    };
    private final static Integer[][] NON_SOLVABLE_PUZZLE = new Integer[][]{
            {12, 1, 10, 2},
            {7, 11, 4, 0},
            {5, 14, 9, 3},
            {8, 13, 6, 15}
    };

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testSolve() throws Exception {

    }

    @Test
    public void testIsSolvable() throws Exception {
        PuzzleGraph graph = new PuzzleGraph();
        graph.setGraph(new Graph(new PuzzleNode("Init", SOLVABLE_PUZZLE)));
        Assert.assertThat(graph.isSolvable(), is(true));
    }

    @Test
    public void testIsNotSolvable() throws Exception {
        PuzzleGraph graph = new PuzzleGraph();
        graph.setGraph(new Graph(new PuzzleNode("Init", NON_SOLVABLE_PUZZLE)));
        Assert.assertThat(graph.isSolvable(), is(false));
    }
}
