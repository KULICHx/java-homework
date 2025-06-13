package edu.Project4test.transformations;

import edu.project4.model.Point;
import edu.project4.transformations.SinusoidalTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import static java.lang.Math.*;

class SinusoidalTransformationTest {
    private static final double EPSILON = 1e-8; // Увеличили допустимую погрешность
    private final SinusoidalTransformation transformation = new SinusoidalTransformation();

    @ParameterizedTest
    @CsvSource({
        // x, y, expectedX, expectedY
        "0.0, 0.0, 0.0, 0.0",                     // Нулевая точка
        "1.5707963267948966, 1.5707963267948966, 1.0, 1.0",          // π/2
        "3.141592653589793, 3.141592653589793, 0.0, 0.0",            // π
        "-1.5707963267948966, -1.5707963267948966, -1.0, -1.0",      // -π/2
        "0.5235987755982988, 0.7853981633974483, 0.5, 0.7071067811865475" // π/6, π/4
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
    @DisplayName("Граничные случаи: очень большие значения")
    void shouldHandleExtremeValues() {
        Point largeValue = new Point(1e10, -1e10);
        Point result = transformation.apply(largeValue);

        // Синус периодичен, результат должен быть в [-1, 1]
        assertThat(result.x()).isBetween(-1.0, 1.0);
        assertThat(result.y()).isBetween(-1.0, 1.0);
    }

    @Test
    @DisplayName("Свойство периодичности")
    void shouldBePeriodic() {
        Point p1 = new Point(0.5, 1.5);
        Point p2 = new Point(0.5 + 2 * PI, 1.5 + 2 * PI);

        Point r1 = transformation.apply(p1);
        Point r2 = transformation.apply(p2);

        assertThat(r1.x()).isCloseTo(r2.x(), offset(EPSILON));
        assertThat(r1.y()).isCloseTo(r2.y(), offset(EPSILON));
    }

    @Test
    @DisplayName("Сохранение симметрии")
    void shouldPreserveSymmetry() {
        Point positive = new Point(0.5, 0.8);
        Point negative = new Point(-0.5, -0.8);

        Point posResult = transformation.apply(positive);
        Point negResult = transformation.apply(negative);

        // sin(-x) = -sin(x)
        assertThat(negResult.x()).isCloseTo(-posResult.x(), offset(EPSILON));
        assertThat(negResult.y()).isCloseTo(-posResult.y(), offset(EPSILON));
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

        assertThatThrownBy(() -> transformation.apply(new Point(Double.NaN, Double.NaN)))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
