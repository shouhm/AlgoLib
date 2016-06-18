package graph;

import java.util.*;

public class Graph {

    static public class Edge {
        public int in, out;
        public Edge(int _in, int _out) { in = _in; out = _out; }
    }

    static public class WeightToEdge {
        public int to, w;
        public WeightToEdge(int _to, int _w) { w = _w; to = _to; }
    }

    static public class FlowEdge {
        public int to, c;
        FlowEdge reverse;
        public FlowEdge(int _to, int _c) {
            to = _to; c = _c; reverse = null;
        }
    }

    static public ArrayList<Integer>[] getDirectedAdjList(int N, List<Edge> edges) {
        ArrayList<Integer>[] g = new ArrayList[N];
        for (int x = 0; x < N; x ++)
            g[x] = new ArrayList<Integer>();
        for (Edge e : edges)
            g[e.in].add(e.out);
        return g;
    }

    static public ArrayList<Integer>[] getReverseDirectedAdjList(int N, List<Edge> edges) {
        ArrayList<Integer>[] g = new ArrayList[N];
        for (int x = 0; x < N; x ++)
            g[x] = new ArrayList<Integer>();
        for (Edge e : edges)
            g[e.out].add(e.in);
        return g;
    }

    static public ArrayList<Integer>[] getNonDirectedAdjList(int N, List<Edge> edges) {
        ArrayList<Integer>[] g = new ArrayList[N];
        for (int x = 0; x < N; x ++)
            g[x] = new ArrayList<Integer>();
        for (Edge e : edges) {
            g[e.in].add(e.out);
            g[e.out].add(e.in);
        }
        return g;
    }

    static public boolean[][] getNonDirectedAdjMat(int N, List<Edge> edges) {
        boolean[][] g = new boolean[N][N];
        for (int x = 0; x < N; x ++)
            Arrays.fill(g[x], false);
        for (Edge e : edges) {
            g[e.in][e.out] = true;
            g[e.out][e.in] = true;
        }
        return g;
    }
}
