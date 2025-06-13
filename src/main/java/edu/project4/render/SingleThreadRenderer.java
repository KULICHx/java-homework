package edu.project4.render;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import edu.project4.utils.ValidationUtils;
import java.util.List;
import java.util.Random;

public class SingleThreadRenderer implements Renderer {

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        RenderParams params
    ) {

        ValidationUtils.validateRenderParams(canvas, world, variations, params.symmetry());

        Random random = new Random(params.seed());

        FractalWorker worker = new FractalWorker(
            canvas,
            world,
            variations,
            params,
            random
        );

        return worker.run();
    }

}
