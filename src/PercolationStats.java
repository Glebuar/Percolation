import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import java.util.Scanner;

public class PercolationStats {

    private double[] fraction; // final

    //O(K*N^3)
    private PercolationStats(int n, int trials) {       // perform trials independent experiments on an n-by-n grid
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        if (trials <= 0) {
            throw new IllegalArgumentException(Integer.toString(trials));
        }
        fraction = new double[trials];
        for (int i = 0; i < trials; i++) {
            fraction[i] = ((double) makePercolation(n)) / (n * n);
        }
     }

    //O(n^3)
    private int makePercolation(int n) {
       // Percolation p = new Percolation(n);
        Percolation2 p = new Percolation2(n);
        while (!p.percolates()) { //N*(N*(N-1))
            int a = StdRandom.uniform(n) + 1;
            int b = StdRandom.uniform(n) + 1;
            if (!p.isOpen(a, b)) {
                p.open(a, b);
            }
        }
        return p.numberOfOpenSites();
    }

    private double mean() {                             // sample mean of percolation threshold
        return StdStats.mean(fraction);
    }

    private double stddev() {                           // sample standard deviation of percolation threshold
        return StdStats.stddev(fraction);
    }

    private double confidenceLo() {                     // low  endpoint of 95% confidence interval
        return StdStats.mean(fraction) - 1.96 * StdStats.stddev(fraction) /  Math.sqrt(fraction.length);
    }

    private double confidenceHi() {                     // high endpoint of 95% confidence interval
        return StdStats.mean(fraction) + 1.96 * StdStats.stddev(fraction) /  Math.sqrt(fraction.length);
    }

    public static void main(String[] args) {            // test client (described below)
        final long startTime = System.currentTimeMillis();
        final Scanner in = new Scanner(System.in, "UTF-8");
        final int n = in.nextInt();
        final int t = in.nextInt();
        final PercolationStats ps = new PercolationStats(n, t);
        // change printf %s:5
        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        final long stopTime = System.currentTimeMillis();
        final long elapsedTime = stopTime - startTime;
        System.out.format("time                    = %.3f seconds (%.3f min)",
                          elapsedTime / 1000.0, (double) elapsedTime/60000);
    }
}