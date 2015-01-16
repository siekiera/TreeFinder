package pl.edu.pw.elka.treefinder.logic.algorithm;

import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by monika on 2015-01-15.
 */
public class BoruvkaAlgorithm implements MSTAlgorithm {
    @Override
    public Graph calculate(Graph inputGraph) {
        inputGraph.findNeighbours();
        Graph mst = new Graph(inputGraph.getVertices(), new ArrayList<>());
        List<Vertex> vertex = new ArrayList<Vertex>(inputGraph.getVertices());
        for(Vertex currentVertex : inputGraph.getVertices()){
            double smallestWeight = 999;
            Edge smallestEdge = new Edge();
            for(int i=0; i<currentVertex.getNeighbours().size(); i++){
                if(currentVertex.getNeighbours().get(i).getWeight() < smallestWeight){
                    smallestWeight = currentVertex.getNeighbours().get(i).getWeight();
                    smallestEdge = currentVertex.getNeighbours().get(i);
                }
                mst.addEdge(smallestEdge);
            }
        }
        return mst;
    }
}
