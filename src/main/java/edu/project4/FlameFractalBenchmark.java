package edu.project4;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.render.MultiThreadRenderer;
import edu.project4.render.RenderParams;
import edu.project4.render.Renderer;
import edu.project4.render.SingleThreadRenderer;
import edu.project4.transformations.HorseshoeTransformation;
import edu.project4.transformations.SinusoidalTransformation;
import edu.project4.transformations.SphericalTransformation;
import edu.project4.transformations.SwirlTransformation;
import edu.project4.transformations.Transformation;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class FlameFractalBenchmark {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int SAMPLES = 500_000;
    private static final int ITERATIONS = 100;
    private static final long SEED = 12345;
    private static final int SYMMETRY = 7;
    private static final int GAMMA = 20;
    private static final double NANOS_IN_SECOND = 1_000_000_000.0;

    private static final Rect WORLD = new Rect(-2.0, -2.0, 4.0, 4.0);
    private static final List<Transformation> VARIATIONS = List.of(
        new SphericalTransformation(),
        new SinusoidalTransformation(),
        new SwirlTransformation(),
        new HorseshoeTransformation()
    );

    private static final Path SINGLE_OUTPUT_PATH = Path.of("fractal_single.png");
    private static final Path MULTI_OUTPUT_PATH = Path.of("fractal_multi.png");

    private static final Logger LOGGER = Logger.getLogger(FlameFractalBenchmark.class.getName());

    private FlameFractalBenchmark() {
    }

    /**
     * Точка входа в программу.
     * Запускает бенчмарк рендеринга фрактала двумя способами:
     * однопоточным и многопоточным.
     *
     * @param args аргументы командной строки (не используются)
     */
    @SuppressWarnings("UncommentedMain")
    public static void main(String[] args) {
        RenderParams renderParams = new RenderParams(SAMPLES, ITERATIONS, SEED, SYMMETRY, GAMMA);

        runBenchmark("SingleThread", new SingleThreadRenderer(), renderParams, SINGLE_OUTPUT_PATH);
        runBenchmark("MultiThread", new MultiThreadRenderer(), renderParams, MULTI_OUTPUT_PATH);
    }

    private static void runBenchmark(
        String label,
        Renderer renderer,
        RenderParams params,
        Path outputPath
    ) {
        FractalImage canvas = FractalImage.create(WIDTH, HEIGHT);
        long startTime = System.nanoTime();
        FractalImage result = renderer.render(canvas, WORLD, VARIATIONS, params);
        long endTime = System.nanoTime();

        ImageUtils.save(result, outputPath, ImageFormat.PNG);
        double durationSec = (endTime - startTime) / NANOS_IN_SECOND;
        LOGGER.log(
            Level.INFO, "{0} рендеринг завершен за {1,number,#.###} секунд. Сохранено в {2}",
            new Object[] {label, durationSec, outputPath}
        );
    }
}
