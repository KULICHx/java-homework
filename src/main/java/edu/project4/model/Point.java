package edu.project4.model;

public record Point(double x, double y) {
    public static Point rotate(Point point, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double x = point.x() * cos - point.y() * sin;
        double y = point.x() * sin + point.y() * cos;
        return new Point(x, y);
    }

}
