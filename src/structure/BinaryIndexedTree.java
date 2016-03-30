package structure;

public class BinaryIndexedTree {
    private int tree[];
    private int N;

    public BinaryIndexedTree(int n) {
        N = n;
        tree = new int[N + 10];
    }

    private int lowbit(int x) {
        return (x & -x);
    }

    public void add(int x, int value) {
        for (int i = x; i <= N; i += lowbit(i))
            tree[i] += value;
    }

    private int get(int x) {
        int sum = 0;
        for (int i = x; i > 0; i -= lowbit(i))
            sum += tree[i];
        return sum;
    }
}
