package graph;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Iterator;
/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Josh Rubin
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        graph = new ArrayList<LinkedList<Integer>>();
        preds = new ArrayList<LinkedList<Integer>>();
        graph.add(null);
        preds.add(null);
    }

    @Override
    public int vertexSize() {
        int count = 0;
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i) != null) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int maxVertex() {
        if (vertexSize() == 0) {
            return 0;
        }
        for (int i = graph.size() - 1; i > 0; i--) {
            if (graph.get(i) != null) {
                return i;
            }
        }
        return 0;
    }

    @Override
    public int edgeSize() {
        int count = 0;
        int special = 0;
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i) != null) {
                for (Integer vertex : graph.get(i)) {
                    if (!isDirected() && vertex == i) {
                        special += 1;
                    } else {
                        count++;
                    }
                }
            }
        }
        if (isDirected()) {
            return count;
        } else {
            return special + (count / 2);
        }
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (graph.size() > v && graph.get(v) != null) {
            return graph.get(v).size();
        } else {
            return 0;
        }
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        return graph.size() > u && graph.get(u) != null;
    }

    @Override
    public boolean contains(int u, int v) {
        if (graph.size() > u && graph.size() > v
                && graph.get(u) != null
                && graph.get(v) != null) {
            for (Integer vertex : graph.get(u)) {
                if (vertex == v) {
                    return true;
                }
            }
            return false;
        } else {
            return false;
        }
    }

    @Override
    public int add() {
        for (int i = 1; i < graph.size(); i++) {
            if (graph.get(i) == null) {
                graph.set(i, new LinkedList<Integer>());
                preds.set(i, new LinkedList<Integer>());
                return i;
            }
        }
        graph.add(new LinkedList<Integer>());
        preds.add(new LinkedList<Integer>());
        return graph.size() - 1;
    }

    @Override
    public int add(int u, int v) {
        if (graph.get(u).contains(v)) {
            return edgeId(u, v);
        } else if (isDirected()) {
            graph.get(u).add(v);
            preds.get(v).add(u);
            return edgeId(u, v);
        } else {
            graph.get(u).add(v);
            if (!graph.get(v).contains(u)) {
                graph.get(v).add(u);
            }
            preds.get(u).add(v);
            if (!preds.get(v).contains(u)) {
                preds.get(v).add(u);
            }
            return edgeId(u, v);
        }
    }

    @Override
    public void remove(int v) {
        graph.set(v, null);
        preds.set(v, null);
        for (int i = 0; i < preds.size(); i++) {
            if (preds.get(i) != null) {
                while (preds.get(i).contains((Integer) v)) {
                    preds.get(i).remove((Integer) v);
                }
            }
        }
        for (int i = 0; i < graph.size(); i++) {
            if (graph.get(i) != null) {
                while (graph.get(i).contains((Integer) v)) {
                    graph.get(i).remove((Integer) v);
                }
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        graph.get(u).remove((Integer) v);
        preds.get(v).remove((Integer) u);
        if (!isDirected()) {
            graph.get(v).remove((Integer) u);
            preds.get(u).remove((Integer) v);
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        ArrayList<Integer> list = new ArrayList<Integer>();
        for (int i = 1; i < graph.size(); i++) {
            if (graph.get(i) != null) {
                list.add(i);
            }
        }
        Iterator<Integer> it = list.iterator();
        Iteration<Integer> iteration = Iteration.iteration(it);
        return iteration;

    }

    @Override
    public int successor(int v, int k) {
        if (contains(v)) {
            if (graph.get(v).size() > 0) {
                return graph.get(v).get(k);
            }
        }
        return 0;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        if (graph.get(v) == null) {
            ArrayList<Integer> empty = new ArrayList<Integer>();
            Iterator<Integer> it = empty.iterator();
            Iteration<Integer> iteration = Iteration.iteration(it);
            return iteration;
        }
        Iterator<Integer> it = graph.get(v).iterator();
        Iteration<Integer> iteration = Iteration.iteration(it);
        return iteration;
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> edges = new ArrayList<int[]>();
        if (isDirected()) {
            for (int i = 0; i < graph.size(); i++) {
                if (graph.get(i) != null) {
                    for (Integer vertex : graph.get(i)) {
                        edges.add(new int[] {i, vertex});
                    }
                }
            }
        } else {
            for (int i = 0; i < graph.size(); i++) {
                if (graph.get(i) != null) {
                    for (Integer vertex : graph.get(i)) {
                        int[] edge = new int[] {i, vertex};
                        if (!edges.contains(edge)) {
                            edges.add(edge);
                        }
                    }
                }
            }
        }
        Iterator<int[]> it = edges.iterator();
        Iteration<int[]> iteration = Iteration.iteration(it);
        return iteration;
    }

    @Override
    protected void checkMyVertex(int v) {
    }

    @Override
    /** Taken from CS61b Slack Group. Adaption of
     * online algorithm that someone found. */
    protected int edgeId(int u, int v) {
        if (contains(u, v)) {
            if (!isDirected()) {
                if (u > v) {
                    int swap = v;
                    v = u;
                    u = swap;
                }
                return (v + (1 + v + u) * (v + u));
            } else if (isDirected()) {
                return (v + (1 + v + u) * (v + u));
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /** Access Preds from ArrayList.
     * @return ArrayList<LinkedList<Integer>> */
    protected ArrayList<LinkedList<Integer>> getPreds() {
        return preds;
    }

    /** Representation of predecessors using ArrayList of LinkedLists. */
    protected ArrayList<LinkedList<Integer>> preds;

    /** Representation of graph using ArrayList of LinkedLists. */
    protected ArrayList<LinkedList<Integer>> graph;

}
