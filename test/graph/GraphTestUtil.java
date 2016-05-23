package graph;

import java.util.*;

import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/17.
 */
public class GraphTestUtil {
    static public ArrayList<Edge> edgeArrayToList(int[][] edges) {
        ArrayList<Edge> ret = new ArrayList<>();
        for (int i = 0; i < edges.length; i ++) {
            ret.add(new Edge(edges[i][0], edges[i][1]));
        }
        return ret;
    }
}
