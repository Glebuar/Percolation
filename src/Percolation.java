import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

import java.util.Arrays;

public class Percolation {                              // create n-by-n grid, with all sites blocked

    private int[][] site;
    private int[][] status;
    private int n;
    private Union u;

    public Percolation(int n) {
        if (n <= 0 ) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        this.n = n;
        site = new int [n][n];//main array
        status = new int [n][n];//open or not
        int k = 1;
        for (int i = 0; i < site.length; i++) {
            for(int j = 0; j < site[i].length; j++) {
                site[i][j] = k;
                k++;
            }
        }
        u = new Union(n*n+2);
     /*   for (int i = 1; i <= n; i++) {
            u.connect(0, i);
        }
        for (int j = n*n; j >= n*n + 1 - n; j--){
            u.connect(n*n + 1, j);
        }*/
    }

    public void open(int row, int col) {                // open site (row, col) if it is not open already
        isCorrectCoordinates(row, col);
        if(!isOpen(row,col)) {
            status[row - 1][col - 1] = 1;
        }
        if (row - 2 >= 0) {
            if (isOpen(row - 1, col)) {
                u.connect(site[row - 2][col - 1], site[row - 1][col - 1]);
            }
        }
        if (row  < site.length) {
            if (isOpen(row + 1, col)) {
                u.connect(site[row][col - 1], site[row - 1][col - 1]);
            }
        }
        if (col - 2 >= 0) {
            if (isOpen(row, col - 1)) {
                u.connect(site[row - 1][col - 2], site[row - 1][col - 1]);
            }
        }
        if (col < site[row - 1].length) {
            if (isOpen(row, col + 1)) {
                u.connect(site[row - 1][col], site[row - 1][col - 1]);
            }
        }
    }

    public boolean isOpen(int row, int col) {           // is site (row, col) open?
        isCorrectCoordinates(row, col);
        return status[row - 1][col - 1] == 1;
    }

    public boolean isFull(int row, int col) {           // is site (row, col) full?
        isCorrectCoordinates(row, col);
        return u.isConnected(0, site[row - 1][col - 1]);
    }


    public int numberOfOpenSites() {                     // number of open sites
        int count = 0;
        for (int i = 0; i < status.length; i++) {
            for(int j = 0; j < status[i].length; j++) {
                if (status[i][j] == 1) {
                    count ++;
                }
            }
        }
        return count;
    }

    public boolean percolates() {                       // does the system percolate?
        for (int i = 1; i <= n; i++) {
            if (isOpen(1, i)) {
                u.connect(0, i);
            }
        }
        for (int j = n*n; j >= n*n + 1 - n; j--){
            if (isOpen(n, j - n * (n - 1))) {
                u.connect(n * n + 1, j);
            }
        }
        return u.isConnected(0, n*n + 1);
    }

    private void isCorrectCoordinates(int row, int col) {
        if (row < 1 || row > site.length) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 || col > site.length) {
            throw new IllegalArgumentException(Integer.toString(col));
        }
    }

    public static void main (String[] args) {
        Percolation p = new Percolation(6);
        p.open(1,3);
        p.open(2,3);
        p.open(2,4);
        p.open(3,4);
        p.open(4,4);
        p.open(5,4);
        p.open(6,4);
        //System.out.println(p.numberOfOpenSites());
        System.out.println(p.percolates());
        //p.u.print();
    }
}
