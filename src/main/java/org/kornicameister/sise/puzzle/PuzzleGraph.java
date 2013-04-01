package org.kornicameister.sise.puzzle;

import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.puzzle.exception.PuzzleBlankFilledMissing;
import org.kornicameister.sise.puzzle.exception.PuzzleNotSolvableException;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.puzzle.stratagies.InversionAccessibleNodeStrategy;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleGraph implements PuzzleSolver {
    private final Logger LOGGER = Logger.getLogger(PuzzleGraph.class.getName());
    private final InversionAccessibleNodeStrategy examination = new InversionAccessibleNodeStrategy();
    private Graph graph;

    @Override
    public void setOrder(String order) {
        for (GraphNode node : this.graph.getNodes()) {
            if (node instanceof PuzzleNode) {
                PuzzleNode puzzleNode = (PuzzleNode) node;
                puzzleNode.setOrder(order);
            }
        }
    }

    @Override
    public void solve() {
        if (this.isSolvable()) {
            LOGGER.info("Puzzle is solvable, proceeding");
        }
        throw new PuzzleNotSolvableException(graph.getNodes().get(0));
    }

    @Override
    public final boolean isSolvable() {
        try {
            return this.examination.isSolvable(graph.getNodes().get(0));
        } catch (PuzzleBlankFilledMissing puzzleBlankFilledMissing) {
            LOGGER.log(Level.SEVERE, "Puzzle with no blank field, bad...", puzzleBlankFilledMissing);
        }
        return false;
    }

    @Override
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PuzzleGraph");
        sb.append("{graph=").append(graph);
        sb.append('}');
        return sb.toString();
    }


}
