package graph;

import java.util.*;
import java.util.stream.*;

import graph.Graph.Edge;

public class KosarajuSCC {

    static int N;
    static ArrayList<Integer>[] g1, g2;
    static ArrayList<Integer> order;
    static boolean[] vis;
    static ArrayList<Integer> component;

    static public List<List<Integer>> KosajaruSCC(int n, List<Edge> directedEdges) {
        ArrayList<List<Integer>> ret = new ArrayList<>();

        N = n;
        g1 = Graph.getDirectedAdjList(N, directedEdges);
        vis = new boolean[N]; Arrays.fill(vis, false);
        order = new ArrayList<Integer>();

        for (int x = 0; x < N; x ++) {
            if (!vis[x]) DFS1(x);
        }

        g2 = Graph.getReverseDirectedAdjList(N, directedEdges);
        vis = new boolean[N]; Arrays.fill(vis, false);
        Collections.reverse(order);
        for (int x : order) {
            if (!vis[x]) {
                component = new ArrayList<Integer>();
                DFS2(x);
                ret.add(component);
            }
        }
        return ret;
    }

    static private void DFS1(int x) {
        vis[x] = true;
        for (int y : g1[x])
            if (!vis[y]) DFS1(y);
        order.add(x);
    }

    static private void DFS2(int x) {
        vis[x] = true;
        component.add(x);
        for (int y : g2[x])
            if (!vis[y]) DFS2(y);
    }

}
