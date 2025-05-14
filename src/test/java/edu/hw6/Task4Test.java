package edu.hw6;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.CRC32;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {

    private static final String EXPECTED_TEXT =
        "Programming is learned by writing programs. ― Brian Kernighan\n";

    @Test
    @DisplayName("Создание файла с правильным содержимым")
    void createDecoratedFileWriter_ShouldCreateFileWithCorrectContent(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("test.txt");

        // Act
        Task4.createDecoratedFileWriter(testFile);

        // Assert
        assertThat(Files.exists(testFile)).isTrue();
        String fileContent = Files.readString(testFile, StandardCharsets.UTF_8);
        assertThat(fileContent).isEqualTo(EXPECTED_TEXT);
    }

    @Test
    @DisplayName("Проверка контрольной суммы")
    void createDecoratedFileWriter_ShouldCalculateChecksum(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("checksum_test.txt");
        CRC32 expectedChecksum = new CRC32();
        expectedChecksum.update(EXPECTED_TEXT.getBytes(StandardCharsets.UTF_8));
        long expectedValue = expectedChecksum.getValue();

        // Act
        Task4.createDecoratedFileWriter(testFile);

        // Assert
        byte[] fileBytes = Files.readAllBytes(testFile);
        CRC32 actualChecksum = new CRC32();
        actualChecksum.update(fileBytes);
        assertThat(actualChecksum.getValue()).isEqualTo(expectedValue);
    }

    @Test
    @DisplayName("Кодировка UTF-8")
    void createDecoratedFileWriter_ShouldUseUtf8Encoding(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("encoding_test.txt");

        // Act
        Task4.createDecoratedFileWriter(testFile);

        // Assert
        String fileContent = Files.readString(testFile, StandardCharsets.UTF_8);
        assertThat(fileContent).isEqualTo(EXPECTED_TEXT);
    }

    @Test
    @DisplayName("Обработка ошибок при создании файла")
    void createDecoratedFileWriter_ShouldHandleIOException() {
        // Arrange
        Path invalidPath = Path.of("/invalid/path/test.txt");

        // Act
        Task4.createDecoratedFileWriter(invalidPath);

        // Assert
        assertThat(Files.exists(invalidPath)).isFalse();
    }

    @Test
    @DisplayName("Цепочка потоков работает корректно")
    void createDecoratedFileWriter_ShouldUseAllStreams(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path testFile = tempDir.resolve("stream_test.txt");

        // Act
        Task4.createDecoratedFileWriter(testFile);

        // Assert
        String content = Files.readString(testFile);
        assertThat(content)
            .isEqualTo(EXPECTED_TEXT)
            .hasSize(EXPECTED_TEXT.length());
    }
}
