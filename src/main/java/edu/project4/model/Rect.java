package edu.project4.model;

import java.util.Random;

public record Rect(double x, double y, double width, double height) {
    public boolean contains(Point p) {
        return p.x() >= x && p.x() <= x + width
            && p.y() >= y && p.y() <= y + height;
    }

    public Point toPixelCoords(Point p, int imgWidth, int imgHeight) {
        double px = (p.x() - x) / width * imgWidth;
        double py = ((y + height) - p.y()) / height * imgHeight;
        return new Point(px, py);
    }

    public Point fromPixelCoords(Point pixelPoint, int imageWidth, int imageHeight) {
        double wx = x + (pixelPoint.x() * width / imageWidth);
        double wy = y + height - (pixelPoint.y() * height / imageHeight);
        return new Point(wx, wy);
    }

    public Point randomPoint(Random rand) {
        return new Point(rand.nextDouble(x, x + width), rand.nextDouble(y, y + height));
    }
}
