package graph;

import java.util.*;

import org.junit.Test;

import static org.junit.Assert.*;

import graph.Graph.Edge;

/**
 * Created by shouhm on 16/5/22.
 */
public class MaximumCliqueTest {
    @Test
    public void testFindMaximumCliqueTest() {
        int N = 9;
        int[][] edges = {
                {0,1}, {0,3}, {0,4}, {0,5},
                {1,4}, {1,5},
                {2,4},
                {3,6},
                {4,5}, {4,8},
                {5,7}, {5,8},
                {6,7},
                {7,8},
                {8,8}
        };
        List<Edge> edgesList = GraphTestUtil.edgeArrayToList(edges);
        assertEquals(4, MaximumClique.findMaxmiumClique(9, edgesList));
    }
}