package pl.edu.pw.elka.treefinder.test;

import java.net.URI;
import pl.edu.pw.elka.treefinder.model.Edge;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Klasa bazowa dla testów
 * <p/>
 * Data utworzenia: 07.01.15 21:41
 *
 * @author Michał Toporowski
 */
public class TestBase {

    /**
     * Pobiera nazwę pliku zasobu o podanej nazwie)
     *
     * @param name
     * @return
     * @throws URISyntaxException
     */
    protected URI getResource(String name) throws URISyntaxException {
        return this.getClass().getClassLoader().getResource(name).toURI();
    }


    /**
     * Weryfikuje, czy zbiór krawędzi zawiera krawędź o takich samych wierzchołkach
     *
     * @param edgeSet
     * @param edge
     * @return
     */
    protected boolean containsEdge(List<Edge> edgeSet, Edge edge) {
        return edgeSet.stream().anyMatch(e -> e.equalsNoReferences(edge));
    }
}
