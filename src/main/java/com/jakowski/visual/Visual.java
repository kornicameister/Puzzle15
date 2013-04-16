package com.jakowski.visual;


import au.com.bytecode.opencsv.CSVReader;
import com.jakowski.visual.coordinates.Coordinates;

import javax.swing.*;
import java.awt.*;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

/* 
 * Main class of graphical visualization solution Fifteen Puzzle
 * 
 * @author Sebastian Jakowski
 */
public class Visual extends JComponent {
    private Coordinates[][] array;
    private char[] solutions;
    private int[] measurement;
    private int countMove;
    private Timer timer;
    ;
    boolean permision = true;


    public void paintComponent(Graphics g) {
        g.setFont(new Font("Arial", Font.ITALIC, 30));
        for (int i = 0; i < measurement[0]; i++) {
            for (int j = 0; j < measurement[1]; j++) {
                if (array[i][j].getValue() != 0) {
                    g.setColor(Color.DARK_GRAY);
                    g.fillRect(array[i][j].getX(), array[i][j].getY(), 98, 98);
                    g.setColor(Color.WHITE);
                    g.drawString(String.valueOf(array[i][j].getValue()), array[i][j].getX() + 35, array[i][j].getY() + 50);

                }
            }
        }
    }

    public Visual() {
        super();
        //array = new ArrayList<Coordinates>();
        measurement = new int[2];
    }

    public void readArray(String path) throws IOException {

        CSVReader read = new CSVReader(new FileReader(path), ' ');
        List input = read.readAll();
        measurement[0] = input.size();
        String[] temp = (String[]) input.get(0);
        measurement[1] = temp.length;
        array = new Coordinates[measurement[0]][measurement[1]];
        for (int i = 0; i < input.size(); i++) {
            temp = (String[]) input.get(i);
            for (int j = 0; j < temp.length; j++) {
                array[i][j] = (new Coordinates(j * 100, i * 100, Integer.parseInt(temp[j])));
                //measurement[1]=j+1;
            }
        }
        read.close();
    }

    public void readSolution(String path) throws IOException {
        CSVReader read = new CSVReader(new FileReader(path), ' ');
        List input = read.readAll();
        String[] temp = (String[]) input.get(0);
        countMove = Integer.parseInt(temp[0]);
        String[] temp2 = (String[]) input.get(1);
        solutions = new char[temp2[0].length()];
        solutions = temp2[0].toCharArray();
        read.close();
    }

    public void paint() throws InterruptedException {
        Thread.sleep(800);
        for (int i = 0; i < solutions.length; i++) {
            char move = solutions[i];
            final int i1 = 1000;
            switch (move) {
                case 'L': {
                    System.out.println(move);
                    moveLeft();
                    Thread.sleep(i1);
                    break;
                }
                case 'P': {
                    System.out.println("P");
                    moveRight();
                    Thread.sleep(i1);
                    break;
                }
                case 'G': {
                    System.out.println(move);
                    moveUp();
                    Thread.sleep(i1);
                    break;
                }
                case 'D': {
                    moveDown();
                    System.out.println(move);
                    Thread.sleep(i1);
                    break;
                }
            }
        }
    }

    private void moveRight() {
        final int[] empty = getEmpty();
        Coordinates[][] old = array;

        final Coordinates temp = new Coordinates(old[empty[0]][empty[1] + 1].getX(), old[empty[0]][empty[1] + 1].getY(), old[empty[0]][empty[1] + 1].getValue());
        array[empty[0]][empty[1] + 1].setValue(0);
        array[empty[0]][empty[1]].setValue(temp.getValue());

        repaint();
    }

    private void moveLeft() {
        final int[] empty = getEmpty();
        Coordinates[][] old = array;

        final Coordinates temp = new Coordinates(old[empty[0]][empty[1] - 1].getX(), old[empty[0]][empty[1] - 1].getY(), old[empty[0]][empty[1] - 1].getValue());
        array[empty[0]][empty[1] - 1].setValue(0);
        array[empty[0]][empty[1]].setValue(temp.getValue());
        repaint();
    }


    private void moveUp() {
        final int[] empty = getEmpty();
        Coordinates[][] old = array;

        final Coordinates temp = new Coordinates(old[empty[0] - 1][empty[1]].getX(), old[empty[0] - 1][empty[1]].getY(), old[empty[0] - 1][empty[1]].getValue());
        array[empty[0] - 1][empty[1]].setValue(0);
        array[empty[0]][empty[1]].setValue(temp.getValue());
        repaint();
    }

    private void moveDown() {
        final int[] empty = getEmpty();
        Coordinates[][] old = array;

        final Coordinates temp = new Coordinates(old[empty[0] + 1][empty[1]].getX(), old[empty[0] + 1][empty[1]].getY(), old[empty[0] + 1][empty[1]].getValue());
        array[empty[0] + 1][empty[1]].setValue(0);
        array[empty[0]][empty[1]].setValue(temp.getValue());
        repaint();
    }


    private int[] getEmpty() {
        int[] where = new int[2];
        for (int i = 0; i < measurement[0]; i++) {
            for (int j = 0; j < measurement[1]; j++) {
                if (array[i][j].getValue() == 0) {
                    where[0] = i;
                    where[1] = j;
                    break;
                }
            }

        }
        return where;

    }

    public Coordinates[][] getArray() {
        return array;
    }

    public void setArray(Coordinates[][] array) {
        this.array = array;
    }

    public char[] getSolutions() {
        return solutions;
    }

    public void setSolutions(char[] solutions) {
        this.solutions = solutions;
    }

    public int[] getMeasurement() {
        return measurement;
    }

    public void setMeasurement(int[] measurement) {
        this.measurement = measurement;
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        Visual visual = new Visual();
        visual.readArray("output.txt");
        visual.readSolution("solution.txt");

        JFrame frame = new JFrame("Fifteen Puzzle");
        frame.setSize(100 * (visual.getMeasurement()[0]) + 5, 100 * (visual.getMeasurement()[1]) + 25);
        frame.getContentPane().add(visual);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
        visual.paint();
    }

}
