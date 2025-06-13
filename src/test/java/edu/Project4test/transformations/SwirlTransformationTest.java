package edu.Project4test.transformations;

import edu.project4.model.Point;
import edu.project4.transformations.SwirlTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;

class SwirlTransformationTest {
    private static final double EPSILON = 1e-10;
    private final SwirlTransformation transformation = new SwirlTransformation();

    @ParameterizedTest
    @CsvSource({
        // x, y
        "0.0, 0.0",    // Origin
        "1.0, 0.0",    // On x-axis
        "0.0, 1.0",    // On y-axis
        "1.0, 1.0",    // Diagonal
        "-1.0, -2.0",  // Negative coordinates
        "0.5, -0.3"    // Mixed coordinates
    })
    @DisplayName("Преобразование сохраняет расстояние от центра")
    void shouldPreserveRadius(double x, double y) {
        Point point = new Point(x, y);
        Point transformed = transformation.apply(point);

        double originalDist = x * x + y * y;
        double transformedDist = transformed.x() * transformed.x() + transformed.y() * transformed.y();

        assertThat(transformedDist)
            .as("Расстояние для точки (%f, %f)".formatted(x, y))
            .isCloseTo(originalDist, within(EPSILON));
    }

    @ParameterizedTest
    @CsvSource({
        // x, y, expectedX, expectedY
        "1.0, 0.0, 0.8414709848, 0.5403023059",  // sin(1)≈0.8415, cos(1)≈0.5403
        "0.0, 1.0, -0.5403023059, 0.8414709848", // sin(1)≈0.8415, cos(1)≈0.5403
        "2.0, 0.0, -1.5136049906, -1.3072872418" // sin(4)≈-0.7568, cos(4)≈-0.6536
    })
    @DisplayName("Корректное преобразование координат")
    void shouldTransformCoordinatesCorrectly(double x, double y, double expectedX, double expectedY) {
        Point point = new Point(x, y);
        Point transformed = transformation.apply(point);

        assertThat(transformed.x())
            .as("X-координата для точки (%f, %f)".formatted(x, y))
            .isCloseTo(expectedX, within(EPSILON));

        assertThat(transformed.y())
            .as("Y-координата для точки (%f, %f)".formatted(x, y))
            .isCloseTo(expectedY, within(EPSILON));
    }

    @Test
    @DisplayName("Преобразование в начале координат даёт нулевую точку")
    void shouldReturnOriginForOrigin() {
        Point origin = new Point(0, 0);
        Point transformed = transformation.apply(origin);

        assertThat(transformed.x()).isZero();
        assertThat(transformed.y()).isZero();
    }

    @Test
    @DisplayName("Обработка очень больших значений (должно работать, но результат непредсказуем)")
    void shouldHandleExtremeValues() {
        Point largePoint = new Point(1e6, 1e6);
        Point result = transformation.apply(largePoint);

        // Просто проверяем, что не получили NaN или бесконечность
        assertThat(result.x()).isFinite();
        assertThat(result.y()).isFinite();
    }

    @Test
    @DisplayName("Обработка специальных значений (NaN и бесконечностей)")
    void shouldHandleSpecialCases() {
        assertThatThrownBy(() -> transformation.apply(new Point(Double.NaN, 0)))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> transformation.apply(new Point(0, Double.POSITIVE_INFINITY)))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> transformation.apply(new Point(Double.NEGATIVE_INFINITY, 0)))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    @DisplayName("Сохранение симметрии для отрицательных координат")
    void shouldPreserveSymmetry() {
        Point positive = new Point(0.5, 0.8);
        Point negative = new Point(-0.5, -0.8);

        Point posResult = transformation.apply(positive);
        Point negResult = transformation.apply(negative);

        // Проверяем симметрию преобразования
        assertThat(negResult.x()).isCloseTo(-posResult.x(), within(EPSILON));
        assertThat(negResult.y()).isCloseTo(-posResult.y(), within(EPSILON));
    }
}
