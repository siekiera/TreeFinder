package pl.edu.pw.elka.treefinder.logic.algorithm;

import pl.edu.pw.elka.treefinder.logic.algorithm.util.Heap;
import pl.edu.pw.elka.treefinder.model.AdjListGraph;
import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.util.*;

/**
 * Implementacja algorytmu Prima
 * <p/>
 * Data utworzenia: 12.12.14 18:27
 *
 * @author Michał Toporowski
 */
public class PrimAlgorithm implements MSTAlgorithm {
    /**
     * mapa wierzchołek->odl. od źródła; null: nieskończoność
     */
    private Map<Vertex, Double> vertexDistances = new HashMap<>();
    private Map<Vertex, Vertex> vertexConnections = new HashMap<>();
    private Set<Vertex> processed = new HashSet<>();
    private AdjListGraph inputAjGraph;
    private Graph inputGraph;
    private Heap<Vertex> heap;

    @Override
    public Graph calculate(Graph inputGraph) {
        // TODO:: implementacja
        this.inputGraph = inputGraph;
        Graph tree = new Graph();
        inputAjGraph = new AdjListGraph(inputGraph);
        int capacity = (int) (inputGraph.getDensity() * inputGraph.getVertices().size());
        heap = new Heap<>(capacity, new VertexComparator());
        // inicjacja V = {x}
        Vertex start = inputGraph.getVertices().get(0);
        tree.getVertices().add(start);
        vertexDistances.put(start, 0.0);
        addNeighbours(start);
        processed.add(start);
        while (!(tree.getVertices().size() == inputGraph.getVertices().size())) {
            //wśród nieprzetworzonych wierzchołków (spoza obecnego MDR) wybierz ten, dla którego koszt dojścia z obecnego MDR jest najmniejszy.
            Vertex next = heap.pop();
            //FIXME:: suboptimal
            //dodaj do obecnego MDR wierzchołek i krawędź realizującą najmniejszy koszt
            Edge nextEdge = inputGraph.getEdge(next, vertexConnections.get(next));
            tree.getVertices().add(next);
            tree.getEdges().add(nextEdge);
            processed.add(next);
            //zaktualizuj kolejkę priorytetową, uwzględniając nowe krawędzie wychodzące z dodanego wierzchołka
            addNeighbours(next);
        }

        return tree;
    }

    /**
     * Oblicza odległości dla sąsiadów danego wierzchołka oraz dodaje ich do kopca
     *
     * @param v
     */
    private void addNeighbours(Vertex v) {
        List<Vertex> neighbours = inputAjGraph.getNeighbours(v);
        double distFromStart = 0.0;//vertexDistances.get(v);
        for (Vertex n : neighbours) {
            double distance = distFromStart + inputGraph.getEdge(v, n).getWeight();
            if (!processed.contains(n)) {
                if (vertexDistances.get(n) == null || vertexDistances.get(n) >= distance) {
                    vertexDistances.put(n, distance);
                    vertexConnections.put(n, v);
                    heap.insert(n);
                }
            }
        }
    }

    /**
     * Klasa porównująca wierzchołki na potrzeby kopca.
     * Wierzchołek o mniejszej wartości w mapie vertexDistances będzie wyżej w kopcu
     */
    private class VertexComparator implements Comparator<Vertex> {
        @Override
        public int compare(Vertex v1, Vertex v2) {
            Double dist1 = vertexDistances.get(v1);
            Double dist2 = vertexDistances.get(v2);
            if (dist1 == null && dist2 == null) return 0;
            if (dist1 == null) return 1;
            if (dist2 == null) return -1;
            return Double.compare(dist1, dist2);
        }
    }
}
