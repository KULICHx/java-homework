package edu.Project4test.model;

import edu.project4.model.Point;
import edu.project4.model.Rect;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.util.Random;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Тестирование Rect")
class RectTest {

    private static final Rect TEST_RECT = new Rect(-2.0, -2.0, 4.0, 4.0);
    private static final double DELTA = 1e-6;

    @Test
    @DisplayName("Создание Rect с корректными параметрами")
    void shouldStoreValuesWhenCreatingRect() {
        Rect rect = new Rect(-1.5, 2.0, 3.0, 4.0);

        assertAll(
            () -> assertThat(rect.x()).isEqualTo(-1.5),
            () -> assertThat(rect.y()).isEqualTo(2.0),
            () -> assertThat(rect.width()).isEqualTo(3.0),
            () -> assertThat(rect.height()).isEqualTo(4.0)
        );
    }

    @ParameterizedTest
    @CsvSource({
        "-2.0, -2.0, true",    // Левый нижний угол
        "2.0, 2.0, true",       // Правый верхний угол
        "0.0, 0.0, true",       // Центр
        "-3.0, 0.0, false",     // Слева за границей
        "0.0, 3.0, false"       // Сверху за границей
    })
    @DisplayName("Проверка contains() для разных точек")
    void shouldCorrectlyDetectPointsWhenCheckingContains(double x, double y, boolean expected) {
        Point point = new Point(x, y);
        assertThat(TEST_RECT.contains(point)).isEqualTo(expected);
    }

    @Test
    @DisplayName("Преобразование в пиксельные координаты")
    void shouldConvertCorrectlyWhenConvertingToPixelCoords() {
        Point worldPoint = new Point(0.0, 0.0);
        Point pixelPoint = TEST_RECT.toPixelCoords(worldPoint, 800, 600);

        assertAll(
            () -> assertThat(pixelPoint.x()).isCloseTo(400.0, offset(DELTA)),
            () -> assertThat(pixelPoint.y()).isCloseTo(300.0, offset(DELTA))
        );
    }

    @Test
    @DisplayName("Обратное преобразование из пиксельных координат")
    void shouldConvertCorrectlyWhenConvertingFromPixelCoords() {
        Point pixelPoint = new Point(400, 300);
        Point worldPoint = TEST_RECT.fromPixelCoords(pixelPoint, 800, 600);

        assertAll(
            () -> assertThat(worldPoint.x()).isCloseTo(0.0, offset(DELTA)),
            () -> assertThat(worldPoint.y()).isCloseTo(0.0, offset(DELTA))
        );
    }

    @Test
    @DisplayName("Генерация случайной точки внутри Rect")
    void shouldGeneratePointWithinBoundsWhenGeneratingRandomPoint() {
        Random fixedRandom = new Random(12345);
        Point randomPoint = TEST_RECT.randomPoint(fixedRandom);

        assertAll(
            () -> assertThat(randomPoint.x()).isBetween(TEST_RECT.x(), TEST_RECT.x() + TEST_RECT.width()),
            () -> assertThat(randomPoint.y()).isBetween(TEST_RECT.y(), TEST_RECT.y() + TEST_RECT.height())
        );
    }

    @Test
    @DisplayName("Преобразование туда-обратно должно давать исходную точку")
    void shouldReturnOriginalPointWhenConvertingBackAndForth() {
        Point original = new Point(1.5, -0.5);
        Point pixel = TEST_RECT.toPixelCoords(original, 800, 600);
        Point world = TEST_RECT.fromPixelCoords(pixel, 800, 600);

        assertAll(
            () -> assertThat(world.x()).isCloseTo(original.x(), offset(DELTA)),
            () -> assertThat(world.y()).isCloseTo(original.y(), offset(DELTA))
        );
    }

    @Test
    @DisplayName("Преобразование Y-координаты: нижняя граница")
    void shouldConvertToBottomPixelWhenConvertingYBottom() {
        Point point = new Point(0, -2.0);  // Нижняя граница
        Point pixel = TEST_RECT.toPixelCoords(point, 800, 600);
        assertThat(pixel.y()).isCloseTo(600.0, offset(DELTA));
    }

    @Test
    @DisplayName("Преобразование Y-координаты: верхняя граница")
    void shouldConvertToTopPixelWhenConvertingYTop() {
        Point point = new Point(0, 2.0);  // Верхняя граница
        Point pixel = TEST_RECT.toPixelCoords(point, 800, 600);
        assertThat(pixel.y()).isCloseTo(0.0, offset(DELTA));
    }
}
