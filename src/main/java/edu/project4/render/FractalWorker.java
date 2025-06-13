package edu.project4.render;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.Random;

public class FractalWorker {
    private static final double FULL_CIRCLE_RADIANS = 2 * Math.PI;

    private final FractalImage canvas;
    private final Rect world;
    private final List<Transformation> variations;
    private final RenderParams params;
    private final Random random;

    public FractalWorker(FractalImage canvas,
                         Rect world,
                         List<Transformation> variations,
                         RenderParams params,
                         Random random) {
        this.canvas = canvas;
        this.world = world;
        this.variations = variations;
        this.params = params;
        this.random = random;
    }

    public FractalImage run() {
        int width = canvas.width();
        int height = canvas.height();

        for (int i = 0; i < params.samples(); i++) {
            Point point = world.randomPoint(random);

            for (int j = 0; j < params.iterPerSample(); j++) {
                Transformation transformation = variations.get(random.nextInt(variations.size()));
                point = transformation.apply(point);

                if (j < params.skipSteps() || !world.contains(point)) {
                    continue;
                }
                for (int s = 0; s < params.symmetry(); s++) {
                    double angle = FULL_CIRCLE_RADIANS * s / params.symmetry();
                    Point rotated = Point.rotate(point, angle);

                    if (!world.contains(rotated)) {
                        continue;
                    }

                    Point pixelPoint = world.toPixelCoords(rotated, width, height);
                    int x = (int) pixelPoint.x();
                    int y = (int) pixelPoint.y();

                    if (canvas.contains(x, y)) {
                        Pixel oldPixel = canvas.pixel(x, y);
                        int hitCount = oldPixel.hitCount();
                        canvas.setPixel(x, y, Pixel.updatePixel(oldPixel, hitCount));
                    }
                }
            }
        }
        return canvas;
    }
}
