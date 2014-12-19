package pl.edu.pw.elka.treefinder.logic.algorithm;

import org.junit.Assert;
import pl.edu.pw.elka.treefinder.model.Graph;

/**
 * Test poprawności algorytmu
 * <p/>
 * Data utworzenia: 12.12.14 18:30
 *
 * @author Michał Toporowski
 */
public class CorrectnessTest {


    /**
     * Testuje poprawność wybranego algorytmu dla wybranego grafu
     *
     * @param algorithm          algorytm
     * @param inputGraph         graf
     * @param expectedResultTree oczekiwane drzewo
     */
    private void performTest(MSTAlgorithm algorithm, Graph inputGraph, Graph expectedResultTree) {
        Graph actualTree = algorithm.calculate(inputGraph);
        Assert.assertEquals(expectedResultTree, actualTree);
    }
}
