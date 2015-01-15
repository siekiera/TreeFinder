package pl.edu.pw.elka.treefinder.logic.algorithm;

import org.junit.Test;

/**
 * Created by monika on 2015-01-15.
 */
public class KruskalAlgorithmTest extends CorrectnessTest {
    @Test
    public void testCalculate() throws Exception {
        super.performTest(new KruskalAlgorithm(), "kruskalSimpleTest");
    }
}
