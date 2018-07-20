import edu.princeton.cs.algs4.WeightedQuickUnionUF;
public class Percolation {
    private final boolean[] system;
    private final int nGrid;
    private final WeightedQuickUnionUF grid;
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException();
        system = new boolean[n * n + 2];
        nGrid = n;
        grid = new WeightedQuickUnionUF(n * n + 2);
        system[0] = true;
        system[n * n + 1] = true;
    }
    private void checkRange(int row, int col) {
        if (row <= 0 || col <= 0 || row > nGrid || col > nGrid) {
            throw new IndexOutOfBoundsException();
        }
    }
    private int numToIdx(int row, int col) {
        return (row - 1) * nGrid + col;
    }
    public void open(int row, int col) {
        checkRange(row, col);
        if (isOpen(row, col)) return;
        int idx = numToIdx(row, col);
        system[idx] = true;
        if (row != 1 && isOpen(row - 1, col)) {
            grid.union(idx, numToIdx(row - 1, col));
        } else if (row == 1) {
            grid.union(idx, 0);
        }
        if (row != nGrid && isOpen(row + 1, col)) {
            grid.union(idx, numToIdx(row + 1, col));
        } else if (row == nGrid) {
            grid.union(idx, nGrid * nGrid + 1);
        }
        if (col != 1 && isOpen(row, col - 1)) {
            grid.union(idx, numToIdx(row, col - 1));
        }
        if (col != nGrid && isOpen(row, col + 1)) {
            grid.union(idx, numToIdx(row, col + 1));
        }
    }

    public boolean isOpen(int row, int col) {
        return system[numToIdx(row, col)];
    }  // is site (row, col) open?
    public boolean isFull(int row, int col) {
        checkRange(row, col);
        return grid.connected(0, numToIdx(row, col));
    }
    public int numberOfOpenSites() {
        int count = -2;
        for (boolean i : system) {
            if (i == true) {
                count++;
            }
        }
        return count;
    }
    public boolean percolates() {
        return grid.connected(0, nGrid * nGrid + 1);
    }
}
