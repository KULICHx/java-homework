package edu.hw8.task2Test;

import edu.hw8.task2.FixedThreadPool;
import org.junit.jupiter.api.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLongArray;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тесты для FixedThreadPool")
class FixedThreadPoolTest {

    private FixedThreadPool pool;

    @BeforeEach
    void setUp() {
        pool = new FixedThreadPool(4);
        pool.start();
    }

    @AfterEach
    void tearDown() throws Exception {
        pool.close();
    }

    @Test
    @DisplayName("Одна задача выполняется успешно")
    void testSingleTaskExecutesSuccessfully() throws InterruptedException {
        AtomicInteger result = new AtomicInteger(0);

        pool.execute(() -> result.set(42));

        Thread.sleep(200);

        assertEquals(42, result.get());
    }

    @Test
    @DisplayName("Несколько задач выполняются корректно")
    void testMultipleTasksExecute() throws InterruptedException {
        AtomicInteger counter = new AtomicInteger(0);
        int taskCount = 10;

        for (int i = 0; i < taskCount; i++) {
            pool.execute(counter::incrementAndGet);
        }

        Thread.sleep(500);

        assertEquals(taskCount, counter.get());
    }

    @Test
    @DisplayName("Пул корректно останавливается через close()")
    void testPoolStopsCorrectly() throws Exception {
        pool.close();
        assertFalse(pool.isRunning());
    }

    @Test
    @DisplayName("Задачи выполняются параллельно в нескольких потоках")
    void testTasksExecuteInParallel() throws InterruptedException {
        long startTime = System.currentTimeMillis();
        AtomicInteger counter = new AtomicInteger(0);

        for (int i = 0; i < 4; i++) {
            pool.execute(() -> {
                try {
                    Thread.sleep(300);
                    counter.incrementAndGet();
                } catch (InterruptedException ignored) {}
            });
        }

        Thread.sleep(400);

        assertEquals(4, counter.get());

        long duration = System.currentTimeMillis() - startTime;
        assertTrue(duration < 1000);
    }
    @Test
    @DisplayName("Вычисление чисел Фибоначчи в пуле потоков")
    void testFibonacciCalculationInThreadPool() throws InterruptedException {
        int tasksCount = 5;
        int startIndex = 20;

        AtomicLongArray results = new AtomicLongArray(tasksCount);
        CountDownLatch latch = new CountDownLatch(tasksCount);

        for (int i = 0; i < tasksCount; i++) {
            final int n = startIndex + i;
            final int idx = i;
            pool.execute(() -> {
                results.set(idx, fibonacci(n));
                latch.countDown();
            });
        }

        boolean completed = latch.await(5, java.util.concurrent.TimeUnit.SECONDS);
        assertTrue(completed, "Все задачи должны завершиться");

        assertEquals(6765, results.get(0));
        assertEquals(10946, results.get(1));}

    private long fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
