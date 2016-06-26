package graph;

import java.util.*;

public class Blossom {

    static int N;
    static ArrayList<Integer>[] g;
    static int[] match, prev, label;
    static boolean[] inq, inBlossom, inPath;
    static Queue<Integer> queue;
    static int S;

    static public int maximumMatch(int n, ArrayList<Integer>[] graph) {
        N = n;
        g = graph;

        match = new int[N]; Arrays.fill(match, -1);
        prev = new int[N];
        label = new int[N];
        inq = new boolean[N];
        inBlossom = new boolean[N];
        inPath = new boolean[N];

        int ans = 0;
        for (S = 0; S < N; S ++) {
            if (match[S] == -1)
                if (findAugmentPath())
                    ans ++;
        }
        return ans;
    }

    static public boolean findAugmentPath() {
        for (int i = 0; i < N; i ++) {
            prev[i] = -1;
            label[i] = i;
            inq[i] = false;
        }

        queue = new LinkedList<>();
        queue.add(S); inq[S] = true;
        while (!queue.isEmpty()) {
            int x = queue.poll();
            for (int y : g[x]) {
                if (label[x] == label[y] || x == match[y]) continue;
                if (y == S || (match[y] >= 0 && prev[match[y]] >= 0))
                    blossomContract(x, y);
                else if (prev[y] == -1) {
                    prev[y] = x;
                    if (match[y] >= 0) {
                        queue.add(match[y]);
                        inq[match[y]] = true;
                    } else {                                //Augment path!
                        int u = y, v, w;
                        while (u != 0) {
                            v = prev[u]; w = match[v];
                            match[u] = v; match[v] = u;
                            u = w;
                        }
                        return true;
                    }
                }
            }
        }

        return false;
    }

    static private void blossomContract(int x, int y) {
        int lca = findCommonAncestor(x, y);
        Arrays.fill(inBlossom, false);
        resetTrace(x, lca); if (label[x] != lca) prev[x] = y;
        resetTrace(y, lca); if (label[y] != lca) prev[y] = x;
        for (int i = 0; i < N; i ++)
            if (inBlossom[ label[i] ]) {
                label[i] = lca;
                if (!inq[i]) { queue.add(i); inq[i] = true; }
            }
    }

    static private int findCommonAncestor(int x, int y) {
        Arrays.fill(inPath, false);
        for (; ; x = prev[ match[x] ]) { x = label[x]; inPath[x] = true; if (x == S) break; }
        for (; ; y = prev[ match[y] ]) { y = label[y]; if (inPath[y]) break; }
        return y;
    }

    static private void resetTrace(int x, int lca) {
        while (label[x] != lca) {
            int y = match[x];
            inBlossom[ label[x] ] = true; inBlossom[ label[y] ] = true;
            x = prev[y];
            if (label[x] != lca) prev[x] = y;
        }
    }
}
