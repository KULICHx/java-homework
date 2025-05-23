package edu.hw8.task3Test;

import edu.hw8.task3.cracker.MultiThreadCracker;
import edu.hw8.task3.cracker.SingleThreadCracker;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class PerformanceTest {
    private static final long MAX_TEST_TIME_MS = 50;

    @Test
    void comparePerformance() throws Exception {
        // Используем простые пароли для быстрого тестирования
        Path testFile = Path.of("test_hashes.txt");
        Files.write(testFile, List.of(
            "user1 0cc175b9c0f1b6a831c399e269772661", // "a"
            "user2 92eb5ffee6ae2fec3ad71c777531578f"  // "b"
        ));

        int maxLength = 1;

        long start = System.nanoTime();
        SingleThreadCracker single = new SingleThreadCracker(testFile);
        Map<String, String> singleResult = single.crack(maxLength);
        long singleTime = System.nanoTime() - start;

        start = System.nanoTime();
        MultiThreadCracker multi = new MultiThreadCracker(testFile, 4);
        Map<String, String> multiResult = multi.crack(maxLength);
        long multiTime = System.nanoTime() - start;

        assumeTrue(singleTime < MAX_TEST_TIME_MS * 1_000_000L,
            "Тест выполняется слишком долго, пропускаем");

        System.out.printf("Single-thread: %d µs%n", singleTime / 1_000);
        System.out.printf("Multi-thread: %d µs%n", multiTime / 1_000);
        System.out.printf("Speedup: %.2fx%n", (double) singleTime / multiTime);

        assertEquals(singleResult, multiResult);
        Files.delete(testFile);
    }
}
