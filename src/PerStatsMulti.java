import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class PerStatsMulti implements Callable<Integer> {

    private final int n;
    private static double[] fraction;

    public PerStatsMulti(final int n) {
        this.n = n;
    }

    @Override
    public Integer call() throws Exception {
     // Percolation p = new Percolation(n);
        Percolation2 p = new Percolation2(n);
        while (!p.percolates()) {
            int a = StdRandom.uniform(n) + 1;
            int b = StdRandom.uniform(n) + 1;
            if (!p.isOpen(a, b)) {
                p.open(a, b);
            }
        }
        return p.numberOfOpenSites();
    }

    private static double mean() {                             // sample mean of percolation threshold
        return StdStats.mean(fraction);
    }

    private static double stddev() {                           // sample standard deviation of percolation threshold
        return StdStats.stddev(fraction);
    }

    private static double confidenceLo() {
        return StdStats.mean(fraction) - 1.96 * StdStats.stddev(fraction) /  Math.sqrt(fraction.length);
    }

    private static double confidenceHi() {
        return StdStats.mean(fraction) + 1.96 * StdStats.stddev(fraction) /  Math.sqrt(fraction.length);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(System.in, "UTF-8");
        int n = in.nextInt();
        int t = in.nextInt();
        int index = 0;
        fraction = new double[t];
        ExecutorService executor = Executors.newFixedThreadPool(12);
        ThreadPoolExecutor pool = (ThreadPoolExecutor) executor;
        List<Future<Integer>> list = new ArrayList<>();
        Callable<Integer> callable = new PerStatsMulti(n);
        for (int i = 0; i < t; i++) {
            Future<Integer> future = executor.submit(callable);
            list.add(future);
        }
        for (Future<Integer> fut : list) {
            try {
                fraction[index] = (double) fut.get() / (n * n);
                index++;
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException();
            }
        }
        executor.shutdown();

        System.out.println("Current threads in pool = " + pool.getPoolSize());
        System.out.println("Maximum allowed threads = " + pool.getMaximumPoolSize());
        System.out.println("Total number of threads = " + pool.getTaskCount());
        System.out.println("mean                    = " + mean());
        System.out.println("stddev                  = " + stddev());
        System.out.println("95% confidence interval = [" + confidenceLo() + ", " + confidenceHi() + "]");
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.format("time                    = %.3f seconds (%.3f min)", elapsedTime / 1000.0, (double) elapsedTime/60000);
    }
}