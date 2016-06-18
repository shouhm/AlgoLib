package graph;

import java.util.*;

import graph.Graph.FlowEdge;

public class HighestLabelPushRelabel {
    static public int N;
    static ArrayList<FlowEdge>[] g;
    static int Src;
    static int Sink;

    static int[] dist;
    static int[] excess;
    static int[] count;
    static boolean[] active;
    static Stack<Integer>[] B;
    static int b;

    static public int maxFlow(int n, int src, int sink, ArrayList<FlowEdge>[] graph) {
        N = n;
        Src = src; Sink = sink;
        g = graph;

        dist = new int[N]; Arrays.fill(dist, 0);
        excess = new int[N]; Arrays.fill(excess, 0);
        count = new int[2 * N]; Arrays.fill(count, 0);
        active = new boolean[N]; Arrays.fill(active, false);
        B = new Stack[N]; for (int i = 0; i < N; i ++) B[i] = new Stack<>();
        b = 0;

        for (FlowEdge e : g[Src])
            excess[Src] += e.c;

        count[0] = n;
        Enqueue(Src);
        active[Sink] = true;

        while (b >= 0) {
            if (!B[b].isEmpty()) {
                int v = B[b].pop();
                active[v] = false;
                Discharge(v);
            } else {
                b --;
            }
        }

        return excess[Sink];
    }

    static private void Enqueue(int v) {
        if (!active[v] && excess[v] > 0 && dist[v] < N) {
            active[v] = true;
            B[dist[v]].push(v);
            b = Math.max(b, dist[v]);
        }
    }

    static private void Discharge(int u) {
        for (FlowEdge e : g[u]) {
            if (excess[u] > 0)
                Push(u, e);
            else break;
        }

        if (excess[u] > 0) {
            if (count[dist[u]] == 1)
                Gap(dist[u]);
            else
                Relabel(u);
        }
    }

    static private void Push(int u, FlowEdge e) {
        int amt = Math.min(excess[u], e.c), v = e.to;
        if (dist[u] == dist[v] + 1 && amt > 0) {
            e.c -= amt;
            e.reverse.c += amt;
            excess[u] -= amt;
            excess[v] += amt;
            Enqueue(v);
        }
    }

    static private void Relabel(int u) {
        count[dist[u]] --;
        dist[u] = N;
        for (FlowEdge e : g[u])
            if (e.c > 0) {
                dist[u] = Math.min(dist[u], dist[e.to] + 1);
            }
        count[dist[u]] ++;
        Enqueue(u);
    }

    static private void Gap(int K) {
        for (int u = 0; u < N; u ++) if (dist[u] >= K) {
            count[dist[u]] --;
            dist[u] = Math.max(dist[u], N);
            count[dist[u]] ++;
            Enqueue(u);
        }
    }
}
