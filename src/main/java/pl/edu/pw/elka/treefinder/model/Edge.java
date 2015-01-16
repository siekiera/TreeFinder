package pl.edu.pw.elka.treefinder.model;

/**
 * Krawędź grafu
 * <p/>
 * Data utworzenia: 12.12.14 18:12
 *
 * @author Michał Toporowski
 */
public class Edge {
    private final Vertex start;
    private final Vertex end;
    private final double weight;

    public Edge(Vertex start, Vertex end, double weight) {
        this.start = start;
        this.end = end;
        this.weight = weight;
    }

    public Vertex getStart() {
        return start;
    }

    public Vertex getEnd() {
        return end;
    }

    public double getWeight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Edge edge = (Edge) o;
        // Krawędzie powinny być równe niezależnie od kolejności start, end
        return ((start.equals(edge.start) && end.equals(edge.end))
                || (start.equals(edge.end) && end.equals(edge.start)));
    }

    @Deprecated
    public boolean equalsNoReferences(Edge edge) {
        if (equals(edge)) {
            return true;
        }
        return ((start.equals(edge.start) && end.equals(edge.end))
                || (start.equals(edge.end) && end.equals(edge.start)));

    }

    @Override
    public int hashCode() {
        // hashCode taki sam niezależnie od kolejności start, end
        int startHash = start.hashCode();
        int endHash = end.hashCode();
        int result;
        if (startHash < endHash) {
            result = startHash;
            result = 31 * result + endHash;
        } else {
            result = endHash;
            result = 31 * result + startHash;
        }
        return result;
    }
}
