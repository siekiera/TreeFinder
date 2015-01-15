package pl.edu.pw.elka.treefinder.logic.generator;

import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.util.ArrayList;
import java.util.Random;

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
            graph.addEdge(
                    graph.getVertices().get(generator.nextInt(graph.getVertices().size())),
                    graph.addVertex(createVertex()),
                    generator.nextDouble() * 100);
        }
        //ilość krawędzi do wylosowania: = density* n(n-1)/2 - (n-1)
        int n = graph.getVertices().size();
        int x = (int) (density*n*(n-1)/2 - n+1);
        while(x>0)
        {
            Edge edge = new Edge(
                    graph.getVertices().get(generator.nextInt(graph.getVertices().size())),
                    graph.getVertices().get(generator.nextInt(graph.getVertices().size())),
                    generator.nextDouble() * 100);
            boolean exists = false;
            for(Edge currentEdge : graph.getEdges()) {
                if(currentEdge.equalsNoReferences(edge))
                {
                    exists = true;
                    break;
                }
            }
            if(!exists) {
                graph.addEdge(edge);
                x--;
            }
        }
        return graph;
    }

    private Vertex createVertex()
    {
        return new Vertex(generator.nextDouble() * 100, generator.nextDouble() * 100);
    }
}
