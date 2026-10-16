package io.github.kirimikh;

public record Tetragon(Point a, Point b, Point c, Point d) {
    public Tetragon(int x1, int y1, int x2, int y2, int x3, int y3, int x4, int y4) {
        this(new Point(x1, y1), new Point(x2, y2), new Point(x3, y3), new Point(x4, y4));
    }

    public double area() {
        return Math.abs(a.x() * b.y() - b.x() * a.y() + b.x() * c.y() - c.x() * b.y() + c.x() * d.y() - d.x() * c.y()
                + d.x() * a.y() - a.x() * d.y()) / 2.0;
    }

    public double perimeter() {
        return a.distanceTo(b) + b.distanceTo(c) + c.distanceTo(d) + d.distanceTo(a);
    }

    public boolean isRhombus() {
        return a.distanceTo(b) == b.distanceTo(c) && b.distanceTo(c) == c.distanceTo(d)
                && c.distanceTo(d) == d.distanceTo(a);
    }

    public boolean isRectangle() {
        return a.distanceTo(c) == b.distanceTo(d) && a.distanceTo(b) == c.distanceTo(d)
                && b.distanceTo(c) == a.distanceTo(d);
    }

    public enum Type {
        RHOMBUS, RECTANGLE, SQUARE, ARBITRARY
    }

    public Type type() {
        if (isRhombus() && isRectangle()) {
            return Type.SQUARE;
        } else if (isRhombus()) {
            return Type.RHOMBUS;
        } else if (isRectangle()) {
            return Type.RECTANGLE;
        } else {
            return Type.ARBITRARY;
        }
    }
}
