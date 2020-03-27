package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author
 */
public class GraphTesting {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    @Test
    public void testSuccessors() {
        UndirectedGraph g = new UndirectedGraph();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add();
        g.add(1, 2);
        g.add(1, 3);
        g.add(1, 4);
        g.add(4, 1);
        for (int v : g.successors(1)) {
            System.out.println(v);
        }
    }
    @Test
    public void testPred() {
        DirectedGraph b = new DirectedGraph();
        b.add();
        b.add();
        b.add();
        b.add();
        b.add(1, 2);
        b.add(3, 2);
        assertEquals(1, b.predecessor(2, 0));
        b.remove(1);
        assertEquals(3, b.predecessor(2, 0));
        System.out.println(b.successors(3));
        for (int v : b.successors(3)) {
            System.out.println(v);
        }
    }
    @Test
    public void testAdd() {
        DirectedGraph g = new DirectedGraph();
        assertEquals(0, g.vertexSize());
        g.add();
        g.add();
        assertEquals(2, g.vertexSize());
        assertTrue(g.contains(1));
        assertTrue(g.contains(2));
        g.remove(1);
        assertFalse(g.contains(1));
        assertEquals(2, g.maxVertex());
        g.add();
        assertTrue(g.contains(1));
        g.add();
        assertEquals(3, g.maxVertex());
        UndirectedGraph a = new UndirectedGraph();
        assertEquals(0, a.vertexSize());
        a.add();
        a.add();
        assertEquals(2, a.vertexSize());
        assertTrue(a.contains(1));
        assertTrue(a.contains(2));
        a.remove(1);
        assertFalse(a.contains(1));
        assertEquals(2, a.maxVertex());
        a.add();
        assertTrue(a.contains(1));
        a.add();
        assertEquals(3, a.maxVertex());
    }

    @Test
    public void testEdgeSize() {
        DirectedGraph graph = new DirectedGraph();
        assertEquals(0, graph.edgeSize());
        graph.add();
        assertEquals(0, graph.edgeSize());
        graph.add();
        graph.add(1, 2);
        assertEquals(1, graph.edgeSize());
        graph.add(2, 1);
        assertEquals(2, graph.edgeSize());
    }

    @Test
    public void testRemove() {
        DirectedGraph x = new DirectedGraph();
        x.add();
        x.add();
        x.add();
        x.add();
        x.add(1, 2);
        x.remove(1);
        assertEquals(0, x.edgeSize());
        x.add();
        x.add(1, 2);
        x.add(2, 1);
        assertEquals(2, x.edgeSize());
        x.remove(2);
        assertEquals(0, x.edgeSize());
    }

}
