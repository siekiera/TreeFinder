package pl.edu.pw.elka.treefinder.io;

import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.model.Vertex;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Klasa do wczytywania grafów z pliku
 * <p/>
 * Data utworzenia: 19.12.14 12:33
 *
 * @author Michał Toporowski
 */
public class GraphFileReader {

    private final Path path;

    public GraphFileReader(URI filename) {
        this.path = Paths.get(filename);
    }

    /**
     * Wczytuje graf z pliku
     *
     * @return graf
     */
    public Graph read() throws GraphFileReaderException {
        Graph graph = new Graph();
        try {
            List<String> lines = Files.readAllLines(path);
            if (lines.size() < 2) {
                throw new GraphFileReaderException("Pierwsze dwie linie muszą zawierać liczbę wierzchołków oraz krawędzi");
            }
            // pierwsza linia - liczba wierzchołków; druga - liczba krawędzi
            int vertexCount = Integer.parseInt(lines.get(0));
            int edgeCount = Integer.parseInt(lines.get(1));
            if (lines.size() != 2 + vertexCount + edgeCount) {
                throw new GraphFileReaderException("Liczba wierszy niezgodna z nagłówkiem");
            }
            // wczytywanie wierzchołków
            Vertex[] vertexes = new Vertex[vertexCount];
            for (int i = 0; i < vertexCount; i++) {
                String[] coords = lines.get(i + 2).split(" ");
                if (coords.length != 2) {
                    throw new GraphFileReaderException("Wiersze dot. wierzchołków muszą zawierać dokładnie 2 liczby");
                }
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                Vertex v = graph.addVertex(x, y);
                vertexes[i] = v;
            }
            // wczytywanie krawędzi
            for (int i = 0; i < edgeCount; i++) {
                // format: [v1] [v2] [długość]
                String[] edgeData = lines.get(i + 2 + vertexCount).split(" ");
                if (edgeData.length != 3) {
                    throw new GraphFileReaderException("Wiersze dot. krawędzi muszą zawierać dokładnie 3 liczby");
                }
                int v1 = Integer.parseInt(edgeData[0]);
                int v2 = Integer.parseInt(edgeData[1]);
                double weight = Double.parseDouble(edgeData[2]);
                if (v1 < 1 || v2 < 1 || v1 > vertexCount || v2 > vertexCount) {
                    throw new GraphFileReaderException("Nieprawidłowy nr wierzchołka");
                }
                graph.addEdgeUnchecked(vertexes[v1 - 1], vertexes[v2 - 1], weight);
            }
        } catch (NumberFormatException | IOException e) {
            throw new GraphFileReaderException(e);
        }
        return graph;
    }
}
