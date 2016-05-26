package graph;

import java.util.*;
import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/17.
 */
public class MaximumClique {

    static int N;
    static boolean[][] g;

    static int ans;
    static int[] mc;

    static int[][] candidate;
    static int[] len;
    static boolean found;

    static public int findMaxmiumClique(int n, List<Edge> edges) {
        N = n;
        //graphAdjList = Graph.getNonDirectedAdjList(n, edges);
        g = Graph.getNonDirectedAdjMat(n, edges);

        mc = new int[N+1];
        ans = 1;

        len = new int[N+2];
        candidate = new int[N+2][N+2];

        for (int i = N - 1; i >= 0; i --) {
            found = false;
            len[0] = 0;
            for (int j = i + 1; j < N; j ++)
                candidate[0][len[0]++] = j;
            DFS(1);
            mc[i] = ans;
        }

        return ans;
    }

    static private void DFS(int size) {
        if (len[size - 1] == 0) {
            if (size > ans) {
                ans = size;
                found = true;
            }
            return;
        }

        for (int k = 0; k < len[size-1] && !found; k ++) {
            if (size + len[size-1] - k <= ans) break;
            int x = candidate[size-1][k];
            if (size + mc[x] <= ans) break;

            len[size] = 0;
            for (int j = k + 1; j < len[size-1]; j ++) {
                if (g[x][candidate[size-1][j]])
                    candidate[size][len[size] ++] = candidate[size-1][j];
            }
            DFS(size + 1);
        }
    }
}
