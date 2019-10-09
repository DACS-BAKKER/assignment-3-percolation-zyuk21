/*
Name: Alex Yuk
File: Percolation Class Graphics Runner
Date: 10/9/2019
Description: SAME CODE AS RUNNER CLASS EXCEPT ADDED GRAPHICAL ELEMENTS
Instruction: Just click run
 */

import edu.princeton.cs.algs4.*;
import java.awt.*;

public class GRunner {

    /*
    Keep N between 10 and 40 when running with graphics
     */
    private static int N = 20;

    private static final int REPETITIONS = 1;
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
        StdOut.print("Enter N\n? ");
        N = StdIn.readInt();
        init();
        long before, after;

        Runner.Alg alg_list[] = new Runner.Alg[4];
        alg_list[0] = Runner.Alg.QuickFind;
        alg_list[1] = Runner.Alg.QuickUnion;
        alg_list[2] = Runner.Alg.WeightedQuickUnion;
        alg_list[3] = Runner.Alg.PathCompressionWQU;


        for (Runner.Alg current_alg : alg_list) {
            System.out.println("Running: " + current_alg);
            before = System.currentTimeMillis();

            double total = 0;
            for (int j = 0; j < REPETITIONS; j++) {
                total += doThing(current_alg);
            }
            after = System.currentTimeMillis();

            System.out.println("Average Percolation Probability: " + total / REPETITIONS / (N * N));
            System.out.println("Time Taken (with graphics): " + (after - before) + " ms\n");
        }
    }


    private static Percolation perc;

    public static int doThing(Runner.Alg current_alg) {
        perc = new Percolation(N);

        switch (current_alg) {
            case QuickFind:
                perc.setAlg(new QuickFind(perc.connected));
                break;
            case QuickUnion:
                perc.setAlg(new QuickUnion(perc.connected));
                break;
            case WeightedQuickUnion:
                perc.setAlg(new WeightedQuickUnion(perc.connected));
                break;
            case PathCompressionWQU:
                perc.setAlg(new PathCompressionWQU(perc.connected));
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
        // Pause time in milliseconds
        StdDraw.pause(1500);
        StdDraw.clear();
        StdDraw.setPenColor(BLOCKED_COLOR);
        StdDraw.filledSquare(APPLICATION_SIZE / 2, APPLICATION_SIZE / 2, APPLICATION_SIZE);

        return count;
    }
}
