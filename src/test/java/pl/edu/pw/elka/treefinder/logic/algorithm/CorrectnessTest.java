package pl.edu.pw.elka.treefinder.logic.algorithm;

import org.junit.Assert;
import org.junit.Test;
import pl.edu.pw.elka.treefinder.io.GraphFileReader;
import pl.edu.pw.elka.treefinder.io.GraphFileReaderException;
import pl.edu.pw.elka.treefinder.logic.generator.GraphGeneratorImpl;
import pl.edu.pw.elka.treefinder.model.Graph;
import pl.edu.pw.elka.treefinder.test.TestBase;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
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

    @Test
    public void testKruskalAlgorithm() throws Exception {
        performAllTests(new KruskalAlgorithm());
    }

    @Test
    public void testBoruvkaAlgorithm() throws Exception {
        performAllTests(new BoruvkaAlgorithm());
    }

    @Test
    public void testPrimForRightVertexCount() throws Exception {
        int correct = 0, total = 0;
        for (int i = 10; i < 30; i += 5) {
            Graph g = new GraphGeneratorImpl().generate(10, 0.5f);
            Graph t = new PrimAlgorithm().calculate(g);
            // Sprawdzamy, czy na pewno wszystkie wierzchołki są
            if (new HashSet<>(t.getVertices()).size() == g.getVertices().size()) {
                correct++;
            }
            total++;
        }
        Assert.assertEquals(total, correct);
    }

    @Test
    public void testForSameWeights() throws Exception {
        int correct = 0, total = 0;
        for (int i = 10; i < 30; i += 5) {
            Graph g = new GraphGeneratorImpl().generate(10, 0.5f);
            Graph primTree = new PrimAlgorithm().calculate(g);
            Graph kruskalTree = new KruskalAlgorithm().calculate(g);
            Graph boruvkaTree = new BoruvkaAlgorithm().calculate(g);
            if ((int) primTree.totalWeight() != (int) kruskalTree.totalWeight()) {
                System.out.println(String.format("Wynik Prima (%s) != wynikowi Kruskala (%s)", primTree.totalWeight(), kruskalTree.totalWeight()));
            } else if ((int) primTree.totalWeight() != (int) boruvkaTree.totalWeight()) {
                System.out.println(String.format("Wynik Prima (%s) != wynikowi Boruvki (%s)", primTree.totalWeight(), boruvkaTree.totalWeight()));
            } else if ((int) kruskalTree.totalWeight() != (int) boruvkaTree.totalWeight()) {
                System.out.println(String.format("Wynik Kruskala (%s) != wynikowi Boruvki (%s)", kruskalTree.totalWeight(), boruvkaTree.totalWeight()));
            } else {
                System.out.println("Wszystkie drzewa tej samej wagi");
                correct++;
            }
            total++;
        }
        Assert.assertEquals(total, correct);
    }


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

    private Graph getGraphFromFile(String filename) throws GraphFileReaderException, URISyntaxException {
        return new GraphFileReader(new URI("file://" + filename)).read();
    }

}
