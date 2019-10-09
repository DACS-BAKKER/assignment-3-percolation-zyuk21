/*
Name: Alex Yuk
File: Percolation Class Runner
Date: 10/9/2019
Description: Uses all three UF algorithms and prints out their average time taken and percolation probability
 */

import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Runner {

    /*
    QuickFind is extremely slow for N above 500
     */
    // N * N grid
    private static int N;
    // Run percolation REPETITIONS times
    private static int REPETITIONS;

    enum Alg {
        QuickFind, QuickUnion, WeightedQuickUnion, PathCompressionWQU
    }

    public static void main(String[] args) {
        // Asks user to set N and Repetitions
        StdOut.print("Enter N\n? ");
        N = StdIn.readInt();
        StdOut.print("Enter Repetitions \n? ");
        REPETITIONS = StdIn.readInt();

        // To keep track of time
        long before, after;

        // So the for loop later can traverse through every Alg
        Alg algList[] = new Alg[4];
        algList[0] = Alg.QuickFind;
        algList[1] = Alg.QuickUnion;
        algList[2] = Alg.WeightedQuickUnion;
        algList[3] = Alg.PathCompressionWQU;

        System.out.println("\nRunning...\n" + N + " * " + N + " grid with " + REPETITIONS + " Repetitions\n");

        /*
        Uncomment the lines ~47 - 55 and comment the for each loop on lines ~58 - 70 to run specific UF algorithms
         */
//        Alg current_alg = Alg.PathCompressionWQU; // Change the specific algorithm here
//        before = System.nanoTime();
//        double total = 0;
//        for (int j = 0; j < REPETITIONS; j++) {
//            total += doThing(current_alg);
//        }
//        after = System.nanoTime();
//        System.out.println(current_alg + "\nAverage Percolation Probability: " + total / REPETITIONS / (N * N));
//        System.out.println("Average Time Taken (microseconds): " + (after - before) / REPETITIONS / 1000);

        // Does all three UF algorithms
        for (Alg current_alg : algList) {
            before = System.nanoTime();

            double total = 0;
            for (int j = 0; j < REPETITIONS; j++) {
                total += percolate(current_alg);
            }

            after = System.nanoTime();

            System.out.println(current_alg + "\nAverage Percolation Probability: " + total / REPETITIONS / (N * N));
            System.out.println("Average Time Taken (microseconds): " + (after - before) / REPETITIONS / 1000 + "\n");
        }

    }

    public static int percolate(Alg current_alg) {
        Percolation perc = new Percolation(N);

        // Chooses current Alg
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
