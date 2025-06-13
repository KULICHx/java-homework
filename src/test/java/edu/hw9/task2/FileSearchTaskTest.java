package edu.hw9.task2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Predicate;
import static org.assertj.core.api.Assertions.assertThat;

class FileSearchTaskTest {

    @Test
    @DisplayName("Должен находить файлы по предикату (расширение и размер)")
    void shouldFindFilesByPredicate(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path targetFile1 = Files.createFile(tempDir.resolve("file1.pdf"));
        Path targetFile2 = Files.createFile(tempDir.resolve("file2.pdf"));
        Files.write(targetFile1, new byte[1024*1024+1]);
        Files.write(targetFile2, new byte[1024*1024+1]);
        Files.createFile(tempDir.resolve("small.pdf"));
        Files.createFile(tempDir.resolve("other.txt"));

        Predicate<Path> predicate = path -> {
            try {
                return path.toString().endsWith(".pdf") &&
                    Files.size(path) > 1024 * 1024;
            } catch (IOException e) {
                return false;
            }
        };

        // Act
        ForkJoinPool pool = new ForkJoinPool();
        FileSearchTask task = new FileSearchTask(tempDir, predicate);
        List<Path> result = pool.invoke(task);

        // Assert
        assertThat(result)
            .hasSize(2)
            .containsExactlyInAnyOrder(targetFile1, targetFile2);
    }

    @Test
    @DisplayName("Должен возвращать пустой список, если файлы не соответствуют предикату")
    void shouldReturnEmptyListWhenNoMatches(@TempDir Path tempDir) throws IOException {
        // Arrange
        Files.createFile(tempDir.resolve("file.txt"));
        Files.createFile(tempDir.resolve("small.pdf"));

        Predicate<Path> predicate = path -> path.toString().endsWith(".doc");

        // Act
        ForkJoinPool pool = new ForkJoinPool();
        FileSearchTask task = new FileSearchTask(tempDir, predicate);
        List<Path> result = pool.invoke(task);

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Должен рекурсивно искать в поддиректориях")
    void shouldSearchInSubdirectories(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path subDir = Files.createDirectory(tempDir.resolve("subdir"));
        Path targetFile = Files.createFile(subDir.resolve("target.pdf"));

        Predicate<Path> predicate = path -> path.toString().endsWith(".pdf");

        // Act
        ForkJoinPool pool = new ForkJoinPool();
        FileSearchTask task = new FileSearchTask(tempDir, predicate);
        List<Path> result = pool.invoke(task);

        // Assert
        assertThat(result)
            .hasSize(1)
            .containsExactly(targetFile);
    }
}
