package edu.hw7.task4Test;

import edu.hw7.task4.MonteCarloPiSingleThread;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MonteCarloPiSingleThreadTest {
    private static final double PI = Math.PI;
    private static final double ALLOWED_ERROR = 0.01; // 1% допустимая погрешность

    @Test
    @DisplayName("Проверка точности вычисления π (10K итераций)")
    void shouldCalculatePiWithReasonablePrecisionFor10K() {
        // Arrange
        int iterations = 10_000;

        // Act
        int circleCount = MonteCarloPiSingleThread.runMonteCarloSimulation(iterations);
        double piApprox = 4.0 * circleCount / iterations;

        // Assert
        assertThat(Math.abs(piApprox - PI) / PI).isLessThan(ALLOWED_ERROR);
    }

    @Test
    @DisplayName("Проверка точности вычисления π (1M итераций)")
    void shouldCalculatePiWithBetterPrecisionFor1M() {
        // Arrange
        int iterations = 1_000_000;

        // Act
        int circleCount = MonteCarloPiSingleThread.runMonteCarloSimulation(iterations);
        double piApprox = 4.0 * circleCount / iterations;

        // Assert
        assertThat(Math.abs(piApprox - PI) / PI).isLessThan(ALLOWED_ERROR / 3);
    }

    @Test
    @DisplayName("Логирование времени выполнения")
    void shouldLogExecutionTime() {
        // Arrange
        int iterations = 100_000;
        TestLogHandler handler = new TestLogHandler();
        Logger logger = Logger.getLogger(MonteCarloPiSingleThread.class.getName());
        logger.addHandler(handler);

        // Act
        MonteCarloPiSingleThread.runMonteCarloSimulation(iterations);

        // Assert
        assertTrue(handler.getLog().contains("Приблизительное значение Pi"));
        assertTrue(handler.getLog().contains("Время выполнения"));
        logger.removeHandler(handler);
    }

    @Test
    @DisplayName("Проверка возвращаемого значения")
    void shouldReturnValidCircleCount() {
        // Arrange
        int iterations = 10_000;

        // Act
        int circleCount = MonteCarloPiSingleThread.runMonteCarloSimulation(iterations);

        // Assert
        assertThat(circleCount).isBetween(0, iterations);
    }

    private static class TestLogHandler extends Handler {
        private final StringBuilder log = new StringBuilder();

        @Override
        public void publish(LogRecord record) {
            log.append(record.getMessage());
        }

        @Override
        public void flush() {}
        @Override
        public void close() {}

        public String getLog() {
            return log.toString();
        }
    }
}
