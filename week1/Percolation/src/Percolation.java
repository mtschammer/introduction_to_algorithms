import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF grid;
    private int gridSize;
    private boolean[] siteStatuses;

    public Percolation(int n) {

        if (n < 1) {
            throw new IllegalArgumentException("Please specify a value of n > 0");
        }

        this.gridSize = n * n + 2;
        this.grid = new WeightedQuickUnionUF(this.gridSize);
        this.n = n;
        this.siteStatuses = new boolean[this.gridSize];

        for (int i = 1; i < this.gridSize - 1; i++) {
            this.siteStatuses[i] = false;
        }

        this.siteStatuses[0] = this.siteStatuses[this.gridSize - 1] = true;
    }

    private int xyToId(int x, int y) {
        int index = -1;

        if (x > 0 && y > 0 && x <= this.n && y <= this.n) {
            index = ((x - 1) * n) + y;
        }

        return index;
    }

    private void checkBounds(int x, int y, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Point (%d, %d) is out of bounds", x, y));
        }
    }

    public void connect(int indexP, int rowQ, int colQ) {
        int indexQ = this.xyToId(rowQ, colQ);
        if (indexQ > -1 && this.isOpen(rowQ, colQ)) {
            this.grid.union(indexP, indexQ);
        }
    }

    public boolean connected(int rowP, int colP, int rowQ, int colQ) {
        int indexP = this.xyToId(rowP, colP);
        checkBounds(rowP, colP, indexP);

        int indexQ = this.xyToId(rowQ, colQ);
        checkBounds(rowQ, colQ, indexQ);

        return this.grid.connected(indexP, indexQ);
    }


    public void open(int row, int col)       // open site (row, col) if it is not open already
    {
        int index = this.xyToId(row, col);
        checkBounds(row, col, index);

        if (!this.isOpen(row, col)) {
            this.siteStatuses[index] = true;

            this.connect(index, row, col - 1); // Left
            this.connect(index, row, col + 1); // Right
            this.connect(index, row - 1, col); // Up
            this.connect(index, row + 1, col); // Down

            if (row == 1) {
                // Top
                this.grid.union(index, 0);
            } else if (row == this.n) {
                // Bottom
                this.grid.union(index, this.gridSize - 1);
            }
        }
    }

    public boolean isOpen(int row, int col)  // is site (row, col) open?
    {
        int index = this.xyToId(row, col);
        checkBounds(row, col, index);
        return this.siteStatuses[index];
    }

    public boolean isFull(int row, int col)  // is site (row, col) full?
    {
        int index = this.xyToId(row, col);
        checkBounds(row, col, index);
        return this.grid.connected(0, index);
    }

    public boolean percolates()              // does the system percolate?
    {
        return this.grid.connected(0, this.gridSize - 1);
    }

    public static void main(String[] args)   // test client (optional)
    {

        Percolation perc = new Percolation(5);
        System.out.println(String.format("Connected (1,2) and (2,2): %b", perc.connected(1, 2, 2, 2)));
        System.out.println(String.format("Full (4,3): %b", perc.isFull(4, 3)));
        System.out.println(String.format("Percolcates: %b", perc.percolates()));

        perc.open(1, 2);
        perc.open(2, 2);
        System.out.println(String.format("Connected (1,2) and (2,2): %b", perc.connected(1, 2, 2, 2)));
        perc.open(3, 2);
        perc.open(4, 2);
        perc.open(4, 3);
        System.out.println(String.format("Full (4,3): %b", perc.isFull(4, 3)));
        perc.open(5, 3);

        System.out.println(String.format("Percolcates: %b", perc.percolates()));
    }
}
