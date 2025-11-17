package graph;

import java.util.*;

/**
 * Weighted directed graph implemented with Vertex list.
 * 
 * AF: vertices list contains all vertices, each Vertex tracks its outgoing edges
 * RI:
 *   - vertices != null
 *   - Each vertex != null
 *   - Each edge weight > 0
 * Safety:
 *   - returns defensive copies
 */
public class ConcreteVerticesGraph implements Graph<String> {

    private final List<Vertex> vertices = new ArrayList<>();

    private void checkRep() {
        assert vertices != null;
        for (Vertex v : vertices) assert v != null;
    }

    private Vertex getVertex(String label) {
        for (Vertex v : vertices) if (v.getName().equals(label)) return v;
        return null;
    }

    @Override
    public boolean add(String vertex) {
        Objects.requireNonNull(vertex);
        if (getVertex(vertex) != null) return false;
        vertices.add(new Vertex(vertex));
        checkRep();
        return true;
    }

    @Override
    public int set(String source, String target, int weight) {
        Objects.requireNonNull(source);
        Objects.requireNonNull(target);

        Vertex src = getVertex(source);
        if (src == null) {
            src = new Vertex(source);
            vertices.add(src);
        }
        Vertex tgt = getVertex(target);
        if (tgt == null) {
            tgt = new Vertex(target);
            vertices.add(tgt);
        }

        int oldWeight = src.setTarget(target, weight);
        checkRep();
        return oldWeight;
    }

    @Override
    public boolean remove(String vertex) {
        Objects.requireNonNull(vertex);
        Vertex v = getVertex(vertex);
        if (v == null) return false;

        vertices.remove(v);
        for (Vertex u : vertices) u.removeTarget(vertex);
        checkRep();
        return true;
    }

    @Override
    public Set<String> vertices() {
        Set<String> set = new HashSet<>();
        for (Vertex v : vertices) set.add(v.getName());
        return Collections.unmodifiableSet(set);
    }

    @Override
    public Map<String, Integer> sources(String target) {
        Map<String, Integer> map = new HashMap<>();
        for (Vertex v : vertices) {
            Integer w = v.getWeight(target);
            if (w != null) map.put(v.getName(), w);
        }
        return Collections.unmodifiableMap(map);
    }

    @Override
    public Map<String, Integer> targets(String source) {
        Vertex v = getVertex(source);
        if (v == null) return Collections.emptyMap();
        return Collections.unmodifiableMap(new HashMap<>(v.getTargets()));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Vertex v : vertices) sb.append(v).append("\n");
        return sb.toString();
    }
}

/**
 * Vertex for ConcreteVerticesGraph
 */
class Vertex {
    private final String name;
    private final Map<String, Integer> targets = new HashMap<>();

    public Vertex(String name) {
        Objects.requireNonNull(name);
        this.name = name;
        checkRep();
    }

    private void checkRep() {
        assert name != null;
        for (int w : targets.values()) assert w > 0;
    }

    public String getName() { return name; }

    public int setTarget(String target, int weight) {
        if (weight == 0) return targets.remove(target) == null ? 0 : targets.get(target);
        return targets.put(target, weight) == null ? 0 : targets.get(target);
    }

    public void removeTarget(String target) { targets.remove(target); }

    public Integer getWeight(String target) { return targets.get(target); }

    public Map<String, Integer> getTargets() { return new HashMap<>(targets); }

    @Override
    public String toString() {
        return name + " -> " + targets;
    }
}
