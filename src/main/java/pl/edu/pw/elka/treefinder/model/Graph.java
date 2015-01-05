package pl.edu.pw.elka.treefinder.model;

import com.sun.javaws.exceptions.InvalidArgumentException;

import java.util.HashSet;
import java.util.Set;

/**
 * Klasa reprezentująca graf
 * <p/>
 * Data utworzenia: 12.12.14 18:11
 *
 * @author Michał Toporowski
 */
public class Graph {
    private final Set<Vertex> vertices = new HashSet<>();
    private final Set<Edge> edges = new HashSet<>();

    public Vertex addVertex(double x, double y) {
        Vertex v = new Vertex(x, y);
        vertices.add(v);
        return v;
    }
    
    public void addEdge(Vertex v1, Vertex v2, double weight) {
        if (vertices.contains(v1) && vertices.contains(v2)) {
            addEdgeUnchecked(v1, v2, weight);
        }
    }
    
    public void addEdgeUnchecked(Vertex v1, Vertex v2, double weight) {
        edges.add(new Edge(v1, v2, weight));
    }

    public Set<Vertex> getVertices() {
        return vertices;
    }

    public Set<Edge> getEdges() {
        return edges;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Graph graph = (Graph) o;

        if (!edges.equals(graph.edges)) return false;
        if (!vertices.equals(graph.vertices)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = vertices.hashCode();
        result = 31 * result + edges.hashCode();
        return result;
    }
}
