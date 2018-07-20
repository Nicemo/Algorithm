import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] attemps;

    public PercolationStats(int n, int t) {
        attemps = new double[t];
        for (int i = 0; i < t; i++) {
            Percolation perc = new Percolation(n);
            int steps = 0;
            while (!perc.percolates()) {
                int row = StdRandom.uniform(n) + 1;
                int col = StdRandom.uniform(n) + 1;
                if (!perc.isOpen(row, col)) {
                    perc.open(row, col);
                    steps++;
                }
            }
            attemps[i] = (double)steps/(n * n);
        }
    }
    public double mean(){
        return StdStats.mean(attemps);
    }
    // sample standard deviation of percolation threshold
    public double stddev(){
        return StdStats.stddev(attemps);
    }
    // returns lower bound of the 95% confidence interval
    public double confidenceLo(){
        return mean()-((1.96*stddev())/Math.sqrt(attemps.length));
    }
    // returns upper bound of the 95% confidence interval
    public double confidenceHi(){
        return mean()+((1.96*stddev())/Math.sqrt(attemps.length));
    }
    // test client, described below
    public static void main(String[] args){
        PercolationStats ps=new PercolationStats(300,1000);
        StdOut.print("mean = "+ps.mean()+"\n");
        StdOut.print("std dev = "+ps.stddev()+"\n");
        StdOut.print("95% confidence interval = "+ps.confidenceLo()+", "+ps.confidenceHi());
    }
}
