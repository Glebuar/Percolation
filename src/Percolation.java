import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    public static void main (String[] args) {
        Union test = new Union();
        test.connect(5,7);
        System.out.println(test.isConnected(5,7));
    }
}