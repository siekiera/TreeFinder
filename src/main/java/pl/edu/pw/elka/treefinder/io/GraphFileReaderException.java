package pl.edu.pw.elka.treefinder.io;

/**
 * Wyjątek podczas wczytywania grafu z pliku
 * <p/>
 * Data utworzenia: 05.01.15 20:02
 *
 * @author Michał Toporowski
 */
public class GraphFileReaderException extends Exception {
    public GraphFileReaderException(String message) {
        super(message);
    }

    public GraphFileReaderException(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphFileReaderException(Throwable cause) {
        super(cause);
    }
}
