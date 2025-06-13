package edu.Project4test.model;

import edu.project4.model.Pixel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Тестирование класса Pixel")
class PixelTest {

    @Test
    @DisplayName("Создание пикселя с корректными значениями")
    void shouldStoreValuesCorrectlyWhenCreatingPixel() {
        Pixel pixel = new Pixel(100, 150, 200, 5);

        assertAll(
            () -> assertThat(pixel.r()).isEqualTo(100),
            () -> assertThat(pixel.g()).isEqualTo(150),
            () -> assertThat(pixel.b()).isEqualTo(200),
            () -> assertThat(pixel.hitCount()).isEqualTo(5)
        );
    }

    @ParameterizedTest
    @CsvSource({
        "0, 0, 0, 0",    // Начальное состояние
        "100, 100, 100, 5", // Средние значения
        "255, 255, 255, 10" // Максимальные значения
    })
    @DisplayName("Обновление пикселя с разными начальными условиями")
    void shouldCorrectlyUpdateValuesWhenUpdatingPixel(int r, int g, int b, int hitCount) {
        Pixel initial = new Pixel(r, g, b, hitCount);
        Pixel updated = Pixel.updatePixel(initial, hitCount);

        assertAll(
            () -> assertThat(updated.r()).isBetween(0, 255),
            () -> assertThat(updated.g()).isBetween(0, 255),
            () -> assertThat(updated.b()).isBetween(0, 255),
            () -> assertThat(updated.hitCount()).isEqualTo(hitCount + 1),
            () -> assertThat(updated).isNotEqualTo(initial)
        );
    }

    @Test
    @DisplayName("Обновление пикселя должно учитывать фазовые сдвиги цветов")
    void shouldApplyColorPhaseShiftsWhenUpdatingPixel() {
        Pixel initial = new Pixel(0, 0, 0, 0);
        Pixel updated = Pixel.updatePixel(initial, 0);

        assertAll(
            () -> assertThat(updated.r()).isNotEqualTo(updated.g()),
            () -> assertThat(updated.g()).isNotEqualTo(updated.b()),
            () -> assertThat(updated.b()).isNotEqualTo(updated.r())
        );
    }

    @Test
    @DisplayName("Многократное обновление должно плавно изменять цвет")
    void shouldSmoothlyChangeColorWithMultipleUpdates() {
        Pixel pixel = new Pixel(0, 0, 0, 0);

        Pixel firstUpdate = Pixel.updatePixel(pixel, 0);
        Pixel secondUpdate = Pixel.updatePixel(firstUpdate, 1);

        assertAll(
            () -> assertThat(secondUpdate.r()).isNotEqualTo(firstUpdate.r()),
            () -> assertThat(secondUpdate.g()).isNotEqualTo(firstUpdate.g()),
            () -> assertThat(secondUpdate.b()).isNotEqualTo(firstUpdate.b()),
            () -> assertThat(secondUpdate.hitCount()).isEqualTo(2)
        );
    }

    @Test
    @DisplayName("При большом количестве hitCount изменения цвета становятся минимальными")
    void shouldStabilizeWhenUpdatingPixelWithLargeHitCount() {
        Pixel initial = new Pixel(100, 100, 100, 1000);
        Pixel updated = Pixel.updatePixel(initial, 1000);

        assertAll(
            () -> assertThat(updated.r()).isCloseTo(initial.r(), offset(5)),
            () -> assertThat(updated.g()).isCloseTo(initial.g(), offset(5)),
            () -> assertThat(updated.b()).isCloseTo(initial.b(), offset(5))
        );
    }

    @Test
    @DisplayName("Обновление пикселя должно сохранять значения в допустимом диапазоне")
    void shouldKeepValuesInValidRangeWhenUpdatingPixel() {
        Pixel extremePixel1 = new Pixel(300, -50, 1000, 5);
        Pixel updated1 = Pixel.updatePixel(extremePixel1, 5);

        assertAll(
            () -> assertThat(updated1.r()).isBetween(0, 255),
            () -> assertThat(updated1.g()).isBetween(0, 255),
            () -> assertThat(updated1.b()).isBetween(0, 255)
        );

        Pixel extremePixel2 = new Pixel(255, 255, 255, Integer.MAX_VALUE - 1);
        Pixel updated2 = Pixel.updatePixel(extremePixel2, Integer.MAX_VALUE - 1);

        assertAll(
            () -> assertThat(updated2.r()).isBetween(0, 255),
            () -> assertThat(updated2.g()).isBetween(0, 255),
            () -> assertThat(updated2.b()).isBetween(0, 255)
        );
    }
}
