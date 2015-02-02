package pl.edu.pw.elka.treefinder.logic.generator;

import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

/**
 * Created by monika on 2015-01-15.
 */
public class GraphGeneratorImpl implements GraphGenerator{
    private final Random generator = new Random();
    @Override
    public Graph generate(int vertexCount, float density) {
        Graph graph = new Graph();
        graph.addVertex(createVertex());

        for(int i=1; i<vertexCount; i++)
        {
            Edge e = new Edge(
                    graph.getVertices().get(generator.nextInt(graph.getVertices().size())),
                    graph.addVertex(createVertex()),
                    generator.nextDouble() * 100);
            graph.addEdge(e);
        }

        List <Edge> edgesWeCanAdd = new ArrayList<Edge>();
        for(int i=0; i<graph.getVertices().size()-1; i++){
            for(int j=i+1; j<graph.getVertices().size();j++){
                Edge e1 = new Edge(graph.getVertices().get(i),
                        graph.getVertices().get(j),
                        generator.nextDouble() * 100);
                if(!graph.getEdges().contains(e1)){
                    edgesWeCanAdd.add(e1);
                }
            }
        }

        //ilość krawędzi do wylosowania: = density* n(n-1)/2 - (n-1)
        int n = graph.getVertices().size();
        int x = (int) (density*n*(n-1)/2 - n+1);
        while(x>0)
        {
            int i = generator.nextInt(edgesWeCanAdd.size());
            graph.addEdge(edgesWeCanAdd.get(i));
            edgesWeCanAdd.remove(i);
            x--;

        }
        return graph;
    }

    private Vertex createVertex()
    {
        return new Vertex(generator.nextDouble() * 98 + 1, generator.nextDouble() * 98 + 1);
    }
}
