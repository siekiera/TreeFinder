package pl.edu.pw.elka.treefinder.logic.algorithm;

import pl.edu.pw.elka.treefinder.model.Graph;

/**
 * Algorytm znajdujący minimalne drzewo rozpinające w grafie
 * <p/>
 * Data utworzenia: 12.12.14 18:23
 *
 * @author Michał Toporowski
 */
public interface MSTAlgorithm {

    /**
     * Oblicza minimalne drzewo rozpinające
     *
     * @param inputGraph graf wejściowy
     * @return drzewo rozpinające jako graf
     */
    Graph calculate(Graph inputGraph);
}
