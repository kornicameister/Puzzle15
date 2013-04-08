package org.kornicameister.sise.puzzle;

import com.rits.cloning.Cloner;
import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.NodeAccessibleStrategy;
import org.kornicameister.sise.puzzle.core.PuzzleSolver;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class PuzzleSolverImpl implements PuzzleSolver {
    private NodeAccessibleStrategy examination;
    private Graph graph;

    public PuzzleSolverImpl(Graph graph) {
        this.graph = graph;
        this.generateFinalNode(graph.getNode(0));
    }

    public PuzzleSolverImpl(Graph graph, NodeAccessibleStrategy strategy) {
        this(graph);
        this.examination = strategy;
    }

    public void setExamination(NodeAccessibleStrategy examination) {
        this.examination = examination;
    }

    private void generateFinalNode(GraphNode node) {
        PuzzleNode puzzleNode = (PuzzleNode) new Cloner().deepClone(node);
        Integer[][] puzzle = puzzleNode.getPuzzle();
        int step = 1;
        for (int i = 0; i < puzzle.length; i++) {
            for (int j = 0; j < puzzle.length; j++) {
                puzzle[i][j] = step++;
                if (i == puzzle.length - 1 && j == puzzle.length - 1) {
                    puzzle[i][j] = 0;
                }
            }
        }
        this.graph.addNode(new PuzzleNode("Final", puzzle));
    }

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
        this.graph.traverse(this.graph.getNode(0), this.graph.getNode(1));
        System.gc();
    }

    @Override
    public final boolean isSolvable() {
        return this.examination.isAccessible(graph.getNodes().get(0));
    }

    @Override
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PuzzleSolverImpl");
        sb.append("{graph=").append(graph);
        sb.append('}');
        return sb.toString();
    }


    public NodeAccessibleStrategy getExamination() {
        return this.examination;
    }
}
