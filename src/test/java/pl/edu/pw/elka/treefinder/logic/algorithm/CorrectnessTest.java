package pl.edu.pw.elka.treefinder.logic.algorithm;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.pw.elka.treefinder.io.GraphFileReader;
import pl.edu.pw.elka.treefinder.io.GraphFileReaderException;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.test.TestBase;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Test poprawności algorytmu
 * <p/>
 * Data utworzenia: 12.12.14 18:30
 *
 * @author Michał Toporowski
 */
public class CorrectnessTest extends TestBase {

    @Test
    public void testPrimAlgorithm() throws Exception {
        performAllTests(new PrimAlgorithm());
    }

    // TODO :: dodać testy dla pozostałych algorytmów


    /**
     * Uruchamia testy poprawności wybranego algorytmu dla wszystkich danych
     *
     * @param algorithm
     * @throws Exception
     */
    private void performAllTests(MSTAlgorithm algorithm) throws Exception {
        System.out.println("Testuję algorytm: " + algorithm.getClass().getSimpleName());
        // Założenie: pliki w katalogu resources/correctness_tests
        // Nazwy: wejściowy <nazwa>.grf, wyjściowy: <nazwa>.grf.out
        List<String> filenames = Files
                .list(Paths.get(getResource("correctness_tests")))
                .map(Path::toString)
                .filter(path -> path.endsWith(".grf"))
                .collect(Collectors.toList());
        int correct = 0, total = 0;
        for (String filename : filenames) {
            if (performTest(algorithm, filename)) {
                correct++;
            }
            total++;
        }
        System.out.println(String.format("Poprawnie zbudowano drzewo dla %s z %s grafów (%s %%)",
                correct, total, 100.0 * correct / total));
        Assert.assertEquals(total, correct);
    }


    /**
     * Testuje poprawność wybranego algorytmu dla wybranego grafu
     *
     * @param algorithm  algorytm
     * @param inFileName nazwa pliku wejściowego
     * @return wynik testu
     * @throws Exception
     */
    private boolean performTest(MSTAlgorithm algorithm, String inFileName) throws Exception {
        Graph input = getGraphFromFile(inFileName);
        Graph expectedOutput = getGraphFromFile(inFileName + ".out");
        return performTest(algorithm, input, expectedOutput);
    }


    /**
     * Testuje poprawność wybranego algorytmu dla wybranego grafu
     *
     * @param algorithm          algorytm
     * @param inputGraph         graf
     * @param expectedResultTree oczekiwane drzewo
     * @return wynik testu
     */
    private boolean performTest(MSTAlgorithm algorithm, Graph inputGraph, Graph expectedResultTree) {
        Graph actualTree = algorithm.calculate(inputGraph);
        return expectedResultTree.sameAs(actualTree);

    }

    private Graph getGraphFromFile(String filename) throws GraphFileReaderException {
        return new GraphFileReader(filename).read();
    }

}
