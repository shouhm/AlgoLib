package graph;

import java.util.*;
import java.util.stream.*;

import graph.Graph.Edge;

public class CutBridgeSolver {

    static int N;
    static int[] low, dfn, vis;
    static ArrayList<Integer>[] g;
    static boolean[] Cut;
    static List<Edge> Bridge;
    static int totalBridges;

    static public void cutBridge(int n, List<Edge> edges, boolean[] cut, List<Edge> bridge) {
        N = n;
        g = Graph.getNonDirectedAdjList(N, edges);
        vis = new int[N]; dfn = new int[N]; low = new int[N];
        Cut = cut; Bridge = bridge; totalBridges = 0;
        cutBridgeDFS(0, -1, 0);
    }

    static public void cutBridgeDFS(int x, int fa, int dep) {
        vis[x] = 1; dfn[x] = dep; low[x] = dep;
        int children = 0;
        for (int y : g[x]) {
            if (y != fa && vis[y] == 1) {
                if (dfn[y] < low[x])
                    low[x] = dfn[y];
            }
            if (vis[y] == 0) {
                cutBridgeDFS(y, x, dep + 1);
                children ++;
                low[x] = Math.min(low[x], low[y]);
                if ((fa == -1 && children > 1) || (fa != -1 && low[y] >= dfn[x]))
                    Cut[x] = true;
                if (low[y] > dfn[x])
                    Bridge.add(new Edge(x, y));
            }
        }
        vis[x] = 2;
    }
}
