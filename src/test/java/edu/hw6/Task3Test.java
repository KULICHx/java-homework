package edu.hw6;

import edu.hw6.task3.AbstractFilter;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import static edu.hw6.task3.AbstractFilter.*;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {
    @TempDir
    Path tempDir;

    @Test
    void testREGULAR_FILEFilter() throws IOException {
        Path file = Files.createFile(tempDir.resolve("test.txt"));
        Path dir = Files.createDirectory(tempDir.resolve("subdir"));

        assertThat(REGULAR_FILE.accept(file)).isTrue();
        assertThat(REGULAR_FILE.accept(dir)).isFalse();
    }

    @Test
    void testREADABLEFilter() throws IOException {
        Path file = Files.createFile(tempDir.resolve("readable.txt"));
        assertThat(READABLE.accept(file)).isTrue();
    }

    @Test
    void testLargerThanFilter() throws IOException {
        Path smallFile = Files.createFile(tempDir.resolve("small.txt"));
        Files.write(smallFile, "small content".getBytes());

        Path largeFile = Files.createFile(tempDir.resolve("large.txt"));
        Files.write(largeFile, new byte[100_001]);

        AbstractFilter filter = largerThan(100_000);
        assertThat(filter.accept(smallFile)).isFalse();
        assertThat(filter.accept(largeFile)).isTrue();
    }

    @Test
    void testMagicNumberFilter() throws IOException {
        Path pngFile = Files.createFile(tempDir.resolve("test.png"));
        Files.write(pngFile, new byte[] {(byte) 0x89, 'P', 'N', 'G'});

        Path txtFile = Files.createFile(tempDir.resolve("test.txt"));
        Files.write(txtFile, "text".getBytes());

        AbstractFilter pngFilter = magicNumber(0x89, 'P', 'N', 'G');
        assertThat(pngFilter.accept(pngFile)).isTrue();
        assertThat(pngFilter.accept(txtFile)).isFalse();
    }

    @Test
    void testGlobMatchesFilter() throws IOException {
        Path pngFile = Files.createFile(tempDir.resolve("image.png"));
        Path txtFile = Files.createFile(tempDir.resolve("document.txt"));

        AbstractFilter filter = globMatches("*.png");
        assertThat(filter.accept(pngFile)).isTrue();
        assertThat(filter.accept(txtFile)).isFalse();
    }

    @Test
    void testRegexContainsFilter() throws IOException {
        Path file1 = Files.createFile(tempDir.resolve("file-1.txt"));
        Path file2 = Files.createFile(tempDir.resolve("file2.txt"));

        AbstractFilter filter = regexContains("[-]");
        assertThat(filter.accept(file1)).isTrue();
        assertThat(filter.accept(file2)).isFalse();
    }

    @Test
    @DisplayName("Проверка цепочки фильтров")
    void testChainedFilters() throws IOException {
        // Arrange
        Path validPng = Files.createTempFile("test", ".png");
        try (OutputStream os = Files.newOutputStream(validPng)) {
            os.write(new byte[] {(byte) 0x89, 'P', 'N', 'G'});
            os.write(new byte[100_000]);
        }

        Path invalidFile = Files.createTempFile("test", ".txt");
        Files.write(invalidFile, "test".getBytes());

        AbstractFilter filter = REGULAR_FILE
            .and(READABLE)
            .and(largerThan(100_000))
            .and(magicNumber(0x89, 'P', 'N', 'G'))
            .and(globMatches("*.png"))
            .and(regexContains("test"));

        assertThat(filter.accept(validPng))
            .as("PNG файл должен соответствовать всем критериям")
            .isTrue();

        assertThat(filter.accept(invalidFile))
            .as("Текстовый файл не должен проходить фильтрацию")
            .isFalse();

        AbstractFilter partialFilter = REGULAR_FILE.and(READABLE);
        assertThat(partialFilter.accept(validPng))
            .as("Файл должен проходить базовые проверки")
            .isTrue();
    }
}
