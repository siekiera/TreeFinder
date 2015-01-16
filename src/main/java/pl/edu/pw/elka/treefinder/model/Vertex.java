package pl.edu.pw.elka.treefinder.model;

import java.util.List;

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
    private List<Edge> neighbours;

    public List<Edge> getNeighbours() { return neighbours; }

    public boolean isVisited() {
        return visited;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    private boolean visited;

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

    boolean isAcyclic(List<Edge> edges, Vertex from) {
        if(this.isVisited()) {
            return false;
        }
        this.setVisited(true);
        for (Edge edge : edges) {
            if(edge.getStart() == this && edge.getEnd() != from)
            {
                if(!edge.getEnd().isAcyclic(edges, this)){
                    return false;
                }
            }
            if(edge.getEnd() == this && edge.getStart() != from)
            {
                if(!edge.getStart().isAcyclic(edges, this)){
                    return false;
                }
            }
        }

        return true;
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

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
