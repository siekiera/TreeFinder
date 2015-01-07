package pl.edu.pw.elka.treefinder.logic.algorithm;

import org.junit.Assert;
import pl.edu.pw.elka.treefinder.io.GraphFileReader;
import pl.edu.pw.elka.treefinder.io.GraphFileReaderException;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.test.TestBase;

import java.net.URISyntaxException;

/**
 * Test poprawności algorytmu
 * <p/>
 * Data utworzenia: 12.12.14 18:30
 *
 * @author Michał Toporowski
 */
public class CorrectnessTest extends TestBase {


    private void performTest(MSTAlgorithm algorithm, String testName) throws Exception {
        Graph input = getGraphFromResource(testName + ".in");
        Graph expectedOutput = getGraphFromResource(testName + ".out");
        performTest(algorithm, input, expectedOutput);
    }


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

    private Graph getGraphFromResource(String resourceName) throws URISyntaxException, GraphFileReaderException {
        return new GraphFileReader(getResource(resourceName)).read();
    }
}
