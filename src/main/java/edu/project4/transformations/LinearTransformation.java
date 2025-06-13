package edu.project4.transformations;

import edu.project4.model.Point;

public class LinearTransformation implements Transformation {
    private final double a;
    private final double b;
    private final double c;
    private final double d;
    private final double e;
    private final double f;

    public LinearTransformation(double a, double b, double c,
        double d, double e, double f) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.e = e;
        this.f = f;
    }

    @Override
    public Point apply(Point point) {
        return new Point(
            a * point.x() + b * point.y() + c,
            d * point.x() + e * point.y() + f
        );
    }
}
