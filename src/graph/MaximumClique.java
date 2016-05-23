package graph;

import java.util.*;
import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/17.
 */
public class MaximumClique {

    static int N;
    static ArrayList<Integer>[] g;

    static int ans;
    static int[] mc;

    static public int findMaxmiumClique(int n, List<Edge> edges) {
        N = n;
        g = Graph.getNonDirectedAdjList(n, edges);

        mc = new int[N+1];
        ans = 1;
        for (int i = N - 1; i >= 0; i --) {
            boolean found = false;
            DFS(1);
            mc[i] = ans;
        }

        return ans;
    }

    static private void DFS(int node) {

    }
}
