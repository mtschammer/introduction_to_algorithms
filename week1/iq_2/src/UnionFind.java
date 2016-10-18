public class UnionFind {
    private int[] id;
    private int[] size;
    private int[] largest;

    private UnionFind(int id) {
        this.id = new int[id];
        this.size = new int[id];
        this.largest = new int[id];


        for (int i = 0; i < id; i++) {
            this.id[i] = this.largest[i] = i;
            this.size[i] = 1;
        }
    }

    private int root(int id) {
        while (this.id[id] != id) {
            id = this.id[this.id[id]];
        }

        return id;
    }

    private int find(int id) {
        int rootId = this.root(id);
        return largest[rootId];
    }

    public boolean union(int p, int q) {
        int pRoot = this.root(p);
        int qRoot = this.root(q);

        if (pRoot != qRoot) {
            if (this.size[pRoot] >= this.size[qRoot]) {
                this.id[qRoot] = pRoot;
                this.size[pRoot] += this.size[qRoot];

            } else {
                this.id[pRoot] = qRoot;
                this.size[qRoot] += this.size[pRoot];
            }

            if (this.largest[qRoot] > this.largest[pRoot]) {
                this.largest[pRoot] = this.largest[qRoot];
            } else {
                this.largest[qRoot] = this.largest[pRoot];
            }


            return true;
        }

        return false;
    }

    public boolean connected(int p, int q) {
        return (this.root(p) == this.root(q));
    }

    public static void main(String[] args) {
        int[][] log = new int[][]{
                {0, 1},
                {1, 2},
                {2, 3},
                {3, 1},
                {2, 8},
                {4, 5},
                {5, 6},
                {6, 7},
                // {0, 1, 2, 3, 8}, {4, 5, 6, 7}, {9}
        };

        UnionFind unionFind = new UnionFind(10);

        for (int i = 0; i < log.length; i++) {
            unionFind.union(log[i][0], log[i][1]);
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("%d: %d", i, unionFind.find(i)));
            // 0: 8
            // 1: 8
            // 2: 8
            // 3: 8
            // 4: 7
            // 5: 7
            // 6: 7
            // 7: 7
            // 8: 8
            // 9: 9

        }
    }
}
