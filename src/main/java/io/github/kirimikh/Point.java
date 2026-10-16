package io.github.kirimikh;

public record Point(int x, int y) {
    public double distanceTo(Point other) {
        return Math.sqrt(Math.pow(x - other.x(), 2) + Math.pow(y - other.y(), 2));
    }
}
