/*
Name: Alex Yuk
File: QuickUnion Class
 */

public class QuickUnion extends UnionFindAlg {
    private int[] list;

    public QuickUnion(int[] arr) {
        list = arr;
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
