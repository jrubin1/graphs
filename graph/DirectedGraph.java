package graph;
import java.util.ArrayList;
import java.util.Iterator;
/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Josh Rubin
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        if ((getPreds().size() > v && getPreds().get(v) != null)) {
            return getPreds().get(v).size();
        } else {
            return 0;
        }
    }

    @Override
    public int predecessor(int v, int k) {
        if ((getPreds().size() > v && getPreds().get(v) != null)) {
            return getPreds().get(v).get(k);
        } else {
            return 0;
        }
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        if (preds.get(v) == null) {
            ArrayList<Integer> empty = new ArrayList<Integer>();
            Iterator<Integer> it = empty.iterator();
            Iteration<Integer> iteration = Iteration.iteration(it);
            return iteration;
        }
        Iteration<Integer> it = Iteration.iteration
                (getPreds().get(v).iterator());
        return it;
    }
}
