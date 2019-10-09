/*
Name: Alex Yuk
File: Percolation Class
Date: 10/9/2019
 */

public class Percolation {
    // Keeps tract of connection
    public int[] connected;
    // Keeps track of what blocks are open and closed
    public boolean opened[];

    private int N;
    private int top;
    private int bottom;

    public UnionFindAlg Alg;

    // Create N * N grid and N * N blocked array
    public Percolation(int N) {
        this.N = N;
        // Sets top and bottom to their corresponding element
        this.top = N * N;
        this.bottom = N * N + 1;

        // Two extra elements used to track top and bottom
        connected = new int[N * N + 2];

        for (int i = 0; i < N * N + 2; i++)
            connected[i] = i;

        opened = new boolean[N * N];
    }

    // Set current algorithm
    public void setAlg(UnionFindAlg Alg) {
        this.Alg = Alg;
        for (int i = 0; i < N; i++) {
            // Connect top row to top and bottom row to bottom
            this.Alg.union(i, this.top);
            this.Alg.union(N * (N - 1) + i, this.bottom);
        }
    }

    // Open r row and c column
    public void open(int r, int c) {
        opened[N * r + c] = true;
    }

    // return true if r row and c column is open, else return false
    public boolean isOpen(int r, int c) {
        return opened[N * r + c];
    }

    // return true if r row and c column is full, else return false
    public boolean isFull(int r, int c) {
        return Alg.connected(N * N,N * r + c);
    }

    // return true if system percolates, else return false
    public boolean percolates() {
        return Alg.connected(N * N,N * N + 1);
    }

    public static void main(String[] args) {

    }
}