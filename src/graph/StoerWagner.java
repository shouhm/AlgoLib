package graph;

/**
 * Created by shouhm on 16/5/30.
 */
public class StoerWagner {

    static int S, T;
    static int N;
    static int[][] G;
    static boolean[] used;
    static int[] dist;
    static int ans;

    static public int globalMinCut(int n, int[][] g) {
        N = n;
        G = new int[N][N];
        for (int i = 0; i < N; i ++)
            for (int j = 0; j < N; j ++)
                G[i][j] = g[i][j];
        used = new boolean[N];
        dist = new int[N];
        ans = Integer.MAX_VALUE;

        for (int i = 1; N > 1; i ++, N --) {
            minCutPhase();
            mergeVertex();
        }

        return ans;
    }

    static private void minCutPhase() {
        for (int i = 0; i < N; i ++) used[i] = false;
        used[0] = true;

        for (int i = 0; i < N; i ++) dist[i] = G[i][0];
        S = -1; T = 0;
        for (int round = 1; round <= N - 1; round ++) {
            int x = -1;
            for (int i = 0; i < N; i ++)
                if (!used[i])
                    if (x == -1 || dist[i] > dist[x]) x = i;
            used[x] = true;
            S = T; T = x;
            for (int i = 0; i < N; i ++)
                dist[i] += G[x][i];
        }
        ans = Math.max(ans, dist[T]);
    }

    static private void mergeVertex() {
        for (int i = 0; i < N; i ++) {
            G[i][S] += G[i][T];
            G[S][i] += G[i][T];
        }
        G[S][S] = 0;
        for (int i = 0; i < N; i ++) {
            int t = G[i][T]; G[i][N-1] = G[i][T]; G[i][T] = t;
        }
        for (int i = 0; i < N; i ++) {
            int t = G[T][i]; G[T][i] = G[N-1][i]; G[N-1][i] = t;
        }
        N --;
    }
}
