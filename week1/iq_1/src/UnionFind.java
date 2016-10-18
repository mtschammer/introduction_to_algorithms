public class UnionFind {
    private int[] id;
    private int[] size;
    private int largestTreeSize = 1;

    private UnionFind(int id) {
        this.id = new int[id];
        this.size = new int[id];


        for (int i = 0; i < id; i++) {
            this.id[i] = i;
            this.size[i] = 1;
        }
    }

    private int root(int id) {
        while (this.id[id] != id) {
            id = this.id[this.id[id]];
        }

        return id;
    }

    public boolean union(int p, int q) {
        int pRoot = this.root(p);
        int qRoot = this.root(q);

        if (pRoot != qRoot) {
            int newTreeSize = 0;
            if (this.size[pRoot] >= this.size[qRoot]) {
                this.id[qRoot] = pRoot;
                this.size[pRoot] += this.size[qRoot];
                newTreeSize = this.size[pRoot];
            } else {
                this.id[pRoot] = qRoot;
                this.size[qRoot] += this.size[pRoot];
                newTreeSize = this.size[qRoot];
            }

            if (newTreeSize > this.largestTreeSize) {
                this.largestTreeSize = newTreeSize;
            }

            return true;
        }

        return false;
    }

    public boolean connected(int p, int q) {
        return (this.root(p) == this.root(q));
    }

    public boolean allConnected() {
        return (this.largestTreeSize == this.id.length);
    }

    public static void main(String[] args) {
        int[][] log = new int[][]{
                {0, 1}, // 9
                {1, 2}, // 8
                {2, 3}, // 7
                {3, 1}, // 7
                {2, 8}, // 6
                {3, 4}, // 5
                {4, 5}, // 4
                {4, 5}, // 4
                {5, 6}, // 3
                {6, 7}, // 2
                {7, 8}, // 2
                {8, 9}, // 1 <-- (index: 11)
                {2, 9},
                {0, 9},
        };

        UnionFind unionFind = new UnionFind(10);

        for (int i = 0; i < log.length; i++) {
            unionFind.union(log[i][0], log[i][1]);
            if (unionFind.allConnected()) {
                System.out.println(String.format("All members connected at %d (%d, %d)", i, log[i][0], log[i][1]));
                return;
            }
        }
    }
}
