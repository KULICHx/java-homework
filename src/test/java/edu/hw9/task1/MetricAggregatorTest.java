package edu.hw9.task1;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.*;
import org.junit.jupiter.params.provider.*;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.assertThat;

class MetricAggregatorTest {

    @ParameterizedTest(name = "Для массива {0} сумма должна быть {1}, среднее {2}")
    @MethodSource("provideTestData")
    void shouldCorrectlyAggregateValues(double[] values, double expectedSum, double expectedAvg) {
        MetricAggregator aggregator = new MetricAggregator();
        aggregator.update(values);

        MetricStats stats = aggregator.getStats();
        assertThat(stats.sum()).isEqualTo(expectedSum);
        assertThat(stats.avg()).isEqualTo(expectedAvg);
    }

    private static Stream<Arguments> provideTestData() {
        return Stream.of(
            Arguments.of(new double[]{1, 2, 3}, 6.0, 2.0),
            Arguments.of(new double[]{-1, 0, 1}, 0.0, 0.0),
            Arguments.of(new double[]{10}, 10.0, 10.0)
        );
    }

    @Test
    void shouldHandleMinMaxCorrectly() {
        MetricAggregator aggregator = new MetricAggregator();
        aggregator.update(new double[]{5, -3, 10, 0});

        MetricStats stats = aggregator.getStats();
        assertThat(stats.min()).isEqualTo(-3.0);
        assertThat(stats.max()).isEqualTo(10.0);
    }
}
