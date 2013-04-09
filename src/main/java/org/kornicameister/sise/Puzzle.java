package org.kornicameister.sise;

import org.kornicameister.sise.core.Graph;
import org.kornicameister.sise.core.Graphs;
import org.kornicameister.sise.core.graph.GraphNode;
import org.kornicameister.sise.core.graph.GraphSearchStrategy;
import org.kornicameister.sise.core.heuristic.Heuristic;
import org.kornicameister.sise.core.io.CLIArguments;
import org.kornicameister.sise.core.io.CLIWrapper;
import org.kornicameister.sise.core.strategies.DFSStrategy;
import org.kornicameister.sise.core.strategies.IDFSStrategy;
import org.kornicameister.sise.puzzle.PuzzleSolverImpl;
import org.kornicameister.sise.puzzle.heuristic.PuzzleCountInvalidHeuristics;
import org.kornicameister.sise.puzzle.heuristic.PuzzleManhattanHeuristic;
import org.kornicameister.sise.puzzle.node.PuzzleNode;
import org.kornicameister.sise.puzzle.stratagies.AStarPuzzleStrategy;
import org.kornicameister.sise.puzzle.stratagies.BFSPuzzleStrategy;
import org.kornicameister.sise.puzzle.stratagies.InversionAccessibleNodeStrategy;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Puzzle15's Puzzle is the main class - launcher -
 * of whole application...contains only main(String []args) method
 * that is further accessible via command line tool
 *
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class Puzzle {
    static boolean idfs = false;

    public static void main(String[] args) throws Exception {

        final Map<CLIArguments, Object> parse = CLIWrapper.getCMD().parse(args);

        if (parse == null) {
            return;
        }

        Integer[][] puzzle = new Integer[0][];
        Heuristic heuristic = null;
        String order = null;
        GraphSearchStrategy strategy = null;

        for (Map.Entry<CLIArguments, Object> option : parse.entrySet()) {
            switch (option.getKey()) {
                case INPUT_C:
                    puzzle = PuzzleLoader.loadPuzzle();
                    break;
                case INPUT_F:
                    puzzle = PuzzleLoader.loadPuzzle((String) option.getValue());
                    break;
                case HEURISTIC:
                    Integer hId = (Integer) option.getValue();
                    switch (hId) {
                        case 1:
                            heuristic = new PuzzleManhattanHeuristic();
                            break;
                        case 2:
                            heuristic = new PuzzleCountInvalidHeuristics();
                            break;
                    }
                    break;
                case ORDER:
                    order = (String) option.getValue();
                    break;
                case STRATEGY:
                    Graphs graphs = (Graphs) option.getValue();
                    switch (graphs) {
                        case BFS:
                            strategy = new BFSPuzzleStrategy();
                            break;
                        case IDFS:
                            idfs = true;
                            //strategy = new IDFSStrategy();
                            break;
                        case DFS:
                            strategy = new DFSStrategy();
                            break;
                        case AStar:
                            strategy = new AStarPuzzleStrategy();
                            break;
                    }
            }
        }

        if (strategy instanceof AStarPuzzleStrategy) {
            ((AStarPuzzleStrategy) strategy).setHeuristic(heuristic);
        }
        if (order == null) {
            order = "R";
        }

        System.out.println(String.format(new StringBuilder()
                .append("\n-------------------------------\n")
                .append("Puzzle solving parameters\n")
                .append("Search strategy:\t%s\n")
                .append("Heuristic:\t\t\t%s\n")
                .append("Order:\t\t\t\t%s\n")
                .append("Puzzle:\t\t\t\t%s\n")
                .append("-------------------------------\n")
                .toString(),
                idfs == false ?
                        strategy.getClass().getSimpleName() : "IDFS",
                heuristic != null ? heuristic.getClass().getSimpleName() : "skipped",
                order,
                Arrays.deepToString(puzzle)
        ));

        final String finalOrder = order;
        final Integer[][] finalPuzzle = puzzle;
        final GraphSearchStrategy finalStrategy = strategy;
        final Integer[][] finalPuzzle1 = puzzle;
        final ProgressBarRotating barRotating = new ProgressBarRotating();

        SwingWorker<String, Void> worker = new SwingWorker<String, Void>() {
            @Override
            protected String doInBackground() throws Exception {
                Graph graph = new Graph((new PuzzleNode("Init", finalOrder, finalPuzzle)));
                graph.setStrategy(finalStrategy);

                PuzzleSolverImpl puzzleSolverImpl = new PuzzleSolverImpl(graph);
                puzzleSolverImpl.setExamination(new InversionAccessibleNodeStrategy());

                String answer;

                barRotating.setShowProgress(true);
                {
                    if (puzzleSolverImpl.isSolvable()) {
                        if (idfs) {
                            IDFSStrategy idfs = new IDFSStrategy();
                            idfs.init(new ArrayList<GraphNode>());
                            idfs.iDFS(puzzleSolverImpl.getGraph().getNode(0), puzzleSolverImpl.getGraph().getNode(1), 20, false);
                            answer = idfs.getReport();
                        } else {
                            puzzleSolverImpl.solve();
                            answer = graph.getStrategy().getReport();
                        }
                    } else {
                        answer = String.format("%s is not solvable based on %s",
                                Arrays.deepToString(finalPuzzle1),
                                puzzleSolverImpl.getExamination().getClass().getSimpleName());
                    }
                }
                barRotating.setShowProgress(false);
                System.out.println("\r\r\r\r");
                return answer;
            }
        };

        barRotating.start();
        worker.execute();
        System.out.println(worker.get());
    }

    static class PuzzleLoader {
        public static Integer[][] loadPuzzle(String file) throws FileNotFoundException {
            Integer[][] puzzle;
            Scanner scanner = new Scanner(new File(file));
            int width = scanner.nextInt(),
                    height = scanner.nextInt();
            puzzle = new Integer[width][height];
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    puzzle[i][j] = scanner.nextInt();
                }
            }
            scanner.close();
            return puzzle;
        }

        public static Integer[][] loadPuzzle() {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Width: ");
            int width = scanner.nextInt();

            System.out.print("Height: ");
            int height = scanner.nextInt();

            System.out.println("Puzzle: ");

            List<String> lines = new ArrayList<>();

            Integer[][] puzzle = new Integer[width][height];
            for (int i = 0; i < width; i++) {
                if (scanner.hasNext()) {
                    for (int j = 0; j < height; j++) {
                        puzzle[i][j] = scanner.nextInt();
                    }
                }
            }

            scanner.close();

            return puzzle;
        }
    }

    static class ProgressBarRotating extends Thread {
        boolean showProgress = true;

        public boolean isShowProgress() {
            return showProgress;
        }

        public void setShowProgress(final boolean showProgress) {
            this.showProgress = showProgress;
        }

        public void run() {
            String anim = "|/-\\";
            int x = 0;
            final Long startTime = System.nanoTime();
            while (showProgress) {
                System.out.print(String.format("\rProcessing [%d ms] %s",
                        TimeUnit.NANOSECONDS.toMillis(System.nanoTime() - startTime),
                        anim.charAt(x++ % anim.length()))
                );
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                    System.err.println(e.getMessage());
                }
            }
        }
    }
}
