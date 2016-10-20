import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int n;
    private WeightedQuickUnionUF grid;

    // 0 = closed, 1 = open, 2 connected to bottom, 3 = connected to top, 4 connected to top and bottom
    private byte[] siteStatuses;
    private boolean percolationStatus;

    public Percolation(int n) {
        this.percolationStatus = false;

        if (n < 1) {
            throw new IllegalArgumentException("Please specify a value of n > 0");
        }

        int gridSize = n * n;
        this.grid = new WeightedQuickUnionUF(gridSize);
        this.n = n;
        this.siteStatuses = new byte[gridSize];

        for (int i = 0; i < gridSize; i++) {
            this.siteStatuses[i] = 0;
        }
    }

    private int xyToId(int x, int y) {
        int index = -1;

        if (x > 0 && y > 0 && x <= this.n && y <= this.n) {
            index = (((x - 1) * n) + y) - 1;
        }

        return index;
    }

    private void checkBounds(int x, int y, int index) {
        if (index < 0) {
            throw new IndexOutOfBoundsException(String.format("Point (%d, %d) is out of bounds", x, y));
        }
    }

    private void connect(int indexP, int rowQ, int colQ) {
        int indexQ = this.xyToId(rowQ, colQ);
        if (indexQ > -1 && this.isOpen(rowQ, colQ)) {

            int rootQ = this.grid.find(indexQ);
            int rootP = -1;

            if (this.connectedToBottom(rootQ, indexQ, rowQ)) {
                rootP = this.grid.find(indexP);
                this.connectToBottom(rootP, indexP);
            }

            if (this.connectedToTop(rootQ, indexQ, rowQ)) {
                if (rootP < 0) {
                    rootP = this.grid.find(indexP);
                }
                this.connectToTop(rootP, indexP);
            }

            this.grid.union(indexP, indexQ);
        }
    }

    private boolean connectedToBottom(int root, int index, int row) {
        if ((row == this.n && this.siteStatuses[index] > 0)
                || this.siteStatuses[index] == 2 || this.siteStatuses[index] == 4
                || this.siteStatuses[root] == 2 || this.siteStatuses[root] == 4) {
            return true;
        }

        return false;
    }

    private void connectToBottom(int root, int index) {
        if (this.siteStatuses[root] == 3 || this.siteStatuses[root] == 4) {
            this.siteStatuses[root] = 4;
            this.siteStatuses[index] = 4;
            this.percolationStatus = true;
        } else {
            this.siteStatuses[root] = 2;
            this.siteStatuses[index] = 2;
        }
    }

    private boolean connectedToTop(int root, int index, int row) {
        if ((row == 1 && this.siteStatuses[index] > 0)
                || this.siteStatuses[index] == 3 || this.siteStatuses[index] == 4
                || this.siteStatuses[root] == 3 || this.siteStatuses[root] == 4) {
            return true;
        }

        return false;
    }

    private void connectToTop(int root, int index) {
        if (this.siteStatuses[root] == 2 || this.siteStatuses[root] == 4) {
            this.siteStatuses[root] = 4;
            this.siteStatuses[index] = 4;
            this.percolationStatus = true;
        } else {
            this.siteStatuses[root] = 3;
            this.siteStatuses[index] = 3;
        }
    }


    public void open(int row, int col) // open site (row, col) if it is not open already
    {
        int index = this.xyToId(row, col);
        checkBounds(row, col, index);

        if (!this.isOpen(row, col)) {
            this.siteStatuses[index] = 1;

            this.connect(index, row, col - 1); // Left
            this.connect(index, row, col + 1); // Right
            this.connect(index, row - 1, col); // Up
            this.connect(index, row + 1, col); // Down

            int root = this.grid.find(index);
            boolean bottom = this.connectedToBottom(root, index, row);

            if (bottom) {
                this.connectToBottom(root, index);
            }

            boolean top = this.connectedToTop(root, index, row);
            if (top) {
                this.connectToTop(root, index);
            }
        }
    }

    public boolean isOpen(int row, int col) // is site (row, col) open?
    {
        int index = this.xyToId(row, col);
        checkBounds(row, col, index);
        return this.siteStatuses[index] > 0;
    }

    public boolean isFull(int row, int col) // is site (row, col) full?
    {
        int index = this.xyToId(row, col);
        checkBounds(row, col, index);

        int root = this.grid.find(index);
        if (this.connectedToTop(root, index, row)) {
            this.connectToTop(root, index);
        }
        return this.siteStatuses[index] > 2;
    }

    public boolean percolates() // does the system percolate?
    {
        return this.percolationStatus;
    }

    public static void main(String[] args) // test client (optional)
    {
        Percolation perc = new Percolation(5);
        System.out.println(String.format("Full (4,3): %b", perc.isFull(4, 3)));
        System.out.println(String.format("Percolates: %b", perc.percolates()));

        perc.open(1, 2);
        perc.open(2, 2);
        perc.open(3, 2);
        perc.open(4, 2);
        perc.open(4, 3);
        System.out.println(String.format("Full (4,3): %b", perc.isFull(4, 3)));
        perc.open(5, 3);

        System.out.println(String.format("Percolates: %b", perc.percolates()));
    }
}
