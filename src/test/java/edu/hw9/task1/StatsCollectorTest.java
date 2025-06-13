package edu.hw9.task1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.assertj.core.api.Assertions.*;

@DisplayName("StatsCollector тесты")
class StatsCollectorTest {

    @Test
    @DisplayName("Агрегирование одной метрики")
    void shouldAggregateSingleMetric() {
        // Arrange
        StatsCollector collector = new StatsCollector();
        double[] values = {1.0, 2.0, 3.0};

        // Act
        collector.push("test_metric", values);
        Map<String, MetricStats> stats = collector.stats();

        // Assert
        assertThat(stats).containsOnlyKeys("test_metric");
        MetricStats metricStats = stats.get("test_metric");
        assertThat(metricStats.sum()).isEqualTo(6.0);
        assertThat(metricStats.avg()).isEqualTo(2.0);
        assertThat(metricStats.min()).isEqualTo(1.0);
        assertThat(metricStats.max()).isEqualTo(3.0);
        assertThat(metricStats.count()).isEqualTo(3);
    }

    @Test
    @DisplayName("Обработка пустого массива значений")
    void shouldHandleEmptyValues() {
        StatsCollector collector = new StatsCollector();
        collector.push("empty_metric", new double[]{});

        MetricStats stats = collector.stats().get("empty_metric");
        assertThat(stats.count()).isZero();
        assertThat(stats.avg()).isZero();
    }

    @Test
    @DisplayName("Агрегирование нескольких метрик")
    void shouldAggregateMultipleMetrics() {
        StatsCollector collector = new StatsCollector();
        collector.push("metric1", new double[]{1.0, 2.0});
        collector.push("metric2", new double[]{3.0, 4.0});

        Map<String, MetricStats> stats = collector.stats();
        assertThat(stats)
            .containsOnlyKeys("metric1", "metric2")
            .satisfies(metrics -> {
                assertThat(metrics.get("metric1").sum()).isEqualTo(3.0);
                assertThat(metrics.get("metric2").max()).isEqualTo(4.0);
            });
    }
}
