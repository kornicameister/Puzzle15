package org.kornicameister.sise.core.graph;

import junit.framework.Assert;
import org.junit.Before;
import org.junit.Test;
import org.kornicameister.sise.core.strategies.BFSStrategy;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

/**
 * @author kornicameister
 * @version 0.0.1
 * @since 0.0.1
 */
public class GraphTest {
    private final static String GRAPH_PATH_1 = "/home/kornicameister/Dropbox/STUDIA/INFORMATYKA/SEMESTR6/SISE/Puzzle15/src/test/resources/graph_1.txt";
    private Graph graph;

    @Before
    public void setUp() throws Exception {
        Scanner scanner = new Scanner(new FileInputStream(new File(GRAPH_PATH_1)));
        int graphNodes = 0;
        int currentNode = 0;
        GraphNode[] nodes = new GraphNode[0];
        while (scanner.hasNextLine()) {
            if (graphNodes == 0) {
                graphNodes = scanner.nextInt();
                nodes = new Node[graphNodes];
                for (int i = 0; i < graphNodes; i++) {
                    nodes[i] = new Node(String.format("Node %d", i + 1));
                }
            } else {
                // reading neighbours of the i-it node
                final String line = scanner.next();
                String[] neighbours = line.split(",");
                for (int i = 0; i < neighbours.length; i++) {
                    nodes[currentNode].addNeighbour(nodes[Integer.parseInt(neighbours[i])]);
                }
                currentNode++;
            }
        }
        this.graph = new Graph(nodes);
    }

    @Test
    public void testTraverse() throws Exception {
        System.out.println("Whole graph");
        graph.setStrategy(new BFSStrategy());
        graph.traverse(0);
        Assert.assertNotNull("Path is null, bad", graph.getPath());
        for (GraphNode node : graph.getPath()) {
            System.out.println(node);
        }
    }

    @Test
    public void testTraverse2() throws Exception {
        System.out.println("Graph with end condition");
        graph.setStrategy(new BFSStrategy());
        graph.traverse(5, 1);
        Assert.assertNotNull("Path is null, bad", graph.getPath());
        for (GraphNode node : graph.getPath()) {
            System.out.println(node);
        }
    }
}
