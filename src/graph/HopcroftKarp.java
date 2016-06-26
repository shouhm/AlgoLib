package graph;

import java.util.*;

public class HopcroftKarp {
    static ArrayList<Integer>[] g;
    static int N1, N2;

    static int[] mx, my;
    static int[] dx, dy;
    static boolean[] vis;

    static public int maximumMatch(int n1, int n2, ArrayList<Integer>[] graph) {
        N1 = n1; N2 = n2;
        g = graph;

        mx = new int[N1]; Arrays.fill(mx, -1);
        my = new int[N2]; Arrays.fill(my, -1);

        dx = new int[N1];
        dy = new int[N2];
        vis = new boolean[N1];

        int match = 0;
        while (bfs()) {
            Arrays.fill(vis, false);
            for (int u = 0; u < N1; u ++) {
                if (mx[u] == -1 && dfs(u)) match ++;
            }
        }
        return match;
    }

    static private boolean bfs() {
        Arrays.fill(dx, -1);
        Arrays.fill(dy, -1);
        Queue<Integer> queue = new LinkedList<>();

        for (int u = 0; u < N1; u ++)
            if (mx[u] != -1)
                queue.add(u);

        boolean updated = false;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (int v : g[u]) {
                if (dy[v] == -1) {
                    dy[v] = dx[u] + 1;
                    if (my[v] != -1) {
                        dx[my[v]] = dy[v] + 1;
                        queue.add(my[v]);
                    } else {
                        updated = true;
                    }
                }
            }
        }
        return updated;
    }

    static private boolean dfs(int u) {
        for (int v : g[u]) {
            if (!vis[v] && dy[v] == dx[u] + 1) {
                vis[v] = true;
                if (my[v] != -1 || dfs(my[v])) {
                    mx[u] = v;
                    my[v] = u;
                    return true;
                }
            }
        }
        return false;
    }
}
