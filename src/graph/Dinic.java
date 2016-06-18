package graph;

import java.util.*;

import graph.Graph.FlowEdge;

/**
 * Created by shouhm on 16/5/31.
 */
public class Dinic {

    static public int[] dist;
    static public boolean[] vis;
    static public int N;
    static ArrayList<FlowEdge>[] g;
    static int Src;
    static int Sink;

    static public int maxFlow(int n, int src, int sink, ArrayList<FlowEdge>[] graph) {
        N = n;
        Src = src; Sink = sink;
        g = graph;

        int F = 0;
        while (true) {
            BFS();
            if (!vis[Sink]) return F;
            F += DFS(Src, Integer.MAX_VALUE);
        }
    }

    static private void BFS() {

        dist = new int[N]; Arrays.fill(dist, 0);
        vis = new boolean[N]; Arrays.fill(vis, false);

        Queue<Integer> queue = new LinkedList<>();
        vis[Src] = true;
        queue.add(Src);
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (FlowEdge e : g[u]) {
                if (e.c > 0 && !vis[e.to]) {
                    queue.add(e.to);
                    dist[e.to] = dist[u] + 1;
                    vis[e.to] = true;
                }
            }
        }
    }

    static private int DFS(int u, int delta) {
        if (u == Sink) {
            return delta;
        }
        int ret = 0;
        for (int j = 0; j < g[u].size(); j ++) {
            FlowEdge e = g[u].get(j);
            int v = e.to;
            if (e.c > 0 && dist[v] == dist[u] + 1) {
                int dd = DFS(v, Math.min(e.c, delta));
                e.c -= dd;
                e.reverse.c += dd;
                delta -= dd;
                ret += dd;
            }
        }
        return ret;
    }

}
