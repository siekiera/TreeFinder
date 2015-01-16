package pl.edu.pw.elka.treefinder.logic.algorithm;

import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Created by monika on 2015-01-15.
 */
public class KruskalAlgorithm implements MSTAlgorithm{
    @Override
    public Graph calculate(Graph inputGraph) {
        Graph mst = new Graph(inputGraph.getVertices(), new ArrayList<>());
        List<Edge> edgesSorted = new ArrayList<Edge>(inputGraph.getEdges());

        //sortowanie krawędzi rosnąco
        edgesSorted.sort(new Comparator<Edge>() {
            @Override
            public int compare(Edge e1, Edge e2) {
                return (int)Math.signum(e1.getWeight() - e2.getWeight());
            }
        });
        //n-1 razy dodajemy krawędź do grafu (n- ilość wierzchołków)
        while (mst.getEdges().size() < mst.getVertices().size() - 1) {
            Edge edge = edgesSorted.remove(0);
            mst.getEdges().add(edge);
            //sprawdzenie czy dodanie danej krawędzi nie wprowadza cyklu
            if(!mst.isAcyclic()) {
                mst.getEdges().remove(edge);
            }
        }

        return mst;
    }
}
