package edu.hw9.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("ConcurrentStatsCollector тесты")
class ConcurrentStatsCollectorTest {

    @RepeatedTest(5)
    @DisplayName("Обработка конкурентных вставок (repeated)")
    void shouldHandleConcurrentPushes() throws InterruptedException {
        // Arrange
        StatsCollector collector = new StatsCollector();
        int threadsCount = 10;
        int pushesPerThread = 1000;
        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        CountDownLatch latch = new CountDownLatch(threadsCount);

        // Act
        for (int i = 0; i < threadsCount; i++) {
            executor.execute(() -> {
                try {
                    for (int j = 0; j < pushesPerThread; j++) {
                        collector.push("concurrent_metric", new double[]{1.0});
                    }
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();
        executor.shutdown();

        // Assert
        MetricStats stats = collector.stats().get("concurrent_metric");
        assertThat(stats.count()).isEqualTo(threadsCount * pushesPerThread);
        assertThat(stats.sum()).isEqualTo(threadsCount * pushesPerThread * 1.0);
    }

    @Test
    @DisplayName("Конкурентные вставки и чтение статистики")
    void shouldHandleConcurrentPushAndStats() throws Exception {
        StatsCollector collector = new StatsCollector();
        ExecutorService executor = Executors.newFixedThreadPool(2);

        Future<?> pushFuture = executor.submit(() -> {
            for (int i = 0; i < 10000; i++) {
                collector.push("shared_metric", new double[]{i});
            }
        });

        Future<Map<String, MetricStats>> statsFuture = executor.submit(() -> {
            Map<String, MetricStats> result = null;
            for (int i = 0; i < 100; i++) {
                result = collector.stats();
                Thread.yield();
            }
            return result;
        });

        pushFuture.get();
        Map<String, MetricStats> stats = statsFuture.get();
        executor.shutdown();

        assertThat(stats.get("shared_metric").count())
            .isBetween(1, 10000); // Проверяем, что статистика обновлялась
    }
}
