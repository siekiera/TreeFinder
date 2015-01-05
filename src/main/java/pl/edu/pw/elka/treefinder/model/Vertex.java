package pl.edu.pw.elka.treefinder.model;

/**
 * Wierzchołek grafu
 * <p/>
 * Data utworzenia: 12.12.14 18:11
 *
 * @author Michał Toporowski
 */
public class Vertex {
    private final double x;
    private final double y;

    public Vertex(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Vertex vertex = (Vertex) o;

        if (Double.compare(vertex.x, x) != 0) return false;
        if (Double.compare(vertex.y, y) != 0) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(x);
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(y);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}
