/*
Name: Alex Yuk
File: Percolation Class Graphics Runner
Description:
SAME CODE AS RUNNER CLASS EXCPET ADDED GRAPHICAL ELEMENTS
 */

import edu.princeton.cs.algs4.*;
import javax.swing.*;
import java.awt.*;

public class GRunner {

    private static final int N = 40;
    private static final int REPITITIONS = 1;
    private static final int APPLICATION_SIZE = 500;
    private static final double SQR_SIZE = 0.5 / N;

    private static final Color OPEN_COLOR = new Color(255, 255, 255);
    private static final Color BLOCKED_COLOR = new Color(80, 80, 80);
    private static final Color FULL_COLOR = new Color(	74, 	134, 232);


    enum Alg {
        QuickFind, QuickUnion, WeightedQuickUnion
    }

    private static void draw(int r, int c) {
        StdDraw.setPenColor(OPEN_COLOR);
        StdDraw.filledSquare(2 * c * SQR_SIZE + SQR_SIZE, 2 * (N - r - 1) * SQR_SIZE + SQR_SIZE, SQR_SIZE);
        StdDraw.setPenColor(FULL_COLOR);
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (perc.isFull(i, j) && perc.isOpen(i,j))
                    StdDraw.filledSquare(2 * j * SQR_SIZE + SQR_SIZE, 2 * (N - i - 1) * SQR_SIZE + SQR_SIZE, SQR_SIZE);
    }

    private static void init() {
        StdDraw.setCanvasSize(APPLICATION_SIZE, APPLICATION_SIZE);
        StdDraw.setPenColor(BLOCKED_COLOR);
        StdDraw.filledSquare(APPLICATION_SIZE / 2, APPLICATION_SIZE / 2, APPLICATION_SIZE);
    }

    public static void main(String[] args) {
        init();
        long before, after;

        Runner.Alg algList[] = new Runner.Alg[3];
        algList[0] = Runner.Alg.QuickFind;
        algList[1] = Runner.Alg.QuickUnion;
        algList[2] = Runner.Alg.WeightedQuickUnion;

        System.out.println(REPITITIONS + " Repetitions");

        for (Runner.Alg currentAlg : algList) {
            before = System.currentTimeMillis();

            double total = 0;
            for (int j = 0; j < REPITITIONS; j++) {
                total += doThing(currentAlg);
            }
            after = System.currentTimeMillis();

            System.out.println(currentAlg + "\nAverage: " + total / REPITITIONS / (N * N));
            System.out.println("Time Taken (ms): " + (after - before));
        }
    }


    private static Percolation perc;

    public static int doThing(Runner.Alg currentAlg) {
        perc = new Percolation(N);

        switch (currentAlg) {
            case QuickFind:
                perc.setAlg(new QuickFind(perc.connected));
                break;
            case QuickUnion:
                perc.setAlg(new QuickUnion(perc.connected));
                break;
            case WeightedQuickUnion:
                perc.setAlg(new WeightedQuickUnion(perc.connected));
                break;
        }

        int rRow = 0, rCol = 0;
        int count = 0;
        while (!perc.percolates()) {

            do {
                rRow = (int) (Math.random() * (N));
                rCol = (int) (Math.random() * (N));
            } while (perc.isOpen(rRow, rCol));

            perc.open(rRow, rCol);
            draw(rRow,rCol);
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int temp = N * rRow + rCol + (i * N) + j;
                    if (temp > 0 && temp < N * N && perc.opened[temp]) {
                        if ((i == -1 || i == 1) && j == 0) {
                            perc.Alg.union(temp, N * rRow + rCol);
                        }
                        else if (i == 0 && (j == -1 || j == 1)) {
                            if (temp / N == (N * rRow + rCol) / N)
                                perc.Alg.union(temp, N * rRow + rCol);
                        }
                    }
                }
            }
            count++;
        }

        draw(rRow,rCol);
        StdDraw.pause(1500);
        StdDraw.clear();
        StdDraw.setPenColor(BLOCKED_COLOR);
        StdDraw.filledSquare(APPLICATION_SIZE / 2, APPLICATION_SIZE / 2, APPLICATION_SIZE);

        return count;
    }
}
