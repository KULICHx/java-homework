package edu.hw7.task4;

import java.util.concurrent.ThreadLocalRandom;
import java.util.logging.Logger;

public class MonteCarloPiSingleThread {
    private MonteCarloPiSingleThread() {}

    final static Logger LOGGER = Logger.getLogger(MonteCarloPiSingleThread.class.getName());
    private static final double PI_COEFFICIENT = 4.0;
    private static final double NANOS_TO_MILLIS = 1_000_000.0;

    public static int runMonteCarloSimulation(int numberOfPoints) {

        long startTime = System.nanoTime();

        int circleCount = 0;
        for (int i = 0; i < numberOfPoints; i++) {
            double x = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            double y = ThreadLocalRandom.current().nextDouble(-1.0, 1.0);
            if (x * x + y * y <= 1) {
                circleCount++;
            }
        }
        double piApprox = PI_COEFFICIENT * circleCount / numberOfPoints;
        long endTime = System.nanoTime();
        double elapsedMillis = (endTime - startTime) / NANOS_TO_MILLIS;

        LOGGER.info("Приблизительное значение Pi: " + piApprox);
        LOGGER.info("Время выполнения: " + elapsedMillis + " мс");

        return circleCount;
    }
}
