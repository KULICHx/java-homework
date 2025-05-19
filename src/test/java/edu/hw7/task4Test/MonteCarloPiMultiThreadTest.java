package edu.hw7.task4Test;

import edu.hw7.task4.MonteCarloPiMultiThread;
import edu.hw7.task4.MonteCarloPiSingleThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class MonteCarloPiMultiThreadTest {
    private static final double PI = Math.PI;
    private static final double ALLOWED_ERROR = 0.01; // 1% допустимая погрешность

    @Test
    @DisplayName("Проверка точности в многопоточном режиме (1M итераций)")
    void shouldCalculatePiWithReasonablePrecisionMultiThread(){
        // Arrange
        int iterations = 1_000_000;
        int threads = Runtime.getRuntime().availableProcessors();

        // Act
        int circleCount = MonteCarloPiMultiThread.runMonteCarloSimulation(iterations, threads);
        double piApprox = 4.0 * circleCount / iterations;

        // Assert
        assertThat(Math.abs(piApprox - PI) / PI).isLessThan(ALLOWED_ERROR);
    }

    @Test
    @DisplayName("Сравнение однопоточной и многопоточной версий")
    void shouldProduceSameResultAsSingleThread(){
        // Arrange
        int iterations = 100_000;
        int threads = 4;

        // Act
        int singleThreadCount = MonteCarloPiSingleThread.runMonteCarloSimulation(iterations);
        int multiThreadCount = MonteCarloPiMultiThread.runMonteCarloSimulation(iterations, threads);

        // Assert
        double singlePi = 4.0 * singleThreadCount / iterations;
        double multiPi = 4.0 * multiThreadCount / iterations;
        assertThat(Math.abs(singlePi - multiPi)).isLessThan(ALLOWED_ERROR * PI);
    }

    @Test
    @DisplayName("Обработка прерывания потока")
    void shouldHandleThreadInterruption() {
        // Arrange
        Thread testThread = new Thread(() -> {
            MonteCarloPiMultiThread.runMonteCarloSimulation(1_000_000, 4);
        });

        // Act & Assert
        testThread.start();
        testThread.interrupt();
    }

    @Test
    @DisplayName("Проверка балансировки нагрузки")
    void shouldHandleUnevenDistribution() {
        // Arrange
        int iterations = 1_000_007; // Простое число для проверки балансировки
        int threads = 4;

        // Act
        int circleCount = MonteCarloPiMultiThread.runMonteCarloSimulation(iterations, threads);
        double piApprox = 4.0 * circleCount / iterations;

        // Assert
        assertThat(Math.abs(piApprox - PI) / PI).isLessThan(ALLOWED_ERROR);
    }

    @Test
    @DisplayName("Проверка работы с разным количеством потоков")
    void shouldWorkWithDifferentThreadCounts(){
        // Arrange
        int iterations = 1_000_000;

        // Act & Assert
        for (int threads : new int[]{1, 2, 4, 8}) {
            int circleCount = MonteCarloPiMultiThread.runMonteCarloSimulation(iterations, threads);
            double piApprox = 4.0 * circleCount / iterations;
            assertThat(Math.abs(piApprox - PI) / PI).isLessThan(ALLOWED_ERROR);
        }
    }
}
