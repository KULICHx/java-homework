package edu.hw9.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import static org.assertj.core.api.Assertions.assertThat;

class LargeDirectoryFinderTest {

    @Test
    @DisplayName("Должен находить директории с более чем 1000 файлов")
    void shouldFindLargeDirectories(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path largeDir = Files.createDirectory(tempDir.resolve("large_dir"));
        for (int i = 0; i < 1001; i++) {
            Files.createFile(largeDir.resolve("file_" + i + ".txt"));
        }

        Path smallDir = Files.createDirectory(tempDir.resolve("small_dir"));
        Files.createFile(smallDir.resolve("file.txt"));

        // Act
        ForkJoinPool pool = new ForkJoinPool();
        LargeDirectoryFinder task = new LargeDirectoryFinder(tempDir);
        List<Path> result = pool.invoke(task);

        // Assert
        assertThat(result)
            .hasSize(1)
            .containsExactly(largeDir);
    }

    @Test
    @DisplayName("Должен возвращать пустой список, когда нет больших директорий")
    void shouldReturnEmptyListWhenNoLargeDirectories(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path smallDir = Files.createDirectory(tempDir.resolve("small_dir"));
        Files.createFile(smallDir.resolve("file.txt"));

        // Act
        ForkJoinPool pool = new ForkJoinPool();
        LargeDirectoryFinder task = new LargeDirectoryFinder(tempDir);
        List<Path> result = pool.invoke(task);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Должен корректно обрабатывать ошибки доступа")
    void shouldHandleAccessErrors(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path inaccessibleDir = Files.createDirectory(tempDir.resolve("inaccessible"));
        Files.createFile(inaccessibleDir.resolve("file.txt"));
        inaccessibleDir.toFile().setReadable(false);

        // Act
        ForkJoinPool pool = new ForkJoinPool();
        LargeDirectoryFinder task = new LargeDirectoryFinder(tempDir);
        List<Path> result = pool.invoke(task);

        // Assert
        assertThat(result).isEmpty();
    }
}
