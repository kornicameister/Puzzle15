package org.kornicameister.sise.core.strategies;

import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;
import org.kornicameister.sise.puzzle.node.PuzzleNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class AStarHeuristicsStrategy implements GraphSearchStrategy {

    final int MOVE_UP = 0;
    final int MOVE_RIGHT = 1;
    final int MOVE_DOWN = 2;
    final int MOVE_LEFT = 3;
    final int FIRST = MOVE_UP;
    final int LAST = MOVE_LEFT;
    final String[] directions = {"G", "P", "D", "L"};
    private List<GraphNode> backupNodes;
    private PuzzleNode solution;
    private int measuremnt;
    private HashMap<Long, Integer> opens = new HashMap<Long, Integer>();
    private HashSet<Long> closed = new HashSet<Long>();
    private int idHeuristic;


    public AStarHeuristicsStrategy(int idHeuristic) {
        this.idHeuristic = idHeuristic;

    }

    @Override
    public void init(List<GraphNode> nodes) {
        this.backupNodes = new ArrayList<>();
        this.backupNodes = nodes;
        measuremnt = (int) Math.sqrt(nodes.size());

        Integer[][] initStart = new Integer[measuremnt][measuremnt];
        int temp = 1;
        for (int i = 1; i < measuremnt; i++) {
            for (int j = 0; j < measuremnt; j++) {
                initStart[i][j] = temp;
                temp++;
            }
        }
        initStart[measuremnt - 1][measuremnt - 1] = 0;
        solution = new PuzzleNode("", initStart);
    }

    @Override
    public String getReport() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public GraphNode getNextAvailableNode(GraphNode node) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<GraphNode> traverse(GraphNode startNode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<GraphNode> traverse(GraphNode startNode, GraphNode endNode) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int heuretystyka(List<GraphNode> start, List<GraphNode> end) {
        if (idHeuristic == 1) {
            return countInvalid(start, end);
        }
        return distance(start, end);
    }

    private int countInvalid(List<GraphNode> start, List<GraphNode> end) {
        int iloscNiepoprawnych = 0;
        for (int i = 0; i < end.size() - 1; i++)
            if (!compare(((PuzzleNode) start.get(i)).getPuzzle(),
                    ((PuzzleNode) end.get(i)).getPuzzle())) iloscNiepoprawnych++;
        return iloscNiepoprawnych;


    }

    private int distance(List<GraphNode> start, List<GraphNode> end) {
        int distance = 0;
        for (int i = 1; i < start.size(); i++) { //0 czyli puste pole pomijamy
            int temp = Math.abs(start.indexOf(i) - end.indexOf(i));
            distance += (temp % measuremnt) + (temp / measuremnt);
        }
        return distance;
    }

    boolean compare(Integer[][] a, Integer[][] b) {
        boolean result = true;
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                if (a[i][j] != b[i][j]) {
                    return false;

                }
            }
        }
        return result;
    }

}
