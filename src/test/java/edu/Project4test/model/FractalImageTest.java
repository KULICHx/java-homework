package edu.Project4test.model;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Тестирование FractalImage")
class FractalImageTest {

    @Test
    @DisplayName("Создание изображения: все пиксели должны быть нулевыми")
    void testCreateInitializesEmptyImage() {
        int width = 100;
        int height = 100;
        FractalImage image = FractalImage.create(width, height);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Pixel pixel = image.pixel(x, y);
                assertEquals(0, pixel.hitCount(), "Пиксель должен быть пустым после создания");
            }
        }
    }

    @Test
    @DisplayName("Установка и получение пикселя: значения должны сохраняться")
    void testSetAndGetPixel() {
        FractalImage image = FractalImage.create(100, 100);
        Pixel testPixel = new Pixel(255, 0, 0, 5);

        image.setPixel(10, 10, testPixel);
        Pixel retrievedPixel = image.pixel(10, 10);

        assertAll(
            () -> assertEquals(testPixel.r(), retrievedPixel.r(), "Красный канал"),
            () -> assertEquals(testPixel.g(), retrievedPixel.g(), "Зелёный канал"),
            () -> assertEquals(testPixel.b(), retrievedPixel.b(), "Синий канал"),
            () -> assertEquals(testPixel.hitCount(), retrievedPixel.hitCount(), "Количество попаданий")
        );
    }

    @Test
    @DisplayName("Граничные условия: выход за пределы изображения вызывает исключение")
    void testPixelOutOfBoundsThrowsException() {
        FractalImage image = FractalImage.create(100, 100);

        assertAll(
            () -> assertThrows(IndexOutOfBoundsException.class, () -> image.pixel(-1, 0)),
            () -> assertThrows(IndexOutOfBoundsException.class, () -> image.pixel(0, -1)),
            () -> assertThrows(IndexOutOfBoundsException.class, () -> image.pixel(100, 0)),
            () -> assertThrows(IndexOutOfBoundsException.class, () -> image.pixel(0, 100))
        );
    }
}
