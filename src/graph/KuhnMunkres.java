package graph;

import java.util.*;

public class KuhnMunkres {
    static int N;
    static int[][] g;

    static int[] lx, ly;
    static int[] matchY;
    static boolean[] visX, visY;
    static int[] slack;

    static public int maximumWeightMatch(int n, int[][] graph) {
        N = n;
        g = graph;

        lx = new int[N];
        ly = new int[N];
        matchY = new int[N];
        slack = new int[N];
        visX = new boolean[N];
        visY = new boolean[N];

        for (int y = 0; y < N; y ++) {
            ly[y] = 0;
            matchY[y] = -1;
        }

        for (int x = 0; x < N; x ++) {
            lx[x] = 0;
            for (int y = 0; y < N; y ++)
                lx[x] = Math.max(lx[x], g[x][y]);
        }

        for (int x = 0; x < N; x ++) {
            Arrays.fill(slack, Integer.MAX_VALUE);
            while (true) {
                Arrays.fill(visX, false);
                Arrays.fill(visY, false);
                if (dfs(x)) break;
                int d = Integer.MAX_VALUE;

                for (int y = 0; y < N; y ++)
                    if (!visY[y] && slack[y] < d)
                        d = slack[y];

                for (int xx = 0; xx < N; xx ++)
                    if (!visX[xx]) lx[xx] -= d;

                for (int y = 0; y < N; y ++)
                    if (visY[y]) ly[y] += d; else slack[y] -= d;
            }
        }

        int sum = 0;
        for (int y = 0; y < N; y ++) {
            if (matchY[y] > -1)
                sum += g[matchY[y]][y];
        }
        return sum;
    }

    static private boolean dfs(int x) {
        visX[x] = true;
        for (int y = 0; y < N; y ++) {
            if (visY[y]) continue;
            int t = lx[x] + ly[y] - g[x][y];
            if (t == 0) {
                visY[y] = true;
                if (matchY[y] == -1 || dfs(matchY[y])) {
                    matchY[y] = x;
                    return true;
                }
            } else {
                if (t < slack[y]) slack[y] = t;
            }
        }
        return false;
    }
}
