package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    @Test
    @DisplayName("Копирование файла без копий")
    void cloneFile_ShouldCreateFirstCopy(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path originalFile = tempDir.resolve("test.txt");
        Files.write(originalFile, "Hello".getBytes());

        // Act
        boolean result = Task2.cloneFile(originalFile);

        // Assert
        assertThat(result).isTrue();
        assertThat(tempDir.resolve("test — копия.txt")).exists();
    }

    @Test
    @DisplayName("Копирование файла с существующими копиями")
    void cloneFile_ShouldIncrementCopyNumber(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path originalFile = tempDir.resolve("document.pdf");
        Files.write(originalFile, "PDF content".getBytes());

        // Создаём несколько копий вручную
        Files.copy(originalFile, tempDir.resolve("document — копия.pdf"));
        Files.copy(originalFile, tempDir.resolve("document — копия (2).pdf"));

        // Act
        boolean result = Task2.cloneFile(originalFile);

        // Assert
        assertThat(result).isTrue();
        assertThat(tempDir.resolve("document — копия (3).pdf")).exists();
    }

    @Test
    @DisplayName("Копирование файла без расширения")
    void cloneFile_ShouldHandleFilesWithoutExtension(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path originalFile = tempDir.resolve("data");
        Files.write(originalFile, "Binary data".getBytes());

        // Act
        boolean result = Task2.cloneFile(originalFile);

        // Assert
        assertThat(result).isTrue();
        assertThat(tempDir.resolve("data — копия")).exists();
    }

    @Test
    @DisplayName("Попытка копирования несуществующего файла")
    void cloneFile_ShouldReturnFalseForNonExistentFile(@TempDir Path tempDir) {
        // Arrange
        Path nonExistentFile = tempDir.resolve("ghost.txt");

        // Act
        boolean result = Task2.cloneFile(nonExistentFile);

        // Assert
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Копирование файла с точкой в имени")
    void cloneFile_ShouldHandleFilesWithMultipleDots(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path originalFile = tempDir.resolve("archive.tar.gz");
        Files.write(originalFile, "Compressed data".getBytes());

        // Act
        boolean result = Task2.cloneFile(originalFile);

        // Assert
        assertThat(result).isTrue();
        assertThat(tempDir.resolve("archive.tar — копия.gz")).exists();
    }
}
