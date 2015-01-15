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

    public Vertex addVertex(Vertex vertex) {
        vertices.add(vertex);
        return vertex;
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

    public void addEdge(Edge edge) {
        edges.add(edge);
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

    public double getDensity() {
        int vCount = vertices.size();
        if (vCount == 0) return Double.NaN;
        return edges.size() / (0.5 * vCount * (vCount - 1));
    }

    /**
     * Pobiera krawędź pomiędzy podanymi wierzchołkami
     *
     * @param v1
     * @param v2
     * @return krawędź lub null, jeśli nie istnieje
     */
    public Edge getEdge(Vertex v1, Vertex v2) {
        for (Edge e: edges) {
            if (e.getStart() == v1 && e.getEnd() == v2 || e.getEnd() == v1 && e.getStart() == v2) {
                return e;
            }
        }
        return null;
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

    /**
     * Weryfikuje, czy graf other jest taki sam jak ten graf
     * tj. zawiera wierzchołki o tych samych współrzędnych
     * i krawędzie pomiędzy tymi samymi wierzchołkami
     *
     * @param other
     * @return
     */
    public boolean sameAs(final Graph other) {
        // te same wierzchołki (kolejność nieistotna)
        if (!new HashSet<>(this.vertices).equals(new HashSet<>(other.vertices))) {
            return false;
        }
        // ta sama liczba krawędzi
        if (!(this.edges.size() == other.edges.size())) {
            return false;
        }
        // każda krawędź z other w this
        for (Edge e : this.edges) {
            if (!containsEdge(other.edges, e)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Weryfikuje, czy zbiór krawędzi zawiera krawędź o takich samych wierzchołkach
     *
     * @param edgeSet
     * @param edge
     * @return
     */
    private boolean containsEdge(List<Edge> edgeSet, Edge edge) {
        return edgeSet.stream().anyMatch(e -> e.equalsNoReferences(edge));
    }

}

