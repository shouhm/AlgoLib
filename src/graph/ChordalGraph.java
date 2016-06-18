package graph;

import java.util.*;

/**
 * Created by shouhm on 16/5/30.
 */
public class ChordalGraph {

    static class Pair implements Comparable<Pair> {
        public int x, y;
        public Pair(int _x, int _y) { x = _x; y = _y; }
        @Override
        public int compareTo(Pair other) {
            if (x != other.x) return x - other.x; else
                return y - other.y;
        }
    }

    static public int[] perfectEliminationSequence(int n, ArrayList<Integer>[] g) {
        int[] rank = new int[n]; Arrays.fill(rank, -1);
        int[] label = new int[n]; Arrays.fill(label, 0);
        PriorityQueue<Pair> heap = new PriorityQueue<>();
        for (int i = 0; i < n; i ++) heap.add(new Pair(0, i));

        for (int i = n - 1; i >= 0; i --) {
            while (true) {
                int u = heap.peek().y;
                if (rank[u] == -1) {
                    rank[u] = i;
                    for (int v : g[u]) {
                        if (rank[v] == -1) {
                            label[v] ++;
                            heap.add(new Pair(label[v], v));
                        }
                    }
                    break;
                }
            }
        }
        int[] ret = new int[n];
        for (int i = 0; i < n; i ++)
            ret[rank[i]] = i;
        return ret;
    }

    static public boolean isChordalGraph(int n, ArrayList<Integer>[] g) {
        int[] ord = perfectEliminationSequence(n, g);
        int[] rank = new int[n];
        for (int i = 0; i < n; i ++) rank[ord[i]] = i;
        boolean[] mark = new boolean[n]; Arrays.fill(mark, false);

        for (int i = 0; i < n; i ++) {
            ArrayList<Pair> neighbor = new ArrayList<>();
            int x = ord[i];
            for (int y : g[x])
                if (!mark[y]) neighbor.add(new Pair(rank[y], y));
            Collections.sort(neighbor);
            if (neighbor.size() > 0) {
                int u = neighbor.get(0).y;
                Set<Integer> adjSet = new HashSet<>();
                for (int v : g[u])
                    adjSet.add(v);
                for (int j = 1; j < neighbor.size(); j ++)
                    if (!adjSet.contains(neighbor.get(j).y))
                        return false;
            }
            mark[x] = true;
        }
        return true;
    }
}
