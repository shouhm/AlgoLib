package graph;

import java.util.*;
import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/14.
 */
public class BiConnectionComponent {

    static int N, M;
    static int[] low, dfn;
    static ArrayList<Integer>[] g;
    static Stack<Integer> st;
    static int counter;
    static List<List<Integer>> ret;

    static public List<List<Integer>> BCC(int n, List<Edge> edges) {
        N = n; M = edges.size();
        g = Graph.getNonDirectedAdjList(N, edges);
        ret = new ArrayList<>();

        dfn = new int[N]; low = new int[N];
        Arrays.fill(dfn, -1);
        st = new Stack<>();
        counter = 0;

        for (int x = 0; x < N; x ++) {
            if (dfn[x] == -1) DFS(x);
        }

        return ret;
    }

    static private void DFS(int x) {
        st.push(x);
        dfn[x] = low[x] = counter; counter ++;
        for (int y : g[x]) {
            if (dfn[y] == -1) {
                DFS(y);
                if (low[y] >= dfn[x]) {
                    List<Integer> bcc = new ArrayList<>();
                    int c;
                    do {
                        c = st.pop();
                        bcc.add(c);
                    } while (c != y);
                    bcc.add(x);
                    ret.add(bcc);
                }
                low[x] = Math.min(low[x], low[y]);
            } else {
                low[x] = Math.min(low[x], dfn[y]);
            }
        };
    }
}
