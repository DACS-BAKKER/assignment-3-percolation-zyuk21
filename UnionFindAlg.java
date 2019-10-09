/*
Name: Alex Yuk
File: UnionFindAlg Abstract Class
Date: 10/8/2019
Description: The four UF algorithms used are extended from this so they can all be called from one run
 */

public abstract class UnionFindAlg {
    protected abstract boolean connected(int p, int q);
    protected abstract void union(int p, int q);
}
