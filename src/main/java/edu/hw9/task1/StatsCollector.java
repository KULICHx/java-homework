package edu.hw9.task1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class StatsCollector {

    private final ConcurrentHashMap<String, MetricAggregator> metrics = new ConcurrentHashMap<>();

    public void push(String metricName, double[] values) {
        metrics.computeIfAbsent(metricName, k -> new MetricAggregator())
                .update(values);
    }

    public Map<String, MetricStats> stats() {
        Map<String, MetricStats> result = new ConcurrentHashMap<>();
        metrics.forEach((key, aggregator) -> result.put(key, aggregator.getStats()));
        return result;
    }
}
