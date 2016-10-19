import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
// import edu.princeton.cs.algs4.Stopwatch;

public class PercolationStats {
    private int trials;
    private double[] percolationThresholds;
    private double calculatedMean = -1;
    private double calculatedStddev = -1;
    private double calculatedConfidenceLo = -1;
    private double calculatedConfidenceHi = -1;

    // perform trials independent experiments on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n < 1 || trials < 1) {
            throw new IllegalArgumentException("Values for 'n' and 'trials' need to be > 0");
        }

        this.trials = trials;
        this.percolationThresholds = new double[trials];

        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            int openedTiles = 0;

            do {
                int randomRow = StdRandom.uniform(1, n + 1);
                int randomCol = StdRandom.uniform(1, n + 1);

                if (!perc.isOpen(randomRow, randomCol)) {
                    openedTiles++;
                    perc.open(randomRow, randomCol);
                }
            } while (openedTiles < n - 1 || !perc.percolates());

            this.percolationThresholds[i] = openedTiles / (double) (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        if (this.calculatedMean > -1) {
            return this.calculatedMean;
        }

        this.calculatedMean = StdStats.mean(this.percolationThresholds);

        return this.calculatedMean;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        if (this.calculatedStddev > -1) {
            return this.calculatedStddev;
        }

        this.calculatedStddev = StdStats.stddev(this.percolationThresholds);

        return this.calculatedStddev;
    }

    // low  endpoint of 95% confidence interval
    public double confidenceLo() {
        if (this.calculatedConfidenceLo > -1) {
            return this.calculatedConfidenceLo;
        }

        this.calculatedConfidenceLo = this.mean() - ((1.96 * this.stddev()) / Math.sqrt(this.trials));

        return this.calculatedConfidenceLo;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        if (this.calculatedConfidenceHi > -1) {
            return this.calculatedConfidenceHi;
        }

        this.calculatedConfidenceHi = this.mean() + ((1.96 * this.stddev()) / Math.sqrt(this.trials));

        return this.calculatedConfidenceHi;
    }

    // test client (described below)
    public static void main(String[] args) {
        // Stopwatch swatch = new Stopwatch();
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);

        PercolationStats percStats = new PercolationStats(n, trials);
        System.out.println(String.format("mean\t= %f", percStats.mean()));
        System.out.println(String.format("stddev\t= %f", percStats.stddev()));
        System.out.println(String.format("95%% confidence interval\t= %f, %f", percStats.confidenceLo(),
                percStats.confidenceHi()));

        // System.out.println(String.format("\nElapsed time: %f seconds.", swatch.elapsedTime()));
    }
}