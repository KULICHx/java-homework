package edu.Project4test.transformations;

import edu.project4.model.Point;
import edu.project4.transformations.HorseshoeTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

class HorseshoeTransformationTest {
    private final HorseshoeTransformation transformation = new HorseshoeTransformation();

    private static final double EPSILON = 1e-5;

    @ParameterizedTest
    @CsvSource({
        "1.0, 0.0, 1.0, 0.0",         // Ось X
        "0.0, 1.0, -1.0, 0.0",        // Ось Y
        "1.0, 1.0, 0.0, 1.414213562",  // Диагональ (√2)
        "-1.0, 1.0, 0.0, -1.414213562", // Отрицательная X
        "2.0, 3.0, -1.386750, 3.328201" // Произвольная точка
    })
    @DisplayName("Корректное преобразование точек")
    void shouldTransformPointsCorrectly(double x, double y, double expectedX, double expectedY) {
        Point point = new Point(x, y);
        Point transformed = transformation.apply(point);

        assertThat(transformed.x())
            .as("X-координата для точки (%f, %f)".formatted(x, y))
            .isCloseTo(expectedX, offset(EPSILON));

        assertThat(transformed.y())
            .as("Y-координата для точки (%f, %f)".formatted(x, y))
            .isCloseTo(expectedY, offset(EPSILON));
    }

    @Test
    @DisplayName("Должен сохранять расстояние от центра")
    void shouldPreserveDistanceFromOrigin() {
        Point point = new Point(2.0, 3.0);
        Point transformed = transformation.apply(point);

        double originalDist = Math.hypot(point.x(), point.y());
        double transformedDist = Math.hypot(transformed.x(), transformed.y());

        assertThat(transformedDist)
            .isCloseTo(originalDist, offset(EPSILON));
    }

    @Test
    @DisplayName("Должен выбрасывать исключение при точке в центре")
    void shouldThrowExceptionAtOrigin() {
        Point center = new Point(0.0, 0.0);

        assertThatThrownBy(() -> transformation.apply(center))
            .isInstanceOf(ArithmeticException.class)
            .hasMessageContaining("Деление на ноль");
    }

    @Test
    @DisplayName("Двойное применение должно сохранять расстояние")
    void shouldPreserveDistanceOnDoubleApply() {
        Point point = new Point(1.5, 2.5);
        double originalDistance = Math.hypot(point.x(), point.y());

        Point twiceTransformed = transformation.apply(transformation.apply(point));
        double newDistance = Math.hypot(twiceTransformed.x(), twiceTransformed.y());

        assertThat(newDistance)
            .isCloseTo(originalDistance, offset(EPSILON));
    }
}
