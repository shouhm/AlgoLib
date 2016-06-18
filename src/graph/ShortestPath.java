package graph;

import java.util.*;

import graph.Graph.WeightToEdge;

/**
 * Created by shouhm on 16/5/30.
 */
public class ShortestPath {

    static public int[] Dijkstra(int n, int src, ArrayList<WeightToEdge>[] g) {
        int[] dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] vis = new boolean[n]; Arrays.fill(vis, false);
        dist[src] = 0;
        for (int i = 0; i < n; i ++) {
            int bestV = -1;
            int minDis = Integer.MAX_VALUE;
            for (int j = 0; j < n; j ++) {
                if (!vis[j] && dist[j] < minDis) {
                    minDis = dist[j];
                    bestV = j;
                }
            }
            vis[bestV] = true;
            for (WeightToEdge edge : g[bestV]) {
                int u = edge.to;
                if (!vis[u])
                    dist[u] = Math.min(dist[u], dist[bestV] + edge.w);
            }
        }
        return dist;
    }

    static public int DijkstraWithHeap(int n, int src, ArrayList<Integer>[] g) {
        //TODO Heap implementation
        return 0;
    }

    static public int[][] FloydWarshall(int n, int[][] g) {
        int[][] f = new int[n][n];
        for (int i = 0; i < n; i ++)
            for (int j = 0; j < n; j ++)
                f[i][j] = g[i][j];

        for (int k = 0; k < n; k ++)
            for (int i = 0; i < n; i ++)
                for (int j = 0; j < n; j ++)
                    f[i][j] = Math.min(f[i][j], f[i][k] + f[k][j]);
        return f;
    }

    static public int[] SPFA(int n, int src, ArrayList<WeightToEdge>[] g) {
        int[] dist = new int[n]; Arrays.fill(dist, Integer.MAX_VALUE);
        boolean[] inQueue = new boolean[n]; Arrays.fill(inQueue, false);
        dist[src] = 0;
        Queue<Integer> queue = new LinkedList<>();
        queue.add(src); inQueue[src] = true;
        while (!queue.isEmpty()) {
            int u = queue.poll();
            for (WeightToEdge e : g[u]) {
                int v = e.to;
                if (dist[u] + e.w < dist[v]) {
                    dist[v] = dist[u] + e.w;
                    if (!inQueue[v]) {
                        queue.add(v);
                        inQueue[v] = true;
                    }
                }
            }
            inQueue[u] = false;
        }
        return dist;
    }


}

