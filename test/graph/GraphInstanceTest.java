/* Copyright (c) 2015-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package graph;

import static org.junit.Assert.*;

import java.util.Collections;

import org.junit.Test;

/**
 * Tests for instance methods of Graph.
 * 
 * <p>PS2 instructions: you MUST NOT add constructors, fields, or non-@Test
 * methods to this class, or change the spec of {@link #emptyInstance()}.
 * Your tests MUST only obtain Graph instances by calling emptyInstance().
 * Your tests MUST NOT refer to specific concrete implementations.
 */
public abstract class GraphInstanceTest {
    
    // Testing strategy
    //   TODO
    // Testing strategy for Graph instance methods
//
// Partition the inputs as follows:
//
// add(vertex):
//   - add new vertex not in graph
//   - add duplicate vertex already in graph
//
// set(source, target, weight):
//   Weight partitions: weight > 0 (add/update), weight = 0 (delete)
//   - edge added between new vertices
//   - edge updated with new weight
//   - edge removed when weight = 0
//   - adding edge implicitly adds missing vertices
//
// remove(vertex):
//   - remove existing vertex
//   - remove non-existing vertex
//
// vertices():
//   - empty graph
//   - graph with multiple vertices
//
// sources(target):
//   - target with no incoming edges
//   - target with multiple incoming edges
//
// targets(source):
//   - source with no outgoing edges
//   - source with multiple outgoing edges
//
// These partitions ensure full coverage of all behaviors of Graph ADT.

    
    /**
     * Overridden by implementation-specific test classes.
     * 
     * @return a new empty graph of the particular implementation being tested
     */
    public abstract Graph<String> emptyInstance();
    
    @Test(expected=AssertionError.class)
    public void testAssertionsEnabled() {
        assert false; // make sure assertions are enabled with VM argument: -ea
    }
    
    @Test
    public void testInitialVerticesEmpty() {
        // TODO you may use, change, or remove this test
        assertEquals("expected new graph to have no vertices",
                Collections.emptySet(), emptyInstance().vertices());
    }
    @Test
public void testAddVertex() {
    Graph<String> g = emptyInstance();
    assertTrue("adding new vertex should return true", g.add("A"));
    assertTrue("graph should contain vertex A", g.vertices().contains("A"));
}

@Test
public void testAddDuplicateVertex() {
    Graph<String> g = emptyInstance();
    g.add("A");
    assertFalse("adding existing vertex should return false", g.add("A"));
}

@Test
public void testRemoveExistingVertex() {
    Graph<String> g = emptyInstance();
    g.add("A");
    assertTrue("removing existing vertex should return true", g.remove("A"));
    assertFalse("vertex should no longer exist", g.vertices().contains("A"));
}

@Test
public void testRemoveNonexistentVertex() {
    Graph<String> g = emptyInstance();
    assertFalse("removing nonexistent vertex should return false", g.remove("X"));
}

@Test
public void testSetAddEdgeWithNewVertices() {
    Graph<String> g = emptyInstance();
    int previous = g.set("A", "B", 5);
    assertEquals("no previous edge", 0, previous);

    assertTrue(g.vertices().contains("A"));
    assertTrue(g.vertices().contains("B"));
    assertEquals("edge weight should be 5", 
        Integer.valueOf(5), g.targets("A").get("B"));
}

@Test
public void testSetUpdateEdge() {
    Graph<String> g = emptyInstance();
    g.set("A", "B", 3);
    int previous = g.set("A", "B", 7);
    assertEquals("previous weight should be 3", 3, previous);
    assertEquals(Integer.valueOf(7), g.targets("A").get("B"));
}

@Test
public void testSetRemoveEdge() {
    Graph<String> g = emptyInstance();
    g.set("A", "B", 4);
    int previous = g.set("A", "B", 0);  // remove edge
    assertEquals("previous weight should be 4", 4, previous);
    assertFalse("edge should no longer exist", g.targets("A").containsKey("B"));
}

@Test
public void testVerticesMultiple() {
    Graph<String> g = emptyInstance();
    g.add("A");
    g.add("B");
    g.add("C");

    assertEquals(3, g.vertices().size());
    assertTrue(g.vertices().contains("A"));
    assertTrue(g.vertices().contains("B"));
    assertTrue(g.vertices().contains("C"));
}

@Test
public void testSourcesNone() {
    Graph<String> g = emptyInstance();
    g.add("A");
    assertTrue("A has no sources", g.sources("A").isEmpty());
}

@Test
public void testSourcesMultiple() {
    Graph<String> g = emptyInstance();
    g.set("A", "C", 1);
    g.set("B", "C", 2);

    assertEquals("C should have 2 sources", 2, g.sources("C").size());
    assertEquals(Integer.valueOf(1), g.sources("C").get("A"));
    assertEquals(Integer.valueOf(2), g.sources("C").get("B"));
}

@Test
public void testTargetsNone() {
    Graph<String> g = emptyInstance();
    g.add("A");
    assertTrue("A has no targets", g.targets("A").isEmpty());
}

@Test
public void testTargetsMultiple() {
    Graph<String> g = emptyInstance();
    g.set("A", "B", 3);
    g.set("A", "C", 4);

    assertEquals(2, g.targets("A").size());
    assertEquals(Integer.valueOf(3), g.targets("A").get("B"));
    assertEquals(Integer.valueOf(4), g.targets("A").get("C"));
}

    // TODO other tests for instance methods of Graph
    
}
