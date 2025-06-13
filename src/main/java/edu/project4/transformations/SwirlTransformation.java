package edu.project4.transformations;

import edu.project4.model.Point;

public class SwirlTransformation implements Transformation {
    @Override
    public Point apply(Point point) {
        if (Double.isNaN(point.x()) || Double.isNaN(point.y())) {
            throw new IllegalArgumentException("Point coordinates cannot be NaN");
        }
        if (Double.isInfinite(point.x()) || Double.isInfinite(point.y())) {
            throw new IllegalArgumentException("Point coordinates cannot be infinite");
        }

        double r2 = point.x() * point.x() + point.y() * point.y();
        double sin = Math.sin(r2);
        double cos = Math.cos(r2);
        return new Point(
            point.x() * sin - point.y() * cos,
            point.x() * cos + point.y() * sin
        );
    }
}
