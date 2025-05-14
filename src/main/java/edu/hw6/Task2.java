package edu.hw6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class Task2 {
    private Task2() {
    }

    private static final Logger LOGGER = Logger.getLogger(Task2.class.getName());
    private static final Pattern COPY_PATTERN = Pattern.compile("(.+) — копия(?: \\((\\d+)\\))?(\\..+)?");
    private static final String COPY_LABEL = " — копия";

    public static boolean cloneFile(Path path) {
        if (path == null || !Files.exists(path) || !Files.isRegularFile(path)) {
            return false;
        }

        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');
        String nameWithoutExt = (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
        String extension = (dotIndex == -1) ? "" : fileName.substring(dotIndex);

        int maxCopyNumber;
        try (Stream<Path> files = Files.list(path.getParent())) {
            maxCopyNumber = files
                .filter(p -> p.getFileName().toString().startsWith(nameWithoutExt + COPY_LABEL))
                .map(p -> p.getFileName().toString())
                .map(COPY_PATTERN::matcher)
                .filter(Matcher::matches)
                .mapToInt(m -> m.group(2) == null ? 1 : Integer.parseInt(m.group(2)))
                .max()
                .orElse(0);
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to list files", e);
            return false;
        }

        String newFileName = (maxCopyNumber == 0)
            ? nameWithoutExt + COPY_LABEL + extension
            : nameWithoutExt + COPY_LABEL + " (" + (maxCopyNumber + 1) + ")" + extension;

        Path clonePath = path.resolveSibling(newFileName);
        try {
            Files.copy(path, clonePath, StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (IOException e) {
            LOGGER.log(Level.SEVERE, "Failed to copy file", e);
            return false;
        }
    }
}
