public class Percolation {                              // create n-by-n grid, with all sites blocked

    private final boolean[][] status;
    private final Union u;

    //O(n^2)
    public Percolation(int n) {
        if (n <= 0) {
            // better message
            throw new IllegalArgumentException(Integer.toString(n));
        }
        status = new boolean[n][n];
        u = new Union(n * n + 2);
    }

    //O(1)
    private int getIndex(int row, int col) {
        return (row - 1) * getSize() + col;
    }

    //O(n)
    public void open(int row, int col) {                // open site (row, col) if it is not open already
        isCorrectCoordinates(row, col);
        if (!isOpen(row, col)) {
            status[row - 1][col - 1] = true;
        }
        if (row == 1) {
            u.connect(0, getIndex(row, col));
        } else {
            if (row == getSize()) {
                u.connect(getSize() * getSize() + 1, getIndex(row, col));
            }
        }
        if (row - 2 >= 0) {
            if (isOpen(row - 1, col)) {
                u.connect(getIndex(row - 1, col), getIndex(row, col));
            }
        }
        if (row  < getSize()) {
            if (isOpen(row + 1, col)) {
                u.connect(getIndex(row + 1, col), getIndex(row, col));
            }
        }
        if (col - 2 >= 0) {
            if (isOpen(row, col - 1)) {
                u.connect(getIndex(row, col - 1), getIndex(row, col));
            }
        }
        if (col < getSize()) {
            if (isOpen(row, col + 1)) {
                u.connect(getIndex(row, col +1), getIndex(row, col));
            }
        }
    }

    //O(1)
    public boolean isOpen(int row, int col) {          // is site (row, col) open?
        isCorrectCoordinates(row, col);
        return status[row - 1][col - 1];
    }

    //O(n)
    public boolean isFull(int row, int col) {           // is site (row, col) full?
        isCorrectCoordinates(row, col);
        return u.isConnected(0, getIndex(row, col));
    }

    //O(n^2)
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

    //O(n)
    public boolean percolates() {                      // does the system percolate?

        return u.isConnected(0, getSize() * getSize() + 1);
    }

    //O(1)
    private int getSize() {
        return status.length;
    }

    //O(n)
    private void isCorrectCoordinates(int row, int col) {
        if (row < 1 || row > getSize()) {
            throw new IllegalArgumentException(Integer.toString(row));
        }
        if (col < 1 || col > getSize()) {
            throw new IllegalArgumentException(Integer.toString(col));
        }
    }
}
