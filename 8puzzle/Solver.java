import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private class searchNode {
        private int move;
        private Board board;
        private searchNode preNode;

        public searchNode(Board b) {
            board = b;
            move = 0;
            preNode = null;
        }
    }
    private searchNode goal;
    private class PriorityOrder implements Comparator<searchNode> {
        @Override
        public int compare(searchNode s1, searchNode s2) {
            int priority1 = s1.board.manhattan() + s1.move;
            int priority2 = s2.board.manhattan() + s2.move;

            if         (priority1 > priority2) return 1;
            else if (priority1 < priority2) return -1;
            else                             return 0;
        }
    }
    public Solver(Board initial) {
        PriorityOrder po = new PriorityOrder();
        MinPQ<searchNode> pq = new MinPQ<searchNode>(po);
        searchNode sn = new searchNode(initial);

        PriorityOrder twinPo = new PriorityOrder();
        MinPQ<searchNode> twinPq = new MinPQ<searchNode>(twinPo);
        searchNode twinSn = new searchNode(initial.twin());
        pq.insert(sn);
        twinPq.insert(twinSn);

        searchNode minNode = pq.delMin();
        searchNode twinMinNode = twinPq.delMin();
        while (!minNode.board.isGoal() && !twinMinNode.board.isGoal()) {
            for (Board b : minNode.board.neighbors()) {
                if ((minNode.preNode == null)
                        || !b.equals(minNode.preNode.board)) {
                    searchNode node = new searchNode(b);
                    node.move = minNode.move + 1;
                    node.preNode = minNode;
                    pq.insert(node);
                }
            }
            for (Board b : twinMinNode.board.neighbors()) {
                if ((minNode.preNode == null)
                        || !b.equals(twinMinNode.preNode.board)) {
                    searchNode node = new searchNode(b);
                    node.move = twinMinNode.move + 1;
                    node.preNode = twinMinNode;
                    twinPq.insert(node);
                }
            }
            minNode = pq.delMin();
            twinMinNode = twinPq.delMin();
        }

        if (minNode.board.isGoal())
            goal = minNode;
        else
            goal = null;

    }           // find a solution to the initial board (using the A* algorithm)
    public boolean isSolvable() {
        return goal != null;
    }            // is the initial board solvable?
    public int moves() {
        return goal.move;
    }                     // min number of moves to solve initial board; -1 if unsolvable
    public Iterable<Board> solution() {
        if (!isSolvable()) return null;
        Stack<Board> bS = new Stack<>();
        for (searchNode s = goal; s != null; s = s.preNode)
            bS.push(s.board);
        return bS;
    }      // sequence of boards in a shortest solution; null if unsolvable

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        // create initial board from file
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] blocks = new int[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
