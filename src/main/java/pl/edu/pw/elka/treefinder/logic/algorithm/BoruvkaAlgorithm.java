package pl.edu.pw.elka.treefinder.logic.algorithm;

import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by monika on 2015-01-15.
 */
public class BoruvkaAlgorithm implements MSTAlgorithm {
    @Override
    public Graph calculate(Graph inputGraph) {
        Graph mst = new Graph(inputGraph.getVertices(), new ArrayList<>());
        List<List<Vertex>> vertexGroups = new ArrayList<>();
        for(Vertex currentVertex : inputGraph.getVertices()){
            List <Vertex> tmp = new ArrayList<>();
            tmp.add(currentVertex);
            vertexGroups.add(tmp);
        }

        while (vertexGroups.size() > 1) {
            for (List<Vertex> vertexGroup : vertexGroups) {
                double smallestWeight = Double.MAX_VALUE;
                Edge smallestEdge = null;

                for (Edge neighbour : getNeighbours(inputGraph, vertexGroup)) {
                    if (neighbour.getWeight() < smallestWeight) {
                        smallestWeight = neighbour.getWeight();
                        smallestEdge = neighbour;
                    }
                }
                if (!mst.getEdges().contains(smallestEdge)) {

                    mst.addEdge(smallestEdge);
                }
            }
            vertexGroups = mst.makeGroups();
        }
        return mst;
    }

    public List<Edge> getNeighbours(Graph inputGraph, List<Vertex> vertices){
        return inputGraph.getEdges().stream().filter(edge -> (vertices.contains(edge.getStart()) && !vertices.contains(edge.getEnd()))
                || (vertices.contains(edge.getEnd()) && !vertices.contains(edge.getStart()))).collect(Collectors.toList());
    }
}
