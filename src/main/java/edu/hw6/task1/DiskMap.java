package edu.hw6.task1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String> {

    private final Path filePath;
    private final Map<String, String> cache = new HashMap<>();

    public DiskMap(String filePath) throws IOException {
        this.filePath = Paths.get(filePath);

        if (Files.exists(this.filePath) && Files.isRegularFile(this.filePath)) {
            loadFromFile();
        } else {
            Files.createFile(this.filePath);
        }
    }

    @Override
    public int size() {
        return cache.size();
    }

    @Override
    public boolean isEmpty() {
        return cache.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return cache.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return cache.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return cache.get(key);
    }

    @Override
    public @Nullable String put(String key, String value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key and value cannot be null");
        }
        if (key.contains(":")) {
            throw new IllegalArgumentException("Key cannot contain ':' character");
        }

        String result = cache.put(key, value);
        saveToFile();
        return result;
    }

    @Override
    public String remove(Object key) {
        String result = cache.remove(key);
        saveToFile();
        return result;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        cache.putAll(m);
        saveToFile();
    }

    @Override
    public void clear() {
        cache.clear();
        try {
            Files.write(filePath, List.of(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public @NotNull Set<String> keySet() {
        return cache.keySet();
    }

    @Override
    public @NotNull Collection<String> values() {
        return cache.values();
    }

    @Override
    public @NotNull Set<Entry<String, String>> entrySet() {
        return cache.entrySet();
    }

    private void loadFromFile() throws IOException {
        List<String> lines = Files.readAllLines(filePath, StandardCharsets.UTF_8);

        for (String line : lines) {
            if (!line.contains(":")) {
                continue;
            }

            String[] parts = line.split(":", 2);
            if (parts.length == 2) {
                cache.put(parts[0], parts[1]);
            }
        }
    }

    private void saveToFile() {
        try {
            List<String> lines = cache.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue())
                .toList();

            Files.write(filePath, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new DiskMapException("Failed to save to file", e);
        }
    }

    public static class DiskMapException extends RuntimeException {
        public DiskMapException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
