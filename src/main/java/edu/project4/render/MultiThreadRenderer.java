package edu.project4.render;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import edu.project4.utils.ValidationUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class MultiThreadRenderer implements Renderer {

    @Override
    public FractalImage render(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        RenderParams params
    ) {
        ValidationUtils.validateRenderParams(canvas, world, variations, params.symmetry());

        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        List<Future<FractalImage>> futures = submitRenderTasks(canvas, world, variations, params, numThreads, executor);

        waitForCompletion(executor);
        mergeResults(canvas, futures);

        return canvas;
    }

    private List<Future<FractalImage>> submitRenderTasks(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        RenderParams params,
        int numThreads,
        ExecutorService executor
    ) {
        List<Future<FractalImage>> futures = new ArrayList<>();
        int width = canvas.width();
        int height = canvas.height();

        int baseSamples = params.samples() / numThreads;
        int remainder = params.samples() % numThreads;

        for (int k = 0; k < numThreads; k++) {
            int threadSamples = baseSamples + (k < remainder ? 1 : 0);
            RenderParams threadParams = params.withSamples(threadSamples);

            futures.add(executor.submit(() -> {
                FractalImage localImage = FractalImage.create(width, height);
                ThreadLocalRandom random = ThreadLocalRandom.current();
                FractalWorker worker = new FractalWorker(localImage, world, variations, threadParams, random);
                return worker.run();
            }));
        }

        return futures;
    }

    public void waitForCompletion(ExecutorService executor) {
        executor.shutdown();
        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                throw new RuntimeException("Время выполнения истекло");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Выполнение прервано", e);
        }
    }

    private void mergeResults(FractalImage canvas, List<Future<FractalImage>> futures) {
        futures.parallelStream().forEach(future -> {
            try {
                FractalImage part = future.get();
                for (int y = 0; y < canvas.height(); y++) {
                    for (int x = 0; x < canvas.width(); x++) {
                        mergePixel(canvas, part, x, y);
                    }
                }
            } catch (InterruptedException | ExecutionException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Error merging rendering results", e);
            }
        });
    }

    private void mergePixel(FractalImage canvas, FractalImage part, int x, int y) {
        Pixel base = canvas.pixel(x, y);
        Pixel add = part.pixel(x, y);
        int totalHits = base.hitCount() + add.hitCount();

        if (totalHits == 0) {
            return;
        }

        int r = (base.r() * base.hitCount() + add.r() * add.hitCount()) / totalHits;
        int g = (base.g() * base.hitCount() + add.g() * add.hitCount()) / totalHits;
        int b = (base.b() * base.hitCount() + add.b() * add.hitCount()) / totalHits;

        canvas.setPixel(x, y, new Pixel(r, g, b, totalHits));
    }
}
