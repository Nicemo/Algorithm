import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

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
            exch(tBoard.block, N , N + 1);
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
    public Iterable<Board> neighbors(){
        int i;
        Board neigh;
        Queue<Board> bq = new LinkedList<>();
        for (i = 0; i < N * N; i++) {
            if (block[i] == 0) break;
        }
        for (i = 0; i < block.length; i++)
            if (block[i] == 0) break;

        /* no 0 block exists. Generally not possible */
        if (i >= block.length) return null;

        /* add all possible neighbor blocks into queue */
        if (i >= N)    {
            /* 0 up */
            neigh = new Board(block);
            exch(neigh.block, i, i-N);
            bq.add(neigh);
        }
        if (i < block.length - N) {
            /* 0 down */
            neigh = new Board(block);
            exch(neigh.block, i, i+N);
            bq.add(neigh);
        }
        if (i % N != 0) {
            /* 0 left */
            neigh = new Board(block);
            exch(neigh.block, i, i-1);
            bq.add(neigh);
        }
        if ((i+1) % N != 0) {
            /* 0 right */
            neigh = new Board(block);
            exch(neigh.block, i, i+1);
            bq.add(neigh);
        }
        return bq;
    }     // all neighboring boards
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
}
