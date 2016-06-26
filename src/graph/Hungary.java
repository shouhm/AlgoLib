package graph;

import java.util.*;

/**
 * Created by shouhm on 16/6/18.
 */
public class Hungary {

    static ArrayList<Integer>[] g;
    static int N;

    static boolean[] vis;
    static int[] match;

    static public int maximumMatch(int n, ArrayList<Integer>[] graph) {
        N = n;
        g = graph;
        vis = new boolean[N];
        match = new int[N]; Arrays.fill(match, -1);

        int matches = 0;
        for (int u = 0; u < N; u ++) {
            Arrays.fill(vis, false);
            if (dfs(u)) matches ++;
        }

        return matches;
    }

    static private boolean dfs(int u) {
        if (vis[u]) return false;
        vis[u] = true;
        for (int v : g[u]) {
            if (match[v] == -1 || dfs(v)) {
                match[v] = u;
                return true;
            }
        }
        return false;
    }
}
