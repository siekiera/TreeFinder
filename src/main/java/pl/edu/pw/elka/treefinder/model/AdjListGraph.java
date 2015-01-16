package pl.edu.pw.elka.treefinder.model;

import java.util.*;

/**
 * Graf zbudowany na liście sąsiedztwa
 * <p/>
 * Data utworzenia: 15.01.15 16:26
 *
 * @author Michał Toporowski
 */
public class AdjListGraph {
    private Map<Vertex, List<Vertex>> adjList = new HashMap<>();
    private Map<Edge, Double> weights = new HashMap<>();

    public AdjListGraph(Graph g) {
        for (Edge e : g.getEdges()) {
            Vertex v1 = e.getStart();
            Vertex v2 = e.getEnd();
            getNeighbours(v1).add(v2);
            getNeighbours(v2).add(v1);
            weights.put(e, e.getWeight());
        }
    }

    /**
     * Pobiera wierzchołki sąsiadujące z danym
     *
     * @param v
     * @return
     */
    public List<Vertex> getNeighbours(Vertex v) {
        List<Vertex> neighbours = adjList.get(v);
        if (neighbours == null) {
            neighbours = new ArrayList<>();
            adjList.put(v, neighbours);
        }
        return neighbours;
    }

    /**
     * Pobiera wagę krawędzi pomiędzy danymi wierzchołkami
     *
     * @param v1
     * @param v2
     * @return
     */
    public double getEdgeWeight(Vertex v1, Vertex v2) {
       return weights.get(new Edge(v1, v2, 0));
    }

    public Set<Vertex> getVertices() {
        return adjList.keySet();
    }
}
