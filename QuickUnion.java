/*
Name: Alex Yuk
File: Standard QuickUnion Class
Date: 10/8/2019
 */

public class QuickUnion extends UnionFindAlg {
    private int[] list;

    public QuickUnion(int[] arr) {
        list = arr;
    }

    public QuickUnion(int n) {
        list = new int[n];
        for (int i = 0; i < n; i++)
            list[i] = i;
    }

    private int root(int i){
        while (i != list[i]){
            i = list[i];
        }
        return i;
    }

    public boolean connected(int p, int q){
        return root(p) == root(q);
    }

    public void union(int p, int q){
        int i = root(p);
        int j = root(q);
        list[i] = j;
    }
}
