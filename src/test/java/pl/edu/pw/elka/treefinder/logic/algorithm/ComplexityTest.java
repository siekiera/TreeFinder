package pl.edu.pw.elka.treefinder.logic.algorithm;

import pl.edu.pw.elka.treefinder.model.Graph;

/**
 * Test złożoności algorytmu
 * <p/>
 * Data utworzenia: 12.12.14 18:29
 *
 * @author Michał Toporowski
 */
public class ComplexityTest {

    /**
     * Wykonuje podany algorytm dla danego grafu
     *
     * @param algorithm  algorytm
     * @param inputGraph graf
     * @return czas wykonania w milisekundach
     */
    private long performTest(MSTAlgorithm algorithm, Graph inputGraph) {
        long start = System.currentTimeMillis();
        algorithm.calculate(inputGraph);
        long end = System.currentTimeMillis();
        return end - start;
    }
}
