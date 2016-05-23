package graph;

import java.util.*;
import java.util.stream.*;

import graph.Graph.Edge;

public class TopoSortSolver {

    static public List<Integer> topoSort(int N, List<Edge> edges) {
        //Make the graph
        ArrayList<Integer>[] g = Graph.getDirectedAdjList(N, edges);

        //Initialize the helper structures
        ArrayList<Integer> ret = new ArrayList<>();
        Queue<Integer> queue = new LinkedList<>();
        int[] inDeg = new int[N];

        for (int x = 0; x < N; x ++)
            for (int y : g[x])
                inDeg[y] ++;

        for (int x = 0; x < N; x ++)
            if (inDeg[x] == 0)
                queue.add(x);

        while (!queue.isEmpty()) {
            int u = queue.poll();
            ret.add(u);
            for (int v : g[u]) {
                inDeg[v]--;
                if (inDeg[v] == 0) queue.add(v);
            }
        }
        return ret;
    }

}
