package edu.Project4test.transformations;

import edu.project4.model.Point;
import edu.project4.transformations.LinearTransformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;

class LinearTransformationTest {
    private static final double EPSILON = 1e-10;

    @Test
    @DisplayName("Тождественное преобразование")
    void identityTransformationShouldReturnSamePoint() {
        // a=1, b=0, c=0, d=0, e=1, f=0
        LinearTransformation identity = new LinearTransformation(1, 0, 0, 0, 1, 0);
        Point point = new Point(2.5, 3.5);

        Point result = identity.apply(point);

        assertThat(result.x()).isCloseTo(point.x(), offset(EPSILON));
        assertThat(result.y()).isCloseTo(point.y(), offset(EPSILON));
    }

    @ParameterizedTest
    @CsvSource({
        "1, 0, 5, 0, 1, -3, 2.0, 4.0, 7.0, 1.0",    // Сдвиг
        "2, 0, 0, 0, 3, 0, 1.5, 2.5, 3.0, 7.5",      // Масштабирование
        "0, 1, 0, 1, 0, 0, 2.0, 3.0, 3.0, 2.0",      // Обмен координат
        "1, 2, 3, 4, 5, 6, 1.0, 2.0, 8.0, 20.0"       // Комплексное преобразование
    })
    @DisplayName("Параметризованные тесты преобразований")
    void shouldTransformCorrectly(
        double a, double b, double c, double d, double e, double f,
        double x, double y, double expectedX, double expectedY
    ) {
        LinearTransformation transformation = new LinearTransformation(a, b, c, d, e, f);
        Point point = new Point(x, y);

        Point result = transformation.apply(point);

        assertThat(result.x())
            .as("X-координата")
            .isCloseTo(expectedX, offset(EPSILON));

        assertThat(result.y())
            .as("Y-координата")
            .isCloseTo(expectedY, offset(EPSILON));
    }

    @Test
    @DisplayName("Преобразование нулевой точки")
    void shouldHandleZeroPoint() {
        LinearTransformation transformation = new LinearTransformation(1, 2, 3, 4, 5, 6);
        Point zero = new Point(0, 0);

        Point result = transformation.apply(zero);

        assertThat(result.x()).isCloseTo(3.0, offset(EPSILON));
        assertThat(result.y()).isCloseTo(6.0, offset(EPSILON));
    }

    @Test
    @DisplayName("Комбинация преобразований")
    void shouldCombineTransformations() {
        LinearTransformation t1 = new LinearTransformation(1, 2, 3, 4, 5, 6);
        LinearTransformation t2 = new LinearTransformation(2, 0, 1, 0, 3, 2);
        Point point = new Point(1.0, 2.0);

        Point afterT1 = t1.apply(point);
        Point afterBoth = t2.apply(afterT1);

        // Ожидаемый результат: t2(t1(point))
        double expectedX = 2*(1*1 + 2*2 + 3) + 1;
        double expectedY = 3*(4*1 + 5*2 + 6) + 2;

        assertThat(afterBoth.x()).isCloseTo(expectedX, offset(EPSILON));
        assertThat(afterBoth.y()).isCloseTo(expectedY, offset(EPSILON));
    }

    @Test
    @DisplayName("Создание преобразования с отрицательными коэффициентами")
    void shouldWorkWithNegativeCoefficients() {
        LinearTransformation transformation = new LinearTransformation(-1, -2, -3, -4, -5, -6);
        Point point = new Point(2.0, 3.0);

        Point result = transformation.apply(point);

        assertThat(result.x()).isCloseTo(-1*2 - 2*3 - 3, offset(EPSILON));
        assertThat(result.y()).isCloseTo(-4*2 - 5*3 - 6, offset(EPSILON));
    }

    @Test
    @DisplayName("Детерминированность преобразования")
    void shouldBeDeterministic() {
        LinearTransformation transformation = new LinearTransformation(1.5, 2.5, 3.5, 4.5, 5.5, 6.5);
        Point point = new Point(1.0, 2.0);

        Point firstResult = transformation.apply(point);
        Point secondResult = transformation.apply(point);

        assertThat(firstResult).isEqualTo(secondResult);
    }
}
