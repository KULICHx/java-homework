package edu.hw7;

import java.util.concurrent.atomic.AtomicInteger;

public class Task1 {
    private Task1() {
    }

    public static int incrementCounterConcurrently(int n) {
        AtomicInteger counter = new AtomicInteger();

        Thread firstThread = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                counter.incrementAndGet();
            }
        });

        Thread secondThread = new Thread(() -> {
            for (int i = 0; i < n; i++) {
                counter.incrementAndGet();
            }
        });

        firstThread.start();
        secondThread.start();

        try {
            firstThread.join();
            secondThread.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Поток был прерван", e);
        }

        return counter.get();
    }
}
