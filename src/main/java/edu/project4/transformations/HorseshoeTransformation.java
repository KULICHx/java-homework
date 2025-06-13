package edu.project4.transformations;

import edu.project4.model.Point;

public class HorseshoeTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        double r = Math.sqrt(point.x() * point.x() + point.y() * point.y());

        if (r == 0) {
            throw new ArithmeticException("Деление на ноль");
        }

        return new Point(
            (point.x() * point.x() - point.y() * point.y()) / r,
            2 * point.x() * point.y() / r
        );
    }
}
