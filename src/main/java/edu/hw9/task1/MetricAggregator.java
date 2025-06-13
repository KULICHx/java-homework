package edu.hw9.task1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

class MetricAggregator {

    private final AtomicReference<Double> min = new AtomicReference<>(Double.POSITIVE_INFINITY);
    private final AtomicReference<Double> max = new AtomicReference<>(Double.NEGATIVE_INFINITY);
    private final AtomicReference<Double> sum = new AtomicReference<>(0.0);
    private final AtomicInteger count = new AtomicInteger(0);

    public synchronized void update(double[] values) {
        for (double value : values) {
            sum.updateAndGet(v -> v + value);
            count.incrementAndGet();

            min.updateAndGet(current -> Math.min(current, value));
            max.updateAndGet(current -> Math.max(current, value));
        }
    }

    public MetricStats getStats() {
        int cnt = count.get();
        double s = sum.get();
        return new MetricStats(
            s,
            cnt > 0 ? s / cnt : 0,
            min.get(),
            max.get(),
            cnt
        );
    }
}
