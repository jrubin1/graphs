package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.Comparator;
import java.util.ArrayList;
import java.util.TreeSet;

/** The shortest paths through an edge-weighted graph.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to represent the weighting
 *  and the search results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Josh Rubin
 */
public abstract class ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        _source = source;
        _dest = dest;
        Comparator<Integer> comp = new ComparatorClassForQueue();
        queue = new TreeSet<Integer>(comp);
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        queue.clear();
        for (int v: _G.vertices()) {
            if (v != getSource()) {
                setWeight(v, Double.POSITIVE_INFINITY);
                queue.add(v);
                setPredecessor(v, 0);
            } else {
                setWeight(v, 0.0);
                setPredecessor(v, 0);
                queue.add(v);
            }
        }
        while (!queue.isEmpty()) {
            int currVer = queue.pollFirst();
            marked.add(currVer);
            if (_dest == currVer) {
                return;
            }
            for (int next : _G.successors(currVer)) {
                if ((getWeight(currVer) + getWeight(currVer, next))
                        < getWeight(next)) {
                    setWeight(next, getWeight(currVer)
                            + getWeight(currVer, next));
                    setPredecessor(next, currVer);
                    queue.remove(next);
                    queue.add(next);
                }


            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        return _dest;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        List<Integer> list = new ArrayList<Integer>();
        int stop = v;
        list.add(stop);
        while (stop != _source) {
            int curr = getPredecessor(stop);
            list.add(curr);

            stop = curr;
        }
        ArrayList<Integer> returnList = new ArrayList<Integer>();
        for (int i = list.size() - 1; i >= 0; i--) {
            returnList.add(list.get(i));
        }
        return returnList;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** Comparator class for Treeset Queue. */
    private class ComparatorClassForQueue implements Comparator<Integer> {
        /** Compare integer A and B based on weight + heuristic.
         * Return an INT. */
        public int compare(Integer a, Integer b) {
            if ((getWeight(a) + estimatedDistance(a))
                    > (getWeight(b) + estimatedDistance(b))) {
                return 1;
            } else if ((getWeight(a) + estimatedDistance(a))
                    < (getWeight(b) + estimatedDistance(b))) {
                return -1;
            } else {
                return a - b;
            }
        }
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;

    /** Queue represented as TreeSet. */
    private TreeSet<Integer> queue;

    /** Marked Arraylist Data Structure. */
    private ArrayList<Integer> marked = new ArrayList<Integer>();





}
