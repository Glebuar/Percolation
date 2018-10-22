import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {                              // create n-by-n grid, with all sites blocked

    private boolean[][] status;
    private WeightedQuickUnionUF u;

    public Percolation2(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException(Integer.toString(n));
        }
        status = new boolean [n][n];
        u = new WeightedQuickUnionUF(n * n + 2);
    }

    private int getIndex(int row, int col) {
        return (row - 1) * getSize() + col;
    }

    public void open(int row, int col) {                // open site (row, col) if it is not open already
        isCorrectCoordinates(row, col);
        if (!isOpen(row, col)) {
            status[row - 1][col - 1] = true;
        }
        if (row == 1) {
            u.union(0, getIndex(row, col));
        } else {
            if (row == getSize()) {
                u.union(getSize() * getSize() +1, getIndex(row, col));
            }
        }
        if (row - 2 >= 0) {
            if (isOpen(row - 1, col)) {
                u.union(getIndex(row - 1, col), getIndex(row, col));
            }
        }
        if (row  < getSize()) {
            if (isOpen(row + 1, col)) {
                u.union(getIndex(row + 1, col), getIndex(row, col));
            }
        }
        if (col - 2 >= 0) {
            if (isOpen(row, col - 1)) {
                u.union(getIndex(row, col - 1), getIndex(row, col));
            }
        }
        if (col < getSize()) {
            if (isOpen(row, col + 1)) {
                u.union(getIndex(row, col +1), getIndex(row, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {          // is site (row, col) open?
        isCorrectCoordinates(row, col);
        return status[row - 1][col - 1];
    }

    public boolean isFull(int row, int col) {           // is site (row, col) full?
        isCorrectCoordinates(row, col);
        return u.connected(0, getIndex(row, col));
    }

    public int numberOfOpenSites() {                    // number of open sites
        int count = 0;
        for (boolean[] stat : status) {
            for (boolean aStat : stat) {
                if (aStat) {
                    count++;
                }
            }
        }
        return count;
    }

    public boolean percolates() {                      // does the system percolate?
        for (int i = 1; i <= getSize(); i++) {
            if (isOpen(1, i)) {
                u.union(0, i);
            }
        }
        for (int j = getSize() * getSize(); j >= getSize() * getSize() + 1 - getSize(); j--) {
            if (isOpen(getSize(), j - getSize() * (getSize() - 1))) {
                u.union(getSize() * getSize() + 1, j);
            }
        }
        return u.connected(0, getSize() * getSize() + 1);
    }

    private int getSize() {
        return status.length;
    }

    private void isCorrectCoordinates(int row, int col) {
        if (row < 1 || row > getSize()) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 || col > getSize()) {
            throw new IllegalArgumentException(Integer.toString(col));
        }
    }

}
