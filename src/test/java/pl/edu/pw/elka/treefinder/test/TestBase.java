package pl.edu.pw.elka.treefinder.test;

import java.net.URI;
import java.net.URISyntaxException;

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
}
