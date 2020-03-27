package graph;

import java.util.ArrayList;
/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Josh Rubin
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
        weights = new ArrayList<Double>();
        prevs = new ArrayList<Integer>();
        for (int i = 0; i < G.vertexSize() + 5; i++) {
            weights.add(null);
            prevs.add(null);
        }
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    @Override
    protected abstract double getWeight(int u, int v);

    @Override
    public double getWeight(int v) {
        if (_G.contains(v) && weights.size() > v
                && weights.get(v) != null) {
            return weights.get(v);
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    protected void setWeight(int v, double w) {
        weights.set(v, w);
    }

    @Override
    public int getPredecessor(int v) {
        if (prevs.size() > v && prevs.get(v) != null) {
            return prevs.get(v);
        }
        return 0;
    }

    @Override
    protected void setPredecessor(int v, int u) {
        prevs.set(v, u);
    }

    /** Weights in ArrayList form. */
    private ArrayList<Double> weights;

    /** Predecessors in ArrayList form. */
    private ArrayList<Integer> prevs;

}
