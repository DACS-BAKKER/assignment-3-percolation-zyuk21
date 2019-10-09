/*
Name: Alex Yuk
File: Standard QuickFind Class
Date: 10/8/2019
 */

public class QuickFind extends UnionFindAlg {

    private int[] list;

    public QuickFind(int[] arr) {
        list = arr;
    }

    public QuickFind(int n) {
        list = new int[n];
        for (int i = 0; i < n; i++)
            list[i] = i;
    }

    public boolean connected(int p, int q) {
        return list[p] == list[q];
    }

    public void union(int p, int q) {
        int temp = list[p];
        for (int i = 0; i < list.length; i++)
            if (list[i] == temp)
                list[i] = list[q];
    }
}