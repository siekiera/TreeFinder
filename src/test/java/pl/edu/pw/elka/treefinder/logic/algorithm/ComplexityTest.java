package pl.edu.pw.elka.treefinder.logic.algorithm;

import org.junit.Test;
import pl.edu.pw.elka.treefinder.logic.generator.GraphGenerator;
import pl.edu.pw.elka.treefinder.logic.generator.GraphGeneratorImpl;
import pl.edu.pw.elka.treefinder.model.Graph;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Test złożoności algorytmu
 * <p/>
 * Data utworzenia: 12.12.14 18:29
 *
 * @author Michał Toporowski
 */
public class ComplexityTest {

    private final MSTAlgorithm[] algorithms = {new PrimAlgorithm(), new KruskalAlgorithm()};
    private static final float[] DENSITIES = {0.1f, 0.2f, 0.3f, 0.4f, 0.5f, 0.6f, 0.7f, 0.8f, 0.9f};
    private static final int[] VERTEX_COUNTS = {50, 100, 150, 200, 250, 300, 350, 400};
    private static final int NUMBER_OF_TESTS = 5;
    private final GraphGenerator generator = new GraphGeneratorImpl();


    @Test
    public void testComplexity() throws Exception {
        Map<String, long[][]> times = new HashMap<>();
        for (MSTAlgorithm algorithm : algorithms) {
            times.put(algorithm.getClass().getSimpleName(), new long[DENSITIES.length][VERTEX_COUNTS.length]);
        }
        for (int i = 0; i < DENSITIES.length; i++) {
            for (int j = 0; j < VERTEX_COUNTS.length; j++) {
                for (MSTAlgorithm algorithm : algorithms) {
                    String algName = algorithm.getClass().getSimpleName();
                    long sum = 0;
                    for (int k = 0; k < NUMBER_OF_TESTS; k++) {
                        Graph graph = generator.generate(VERTEX_COUNTS[j], DENSITIES[i]);
                        // Uruchom algorytm
                        long time = performTest(algorithm, graph);
                        // WYpisz info
                        System.out.println(String.format("Wykonano test %s (algorytm: %s; gęstość: %s; liczba wierzchołków: %s; czas: %s ms)",
                                i, algName, DENSITIES[i], VERTEX_COUNTS[j], time));
                        sum += time;
                    }
                    // Zapisz wynik
                    times.get(algName)[i][j] = sum / NUMBER_OF_TESTS;
                    // WYpisz info
                    System.out.println(String.format("Wykonano test (algorytm: %s; gęstość: %s; liczba wierzchołków: %s; średni czas: %s ms)",
                            algName, DENSITIES[i], VERTEX_COUNTS[j], sum / NUMBER_OF_TESTS));
                }
            }
        }
        // Eksport wyników do pliku
        exportResults(times);
    }


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

    /**
     * Eksportuje wyniki testu do pliku CSV
     *
     * @param times
     * @throws IOException
     */
    private void exportResults(Map<String, long[][]> times) throws IOException {
        for(String algName : times.keySet()) {
            FileWriter writer = new FileWriter(algName + ".csv");
            for (long[] row : times.get(algName)) {
                String line = "";
                for (long value : row) {
                    line += Long.toString(value);
                    line += ',';
                }
                line = line.substring(0, line.length() - 1) + '\n';
                writer.append(line);
            }
            writer.flush();
            writer.close();
        }
    }
}
