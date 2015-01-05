package pl.edu.pw.elka.treefinder.io;

import org.junit.Test;
import pl.edu.pw.elka.treefinder.model.Edge;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class GraphFileReaderTest {

    @Test
    public void testRead() throws Exception {
        GraphFileReader reader = new GraphFileReader(getResource("simple_graph.txt"));


        Vertex v1 = new Vertex(1.0, 2.0);
        Vertex v2 = new Vertex(2.4, 2.4);
        Vertex v3 = new Vertex(5.0, 3.0);
        Vertex v4 = new Vertex(4.0, -1.2);
        Vertex v5 = new Vertex(0.0, 0.0);
        Set<Vertex> vertices = new HashSet<>(Arrays.asList(v1, v2, v3, v4, v5));

        Graph output = reader.read();
        // porównanie wierzchołków
        assertEquals(vertices, output.getVertices());
        // porównanie krawędzi
        Set<Edge> outputEdges = output.getEdges();
        assertEquals(7, outputEdges.size());
        assertTrue(containsEdge(outputEdges, new Edge(v1, v2, 1)));
        assertTrue(containsEdge(outputEdges, new Edge(v1, v3, 0.5)));
        assertTrue(containsEdge(outputEdges, new Edge(v1, v4, 2.1)));
        assertTrue(containsEdge(outputEdges, new Edge(v1, v5, 10)));
        assertTrue(containsEdge(outputEdges, new Edge(v2, v4, 2)));
        assertTrue(containsEdge(outputEdges, new Edge(v3, v2, 4)));
        assertTrue(containsEdge(outputEdges, new Edge(v4, v5, 1)));

    }

    private boolean containsEdge(Set<Edge> edgeSet, Edge edge) {
        return edgeSet.stream().anyMatch(e -> e.equalsNoReferences(edge));
    }

    private String getResource(String name) throws URISyntaxException {
        return this.getClass().getClassLoader().getResource(name).getFile();
    }
}