package pl.edu.pw.elka.treefinder.logic.generator;

import pl.edu.pw.elka.treefinder.model.Graph;

/**
 * GraphGenerator
 * <p/>
 * Data utworzenia: 19.12.14 12:28
 *
 * @author Michał Toporowski
 */
public interface GraphGenerator {

    /**
     * Generuje graf
     *
     * @param vertexCount liczba wierzchołków
     * @param density     gęstość grafu
     * @return
     */
    Graph generate(int vertexCount, float density);
}
