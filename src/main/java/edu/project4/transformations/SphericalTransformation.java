package edu.project4.transformations;

import edu.project4.model.Point;

public class SphericalTransformation implements Transformation {

    @Override
    public Point apply(Point point) {
        if (Double.isNaN(point.x()) || Double.isNaN(point.y())) {
            throw new IllegalArgumentException("Координаты точки не могут быть NaN");
        }
        if (Double.isInfinite(point.x()) || Double.isInfinite(point.y())) {
            throw new IllegalArgumentException("Координаты точки не могут быть infinite");
        }
        double r2 = point.x() * point.x() + point.y() * point.y();
        return (r2 == 0) ? new Point(0, 0) : new Point(point.x() / r2, point.y() / r2);
    }
}

