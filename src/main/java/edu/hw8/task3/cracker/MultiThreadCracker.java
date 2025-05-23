package edu.hw8.task3.cracker;

import edu.hw8.task3.generator.PasswordGenerator;
import edu.hw8.task3.model.UserHashRecord;
import edu.hw8.task3.util.FileUtils;
import edu.hw8.task3.util.HashUtils;
import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class MultiThreadCracker implements PasswordCracker {
    private final Map<String, String> hashToUsername;
    private final int threadCount;
    private static final Logger LOGGER = Logger.getLogger(MultiThreadCracker.class.getName());
    private static final int TERMINATION_TIMEOUT_MINUTES = 10;
    private static final long NANOS_TO_MILLIS = 1_000_000L;

    public MultiThreadCracker(Path filePath, int threadCount) throws IOException {
        List<UserHashRecord> records = FileUtils.loadHashes(filePath);
        this.hashToUsername = new HashMap<>();
        for (UserHashRecord userRecord : records) {
            this.hashToUsername.put(userRecord.md5Hash(), userRecord.username());
        }
        this.threadCount = threadCount;
    }

    @Override
    public Map<String, String> crack(int maxPasswordLength) {
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        Map<String, String> result = new ConcurrentHashMap<>();
        PasswordGenerator generator = new PasswordGenerator(maxPasswordLength);

        long startTime = System.nanoTime();

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                while (generator.hasNext() && !hashToUsername.isEmpty()) {
                    String password = generator.nextPassword();
                    String md5 = HashUtils.hash(password);
                    String username = hashToUsername.remove(md5);
                    if (username != null) {
                        result.put(username, password);
                    }
                }
            });
        }
        executor.shutdown();
        try {
            boolean terminated = executor.awaitTermination(TERMINATION_TIMEOUT_MINUTES, TimeUnit.MINUTES);
            if (!terminated) {
                LOGGER.warning("Executor не завершил работу за отведённое время.");
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.warning("Ожидание завершения executor было прервано.");
        }

        long endTime = System.nanoTime();
        long durationMillis = (endTime - startTime) / NANOS_TO_MILLIS;

        LOGGER.info("Время выполнения: " + durationMillis + " мс");

        return result;
    }
}
