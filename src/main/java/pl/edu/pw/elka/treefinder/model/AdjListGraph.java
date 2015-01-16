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

    public AdjListGraph(Graph g) {
        for (Edge e : g.getEdges()) {
            Vertex v1 = e.getStart();
            Vertex v2 = e.getEnd();
            getNeighbours(v1).add(v2);
            getNeighbours(v2).add(v1);
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

    public Set<Vertex> getVertices() {
        return adjList.keySet();
    }
}
