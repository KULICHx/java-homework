package edu.hw7;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Task1Test {

    @Test
    @DisplayName("Маленькое значение n: проверка, что счётчик равен 2 * n")
    void testSmallIncrement() {
        int n = 10;
        int expected = 2 * n;
        int result = Task1.incrementCounterConcurrently(n);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Значение n = 0: счётчик должен быть равен 0")
    void testZeroIncrement() {
        int n = 0;
        int result = Task1.incrementCounterConcurrently(n);
        assertEquals(0, result);
    }

    @Test
    @DisplayName("Большое значение n: проверка корректности при больших нагрузках")
    void testLargeIncrement() {
        int n = 1_000_000;
        int expected = 2 * n;
        int result = Task1.incrementCounterConcurrently(n);
        assertEquals(expected, result);
    }

    @Test
    @DisplayName("Повторный запуск: счётчик стабильно выдаёт правильный результат")
    void testMultipleRunsConsistency() {
        int n = 100_000;
        for (int i = 0; i < 5; i++) {
            int result = Task1.incrementCounterConcurrently(n);
            assertEquals(2 * n, result);
        }
    }
}
