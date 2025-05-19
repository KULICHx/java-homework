package edu.hw7.task4;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class MonteCarloPiMultiThread {
    private MonteCarloPiMultiThread() {

    }

    private static final Logger LOGGER = Logger.getLogger(MonteCarloPiMultiThread.class.getName());

    public static int runMonteCarloSimulation(int totalCount, int threadCount) {

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        int pointsPerThread = totalCount / threadCount;
        List<Future<Integer>> futures = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            futures.add(executor.submit(() -> MonteCarloPiSingleThread.runMonteCarloSimulation(pointsPerThread)));
        }

        int totalCircleCount = 0;
        for (Future<Integer> future : futures) {
            try {
                totalCircleCount += future.get();
            } catch (InterruptedException | ExecutionException e) {
                LOGGER.severe("Ошибка при выполнении задачи: " + e.getMessage());
                Thread.currentThread().interrupt();
            }
        }

        executor.shutdown();
        return totalCircleCount;
    }
}
