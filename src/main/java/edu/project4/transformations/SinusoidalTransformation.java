package edu.project4.transformations;

import edu.project4.model.Point;

public class SinusoidalTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        if (Double.isNaN(point.x()) || Double.isNaN(point.y())) {
            throw new IllegalArgumentException("Координаты точки не могут быть NaN");
        }
        if (Double.isInfinite(point.x()) || Double.isInfinite(point.y())) {
            throw new IllegalArgumentException("Координаты точки не могут быть infinite");
        }

        return new Point(Math.sin(point.x()), Math.sin(point.y()));
    }
}
