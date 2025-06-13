package edu.Project4test.utils;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.utils.ImageFormat;
import edu.project4.utils.ImageUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ImageUtilsTest {
    @TempDir
    Path tempDir;

    @Test
    @DisplayName("Сохранение изображения в формате PNG")
    void shouldSaveImageToPng() throws Exception {
        // Given
        FractalImage image = createTestImage(100, 100);
        Path outputPath = tempDir.resolve("test.png");

        // When
        ImageUtils.save(image, outputPath, ImageFormat.PNG);

        // Then
        assertTrue(Files.exists(outputPath));
        BufferedImage savedImage = ImageIO.read(outputPath.toFile());
        assertThat(savedImage.getWidth()).isEqualTo(100);
        assertThat(savedImage.getHeight()).isEqualTo(100);
    }

    @ParameterizedTest
    @EnumSource(ImageFormat.class)
    @DisplayName("Сохранение изображения в разных форматах")
    void shouldSaveImageInAllFormats(ImageFormat format) throws Exception {
        // Given
        FractalImage image = createTestImage(50, 50);
        Path outputPath = tempDir.resolve("test." + format.name().toLowerCase());

        // When
        ImageUtils.save(image, outputPath, format);

        // Then
        assertTrue(Files.exists(outputPath));
    }

    @Test
    @DisplayName("Корректное преобразование цветов пикселей")
    void shouldCorrectlyConvertPixelColors() throws Exception {
        // Given
        FractalImage image = FractalImage.create(1, 1);
        image.setPixel(0, 0, new Pixel(255, 128, 0, 1));
        Path outputPath = tempDir.resolve("color_test.png");

        // When
        ImageUtils.save(image, outputPath, ImageFormat.PNG);

        // Then
        BufferedImage savedImage = ImageIO.read(outputPath.toFile());
        int rgb = savedImage.getRGB(0, 0);
        assertThat((rgb >> 16) & 0xFF).isEqualTo(255); // Red
        assertThat((rgb >> 8) & 0xFF).isEqualTo(128);  // Green
        assertThat(rgb & 0xFF).isEqualTo(0);           // Blue
    }

    @Test
    @DisplayName("Выброс исключения при нулевых аргументах")
    void shouldThrowWhenArgumentsAreNull() {
        FractalImage image = createTestImage(10, 10);
        Path validPath = tempDir.resolve("test.png");

        assertThatThrownBy(() -> ImageUtils.save(null, validPath, ImageFormat.PNG))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("не должны быть пустыми");

        assertThatThrownBy(() -> ImageUtils.save(image, null, ImageFormat.PNG))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("не должны быть пустыми");

        assertThatThrownBy(() -> ImageUtils.save(image, validPath, null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("не должны быть пустыми");
    }

    @Test
    @DisplayName("Выброс исключения при ошибке записи файла")
    void shouldThrowWhenCannotWriteFile() {
        // Given
        FractalImage image = createTestImage(10, 10);
        Path invalidPath = tempDir.resolve("nonexistent_directory/test.png");

        // When/Then
        assertThatThrownBy(() -> ImageUtils.save(image, invalidPath, ImageFormat.PNG))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Ошибка при сохранении изображения в файл")
            .hasCauseInstanceOf(IOException.class);
    }

    private FractalImage createTestImage(int width, int height) {
        FractalImage image = FractalImage.create(width, height);
        // Заполняем изображение тестовыми данными
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int r = (x * 255) / width;
                int g = (y * 255) / height;
                int b = (x + y) * 255 / (width + height);
                image.setPixel(x, y, new Pixel(r, g, b, 1));
            }
        }
        return image;
    }
}
