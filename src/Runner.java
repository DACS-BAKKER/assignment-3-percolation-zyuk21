/*
Name: Alex Yuk
File: Percolation Class Runner
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.util.ArrayList;

public class Runner {

    // N * N grid
    private static final int N = 500;
    // Run percolation REPETITION times
    private static final int REPITITIONS = 10;

    enum Alg {
        QuickFind, QuickUnion, WeightedQuickUnion
    }

    public static void main(String[] args) {
        long before, after;

        Alg algList[] = new Alg[3];
        algList[0] = Alg.QuickFind;
        algList[1] = Alg.QuickUnion;
        algList[2] = Alg.WeightedQuickUnion;

        System.out.println(N + " * " + N + " Grid");
        System.out.println(REPITITIONS + " Repetitions\n");


        // Uncomment the lines below and comment the for each loop to run specific UF algorithms

//        before = System.currentTimeMillis();
//        double total = 0;
//        for (int j = 0; j < REPITITIONS; j++) {
//            total += doThing(Alg.WeightedQuickUnion); // Change the algorithm here
//        }
//        after = System.currentTimeMillis();
//        System.out.println(Alg.WeightedQuickUnion + "\nAverage: " + total / REPITITIONS / (N * N));
//        System.out.println("Time Taken (ms): " + (after - before));

        // Does all three UF algorithms
        for (Alg currentAlg : algList) {
            before = System.nanoTime();

            double total = 0;
            for (int j = 0; j < REPITITIONS; j++) {
                total += doThing(currentAlg);
            }

            after = System.nanoTime();

            System.out.println(currentAlg + "\nAverage: " + total / REPITITIONS / (N * N));
            System.out.println("Time Taken (ms): " + (after - before) / REPITITIONS / 1000 + "\n");
        }

    }

    public static int doThing(Alg currentAlg) {
        Percolation perc = new Percolation(N);

        // Chooses current Alg
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

        int rRow, rCol;
        int count = 0;
        while (!perc.percolates()) {

            // Picks random number for a blocked block
            do {
                rRow = (int) (Math.random() * (N));
                rCol = (int) (Math.random() * (N));
            } while (perc.isOpen(rRow, rCol));

            perc.open(rRow, rCol);

            // Checks top bottom left and right of the newly opened block to see if they are also open
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int temp = N * rRow + rCol + (i * N) + j;
                    if (temp > 0 && temp < N * N && perc.opened[temp]) {
                        // If they are opened, connect them
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

        return count;
    }
}
