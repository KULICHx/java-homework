package edu.hw8.task3.util;

import edu.hw8.task3.model.UserHashRecord;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public final class FileUtils {
    private FileUtils() {
    }

    public static List<UserHashRecord> loadHashes(Path filePath) throws IOException {
        List<UserHashRecord> records = new ArrayList<>();

        for (String line : Files.readAllLines(filePath)) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                continue;
            }

            String[] parts = trimmed.split("\\s+", 2);
            if (parts.length == 2) {
                records.add(new UserHashRecord(parts[0], parts[1]));
            }
        }

        return records;
    }
}
