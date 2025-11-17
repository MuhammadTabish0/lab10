package graph;

import java.util.*;

/**
 * Weighted directed graph implemented with Edge list.
 * 
 * Abstraction function (AF):
 *   - vertices: all strings in `vertices` are vertices of the graph
 *   - edges: all Edge objects in `edges` are directed edges with weights
 * 
 * Representation invariant (RI):
 *   - vertices != null
 *   - edges != null
 *   - For every edge e in edges: e.source ∈ vertices && e.target ∈ vertices && e.weight > 0
 *   - No duplicate edges with same source & target
 * 
 * Safety from rep exposure:
 *   - All fields private and final
 *   - vertices() returns unmodifiable set
 *   - sources() and targets() return unmodifiable maps
 */
public class ConcreteEdgesGraph implements Graph<String> {
    
    private final Set<String> vertices = new HashSet<>();
    private final List<Edge> edges = new ArrayList<>();

    private void checkRep() {
        assert vertices != null;
        assert edges != null;
        for (Edge e : edges) {
            assert vertices.contains(e.getSource());
            assert vertices.contains(e.getTarget());
            assert e.getWeight() > 0;
        }
    }

    @Override
    public boolean add(String vertex) {
        Objects.requireNonNull(vertex);
        boolean added = vertices.add(vertex);
        checkRep();
        return added;
    }

    @Override
    public int set(String source, String target, int weight) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);

        if (!vertices.contains(source)) vertices.add(source);
        if (!vertices.contains(target)) vertices.add(target);

        for (Edge e : edges) {
            if (e.getSource().equals(source) && e.getTarget().equals(target)) {
                int oldWeight = e.getWeight();
                edges.remove(e);
                if (weight > 0) edges.add(new Edge(source, target, weight));
                checkRep();
                return oldWeight;
            }
        }

        if (weight > 0) edges.add(new Edge(source, target, weight));
        checkRep();
        return 0;
    }

    @Override
    public boolean remove(String vertex) {
        Objects.requireNonNull(vertex);
        if (!vertices.contains(vertex)) return false;

        vertices.remove(vertex);
        edges.removeIf(e -> e.getSource().equals(vertex) || e.getTarget().equals(vertex));
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        return Collections.unmodifiableSet(vertices);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> map = new HashMap<>();
        for (Edge e : edges) {
            if (e.getTarget().equals(target)) map.put(e.getSource(), e.getWeight());
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Map<String, Integer> map = new HashMap<>();
        for (Edge e : edges) {
            if (e.getSource().equals(source)) map.put(e.getTarget(), e.getWeight());
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Vertices: " + vertices + "\nEdges:\n");
        for (Edge e : edges) sb.append("  ").append(e).append("\n");
        return sb.toString();
    }
}

/**
 * Immutable Edge class for ConcreteEdgesGraph
 */
class Edge {
    private final String source;
    private final String target;
    private final int weight;

    public Edge(String source, String target, int weight) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);
        if (weight <= 0) throw new IllegalArgumentException("weight must be positive");

        this.source = source;
        this.target = target;
        this.weight = weight;
        checkRep();
    }

    private void checkRep() {
        assert source != null;
        assert target != null;
        assert weight > 0;
    }

    public String getSource() { return source; }
    public String getTarget() { return target; }
    public int getWeight() { return weight; }

    @Override
    public String toString() { return source + " -> " + target + " (" + weight + ")"; }
}
