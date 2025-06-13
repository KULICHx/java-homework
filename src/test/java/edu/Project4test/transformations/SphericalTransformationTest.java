package edu.Project4test.transformations;

import edu.project4.model.Point;
import edu.project4.transformations.SphericalTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.within;

class SphericalTransformationTest {
    private static final double EPSILON = 1e-10;
    private final SphericalTransformation transformation = new SphericalTransformation();

    @ParameterizedTest
    @CsvSource({
        // x, y, expectedX, expectedY
        "0.0, 0.0, 0.0, 0.0",                     // Origin
        "1.0, 0.0, 1.0, 0.0",                     // On x-axis
        "0.0, 1.0, 0.0, 1.0",                     // On y-axis
        "1.0, 1.0, 0.5, 0.5",                     // Diagonal
        "2.0, 3.0, 0.1538461538, 0.2307692307",   // Arbitrary point
        "-1.0, -2.0, -0.2, -0.4",                // Negative coordinates
        "0.5, -0.5, 1.0, -1.0"                   // Small values
    })
    @DisplayName("Корректное преобразование точек")
    void shouldTransformPointsCorrectly(double x, double y, double expectedX, double expectedY) {
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
    @DisplayName("Двойное применение возвращает исходную точку")
    void apply_ShouldReturnSamePoint_WhenAppliedTwice() {
        Point point = new Point(0.3, -0.7);
        Point transformedOnce = transformation.apply(point);
        Point transformedTwice = transformation.apply(transformedOnce);

        assertThat(transformedTwice.x()).isCloseTo(point.x(), within(EPSILON));
        assertThat(transformedTwice.y()).isCloseTo(point.y(), within(EPSILON));
    }

    @Test
    @DisplayName("Обработка очень малых значений")
    void shouldHandleVerySmallValues() {
        Point smallValue = new Point(1e-10, 2e-10);
        Point result = transformation.apply(smallValue);

        // Проверяем что значения стали значительно больше (1/r^2 эффект)
        assertThat(result.x()).isEqualTo(2e9);
        assertThat(result.y()).isEqualTo(4e9);

        // Альтернативный вариант - проверка относительных величин
        assertThat(result.x()).isCloseTo(2e9, within(1.0));
        assertThat(result.y()).isCloseTo(4e9, within(1.0));
    }

    @Test
    @DisplayName("Обработка специальных значений (NaN и бесконечностей)")
    void shouldHandleSpecialCases() {
        assertThatThrownBy(() -> transformation.apply(new Point(Double.NaN, 0)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("NaN");

        assertThatThrownBy(() -> transformation.apply(new Point(0, Double.POSITIVE_INFINITY)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("infinite");

        assertThatThrownBy(() -> transformation.apply(new Point(Double.NEGATIVE_INFINITY, 0)))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("infinite");
    }

    @Test
    @DisplayName("Сохранение симметрии")
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
