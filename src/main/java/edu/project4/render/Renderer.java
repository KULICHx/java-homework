package edu.project4.render;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;

@FunctionalInterface
public interface Renderer {
    FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        RenderParams params
    );
}
