package pl.edu.pw.elka.treefinder.model;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * Klasa reprezentująca graf
 * <p/>
 * Data utworzenia: 12.12.14 18:11
 *
 * @author Michał Toporowski
 */
public class Graph {
    private List<Vertex> vertices = new ArrayList<>();
    private List<Edge> edges = new ArrayList<>();

    public Graph() {}

    public Graph(List<Vertex> vertices, List<Edge> edges) {
        this.vertices =  new ArrayList<>(vertices);
        this.edges =  new ArrayList<>(edges);
    }

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

    public List<Vertex> getVertices() {
        return vertices;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public boolean isAcyclic() {
        clearVisited();
        for (Vertex vertex : vertices) {
            if (!vertex.isVisited()) {
                if(!vertex.isAcyclic(edges, null)) {
                    return false;
                }
            }
        }

        return true;
    }

    private void clearVisited() {
        for(Vertex vertex : vertices) {
            vertex.setVisited(false);
        }
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

