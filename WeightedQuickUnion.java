/*
Name: Alex Yuk
File: Standard WeightedQuickUnion Class
Date: 10/8/2019
 */

public class WeightedQuickUnion extends UnionFindAlg {

    private int[] list;
    private int[] size;
    private int count;

    public WeightedQuickUnion(int n) {
        count = n;
        list = new int[n];
        size = new int[n];
        for (int i = 0; i < n; i++) {
            list[i] = i;
            size[i] = 1;
        }
    }

    public WeightedQuickUnion(int[] arr) {
        count = arr.length;
        list = arr;
        size = new int[arr.length];
        for (int i = 0; i < count; i++) {
            size[i] = 1;
        }
    }

    public int find(int p) {
        validate(p);
        while (p != list[p])
            p = list[p];
        return p;
    }

    private void validate(int p) {
        int n = list.length;
        if (p < 0 || p >= n) {
            throw new IllegalArgumentException("Index " + p + " is out of bounds");
        }
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {
        int rootP = find(p);
        int rootQ = find(q);
        if (rootP == rootQ) return;

        // make smaller root point to larger one
        if (size[rootP] < size[rootQ]) {
            list[rootP] = rootQ;
            size[rootQ] += size[rootP];
        }
        else {
            list[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
        count--;
    }
}
