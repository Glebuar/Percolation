import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {                               // create n-by-n grid, with all sites blocked

    private int[][] site;

    public Percolation(int n) {
        if (n <= 0 ) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        site = new int [n][n];
        int k = 1;
        for (int i = 0; i < site.length; i++) {
            for(int j = 0; j < site[i].length; j++) {
                site[i][j] = k;
                k++;
            }
        }
    }

    public    void open(int row, int col) {              // open site (row, col) if it is not open already
        if (row < 1 || row > site.length) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 ||col > site.length) {
            throw new IllegalArgumentException(Integer.toString(col));
        }
        if (site[row-1][col-1] != 1) site[row-1][col-1] = 1;
    }


    public boolean isOpen(int row, int col) {            // is site (row, col) open?
        if (row < 1 || row > site.length) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 ||col > site.length) {
            throw new IllegalArgumentException(Integer.toString(col));
        }
        return site[row-1][col-1] == 1;
    }
/*
    public boolean isFull(int row, int col) {            // is site (row, col) full?
        if (row < 1 || row > site.length) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 ||col > site.length) {
            throw new IllegalArgumentException(Integer.toString(col));
        }

    }


    public     int numberOfOpenSites();       // number of open sites
    public boolean percolates();              // does the system percolate?
    */


    public static void main (String[] args) {
        Percolation p = new Percolation(6);
        System.out.println(Arrays.deepToString(p.site));

    }
}
