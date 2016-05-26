package graph;

import java.util.*;

import graph.Graph.Edge;

public class LowestCommonAncestor {
    public int N;
    public int root;
    public int[] dep;
    public int[] log;
    public int[][] anc;
    public ArrayList<Integer>[] g;
    public int maxH;

    public LowestCommonAncestor(int n, int rootNode, List<Edge> edges) {
        N = n;
        root = rootNode;
        g = Graph.getNonDirectedAdjList(n, edges);

        dep = new int[N+1];
        log = new int[N+1];
        log[1] = 0; for (int i = 2; i <= n; i ++) log[i] = log[i/2] + 1;
        maxH = log[N] + 2;
        anc = new int[n][maxH];

        dep[root] = 1;
        for (int i = 0; i < maxH; i ++) anc[root][i] = root;
        DFS(root);
    }

    private void DFS(int x) {
        if (x != root) {
            for (int i = 1; i < maxH; i ++) {
                int y = anc[x][i-1];
                anc[x][i] = anc[y][i-1];
            }
        }
        for (int y : g[x]) {
            if (y != anc[x][0]) {
                dep[y] = dep[x] + 1;
                anc[y][0] = x;
                DFS(y);
            }
        }
    }

    private int ancestor(int x, int H) {
        for (int i = 0; H > 0; i ++) {
            if (H % 2 == 1) x = anc[x][i];
            H /= 2;
        }
        return x;
    }

    public int lca(int x, int y) {
        if (dep[x] > dep[y]) { int t = x; x = y; y = t; }

        //System.out.println("before swim: " + x + " " + y);
        y = ancestor(y, dep[y] - dep[x]);

        //System.out.println("after swim: " + x + " " + y);

        if (x == y) return x;
        int top = 1, bottom = dep[x]-1, ans = -1;
        while (top <= bottom) {

            //System.out.println("top = " + top + " bottom = " + bottom);

            int level = log[bottom - top + 1];
            int lx = anc[x][level], ly = anc[y][level];

            int mid = bottom - (1 << level) + 1;

            //System.out.println("mid try = " + mid + " lx = " + lx + " ly = " + ly);
            if (lx == ly) {
                ans = lx;
                top = mid + 1;
            } else {
                bottom = mid - 1;
                x = lx; y = ly;
            }
        }
        return ans;
    }
}
