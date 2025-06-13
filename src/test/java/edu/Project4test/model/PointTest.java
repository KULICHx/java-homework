package edu.Project4test.model;

import edu.project4.model.Point;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.data.Offset.offset;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Тестирование класса Point")
class PointTest {
    private static final double DELTA = 1e-8;
    private static final double PI = Math.PI;
    private static final double PI_2 = Math.PI/2;
    private static final double PI_4 = Math.PI/4;

    @Test
    @DisplayName("Создание точки с корректными координатами")
    void shouldStoreCoordinatesWhenCreatingPoint() {
        Point point = new Point(1.5, -2.3);

        assertAll(
            () -> assertThat(point.x()).isEqualTo(1.5),
            () -> assertThat(point.y()).isEqualTo(-2.3)
        );
    }

    @ParameterizedTest
    @CsvSource({
        "0.0, 0.0, 0.0",
        "1.0, 0.0, 1.5707963267948966",  // π/2
        "0.0, 1.0, 3.141592653589793",    // π
        "1.0, 1.0, 0.7853981633974483"    // π/4
    })
    @DisplayName("Поворот точки на заданный угол")
    void shouldCorrectlyRotatePointWhenRotating(double x, double y, double angle) {
        Point original = new Point(x, y);
        Point rotated = Point.rotate(original, angle);

        double originalDistance = Math.sqrt(x*x + y*y);
        double rotatedDistance = Math.sqrt(rotated.x()*rotated.x() + rotated.y()*rotated.y());

        assertThat(rotatedDistance).isCloseTo(originalDistance, offset(DELTA));
    }

    @Test
    @DisplayName("Поворот на 360 градусов возвращает исходную точку")
    void shouldReturnOriginalPointWhenRotatingFullCircle() {
        Point point = new Point(2.0, 3.0);
        Point rotated = Point.rotate(point, 2 * PI);

        assertAll(
            () -> assertThat(rotated.x()).isCloseTo(point.x(), offset(DELTA)),
            () -> assertThat(rotated.y()).isCloseTo(point.y(), offset(DELTA))
        );
    }

    @Test
    @DisplayName("Поворот на 90 градусов против часовой стрелки")
    void shouldWorkCorrectlyWhenRotating90Degrees() {
        Point point = new Point(1.0, 0.0);
        Point rotated = Point.rotate(point, PI_2);

        assertAll(
            () -> assertThat(rotated.x()).isCloseTo(0.0, offset(DELTA)),
            () -> assertThat(rotated.y()).isCloseTo(1.0, offset(DELTA))
        );
    }

    @Test
    @DisplayName("Последовательные повороты дают тот же результат, что и один поворот на сумму углов")
    void shouldBeAdditiveWhenRotating() {
        Point point = new Point(1.0, 0.0);
        double angle1 = PI_4;
        double angle2 = PI/3;

        Point rotatedOnce = Point.rotate(point, angle1 + angle2);
        Point rotatedTwice = Point.rotate(Point.rotate(point, angle1), angle2);

        assertAll(
            () -> assertThat(rotatedOnce.x()).isCloseTo(rotatedTwice.x(), offset(DELTA)),
            () -> assertThat(rotatedOnce.y()).isCloseTo(rotatedTwice.y(), offset(DELTA))
        );
    }

    @Test
    @DisplayName("Поворот нулевой точки не изменяет её")
    void shouldStayZeroWhenRotatingZeroPoint() {
        Point zero = new Point(0, 0);
        Point rotated = Point.rotate(zero, PI/3);

        assertAll(
            () -> assertThat(rotated.x()).isEqualTo(0.0),
            () -> assertThat(rotated.y()).isEqualTo(0.0)
        );
    }
}
