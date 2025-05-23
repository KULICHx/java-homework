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
import java.util.logging.Logger;

public class SingleThreadCracker implements PasswordCracker {
    private final Map<String, String> hashToUsername;
    private static final Logger LOGGER = Logger.getLogger(SingleThreadCracker.class.getName());
    private static final long NANOS_TO_MILLIS = 1_000_000L;

    public SingleThreadCracker(Path filePath) throws IOException {
        List<UserHashRecord> records = FileUtils.loadHashes(filePath);
        this.hashToUsername = new HashMap<>();
        for (UserHashRecord userRecord : records) {
            this.hashToUsername.put(userRecord.md5Hash(), userRecord.username());
        }
    }

    @Override
    public Map<String, String> crack(int maxPasswordLength) {
        if (maxPasswordLength < 1 || maxPasswordLength > PasswordGenerator.getMaxLength()) {
            throw new IllegalArgumentException("Invalid password length");
        }

        long startTime = System.nanoTime();
        Map<String, String> result = new HashMap<>();

        for (int length = 1; length <= maxPasswordLength; length++) {
            PasswordGenerator generator = new PasswordGenerator(length);

            while (generator.hasNext() && !hashToUsername.isEmpty()) {
                String password = generator.nextPassword();
                if (password.length() != length) {
                    continue;
                }

                String md5Hash = HashUtils.hash(password);
                String username = hashToUsername.remove(md5Hash);
                if (username != null) {
                    result.put(username, password);
                }
            }
        }

        long endTime = System.nanoTime();
        LOGGER.info("Время выполнения: " + (endTime - startTime) / NANOS_TO_MILLIS + " мс");
        return result;
    }
}
