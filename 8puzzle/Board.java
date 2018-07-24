import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Stack;

public class Board {
    private int[] block;
    private int N;
    public Board(int[][] blocks) {
        N = blocks.length;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                block[i * N + j] = blocks[i][j];
            }
        }
    }             // construct a board from an n-by-n array of blocks
    private Board(int[] blocks) {
        N = (int) Math.sqrt(blocks.length);
        this.block = new int[blocks.length];
        for (int i = 0; i < blocks.length; i++)
            this.block[i] = blocks[i];
    }
    // (where blocks[i][j] = block in row i, column j)
    public int dimension() {
        return N;
    }                 // board dimension n
    public int hamming() {
        int cnt = 0;
        for (int i = 0; i < N * N; i++) {
            if (block[i] != i + 1) {
                cnt++;
            }
        }
        return cnt;
    }                   // number of blocks out of place
    public int manhattan() {
        int sum = 0;
        int col, row;
        for (int i = 0; i < N * N; i++) {
            row = Math.abs(i / N - block[i] / N);
            col = Math.abs(i % N - block[i] % N);
            sum += row + col;
        }
        return sum;
    }                 // sum of Manhattan distances between blocks and goal
    public boolean isGoal() {
        for (int i = 0; i < block.length; i++)
            if (block[i] != 0 && block[i] != i+1)
                return false;
        return true;
    }                // is this board the goal board?
    private void exch(int[] arr, int i, int j) {
        int tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }
    public Board twin() {
        if (N <= 1) {
            return null;
        }
        Board tBoard = new Board(block);
        if (block[0] != 0 && block[1] != 0) {
            exch(tBoard.block, 0 , 1);
        } else {
            exch(tBoard.block, N * N - 2 , N * N - 1);
        }
        return tBoard;
    }                    // a board that is obtained by exchanging any pair of blocks
    public boolean equals(Object y) {
        if (y == null) return false;
        if (y == this) return true;
        if (y.getClass() != this.getClass())
            return false;

        Board yBoard = (Board) y;
        return Arrays.equals(block, yBoard.block);
    }        // does this board equal y?
    public Iterable<Board> neighbors() {
        Stack<Board> neighbors = new Stack<>();
        int[] xDiff = {-1, 1, 0, 0};
        int[] yDiff = {0, 0, -1, 1};
        int[] swappedBlocks;
        int idxOfBlank, idxOfNeigh;
        for (idxOfBlank = 0; idxOfBlank < N * N; idxOfBlank++) {
            if (block[idxOfBlank] == 0) break;
        }
        for (int i = 0; i < 4; i++) {
            int row = idxOfBlank / N + xDiff[i];
            int col = idxOfBlank % N + yDiff[i];

            if (row >= 0 && row < N && col >= 0 && col < N) {
                idxOfNeigh = col + row / N;
                int[] swap = getSwappedBlocks(idxOfBlank, idxOfNeigh);
                neighbors.push(new Board(swap));
            }
        }
        return neighbors;
    }

        private int[] getSwappedBlocks(int i, int j) {
            // copy the blocks
            int[] arr = Arrays.copyOf(block, N * N);
            int swap = arr[i];
            arr[i] = arr[j];
            arr[j] = swap;
            return arr;
        }



    public String toString() {
        StringBuilder s = new StringBuilder();
        int digit = 0;
        String format;

        s.append(N);
        s.append("\n");
        for (int n = block.length; n != 0; n /= 10) {
            digit++;
        }
        format = "%" + digit + "d ";
        for (int i = 0; i < block.length; i++) {
            s.append(String.format(format, block[i]));
            if ((i+1) % N == 0)
                s.append("\n");
        }
        return s.toString();

    }
    public static void main(String[] args)
    {
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        StdOut.println(initial.dimension());
        StdOut.println(initial.toString());
        StdOut.println(initial.hamming());
        StdOut.println(initial.manhattan());
        StdOut.println(initial.twin().toString());
        for (Board nb : initial.neighbors())
            for (Board nbb : nb.neighbors())
                StdOut.println(nbb.toString());
    }
}
