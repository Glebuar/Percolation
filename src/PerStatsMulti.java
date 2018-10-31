import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.*;

public class PerStatsMulti  {
    private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(12);
    private double[] fraction;
    private final int n;
    private final int t;
    public PerStatsMulti(int n, int t) {
        this.n = n;
        this.t = t;
        fraction = new double[t];
    }
    
    private static class PerWorker implements Callable<Integer> {

        private final int n;
        
        public PerWorker(final int n) {
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
    }

    private void calculate() {

        List<Future<Integer>> list = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < t; i++) {
            Future<Integer> future = EXECUTOR.submit(new PerWorker(n));
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
        EXECUTOR.shutdown();

    }

    private double mean() {                             // sample mean of percolation threshold
        return StdStats.mean(fraction);
    }

    private double stddev() {                           // sample standard deviation of percolation threshold
        return StdStats.stddev(fraction);
    }

    private double confidenceLo() {
        return StdStats.mean(fraction) - 1.96 * StdStats.stddev(fraction) /  Math.sqrt(fraction.length);
    }

    private double confidenceHi() {
        return StdStats.mean(fraction) + 1.96 * StdStats.stddev(fraction) /  Math.sqrt(fraction.length);
    }

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        Scanner in = new Scanner(System.in, "UTF-8");
        int n = in.nextInt();
        int t = in.nextInt();
        PerStatsMulti p = new PerStatsMulti(n, t);
        p.calculate();
        ThreadPoolExecutor pool = (ThreadPoolExecutor) EXECUTOR;
        System.out.println("Current threads in POOL = " + pool.getPoolSize());
        System.out.println("Maximum allowed threads = " + pool.getMaximumPoolSize());
        System.out.println("Total number of threads = " + pool.getTaskCount());
        System.out.println("mean                    = " + p.mean());
        System.out.println("stddev                  = " + p.stddev());
        System.out.println("95% confidence interval = [" + p.confidenceLo() + ", " + p.confidenceHi() + "]");
        long stopTime = System.currentTimeMillis();
        long elapsedTime = stopTime - startTime;
        System.out.format("time                    = %.3f seconds (%.3f min)", elapsedTime / 1000.0, (double) elapsedTime/60000);
    }
}
