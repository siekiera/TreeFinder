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

        if (Double.compare(edge.weight, weight) != 0) return false;
        // Porównujemy referencje; graf niezorientowany
        return ((start == edge.start && end == edge.end)
                || (start == edge.end && end == edge.start));
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = start.hashCode();
        result = 31 * result + end.hashCode();
        temp = Double.doubleToLongBits(weight);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
