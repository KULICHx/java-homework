package edu.hw2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {

    static Stream<Task2.Rectangle> rectangles() {
        return Stream.of(
            new Task2.Rectangle(1, 1),
            new Task2.Square(1)
        );
    }

    @Test
    @DisplayName("Площадь прямоугольника после изменения ширины и высоты")
    void rectangleArea_ShouldBeCorrect() {
        // Arrange
        var rect = new Task2.Rectangle(1, 1);

        // Act
        var updated = rect.withWidth(20).withHeight(10);

        // Assert
        assertThat(updated.area()).isEqualTo(200.0);
    }

    @Test
    @DisplayName("Площадь квадрата после изменения стороны")
    void squareArea_ShouldBeCorrect() {
        // Arrange
        var square = new Task2.Square(1);

        // Act
        var updated = square.withWidth(6); // или withHeight(6)

        // Assert
        assertThat(updated.area()).isEqualTo(36.0);
    }


    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Метод withWidth должен возвращать новый прямоугольник с изменённой шириной")
    void withWidth_ShouldReturnUpdatedRectangle(Task2.Rectangle rect) {
        // Act
        var updated = rect.withWidth(15);

        // Assert
        assertThat(updated.getWidth()).isEqualTo(15);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Метод withHeight должен возвращать новый прямоугольник с изменённой высотой")
    void withHeight_ShouldReturnUpdatedRectangle(Task2.Rectangle rect) {
        // Act
        var updated = rect.withHeight(12);

        // Assert
        assertThat(updated.getHeight()).isEqualTo(12);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Исходный прямоугольник должен оставаться неизменным (иммутабельность)")
    void originalRectangleShouldRemainUnchanged(Task2.Rectangle rect) {
        // Arrange
        int originalWidth = rect.getWidth();
        int originalHeight = rect.getHeight();

        // Act
        rect.withWidth(100);
        rect.withHeight(200);

        // Assert
        assertThat(rect.getWidth()).isEqualTo(originalWidth);
        assertThat(rect.getHeight()).isEqualTo(originalHeight);
    }

    @ParameterizedTest
    @MethodSource("rectangles")
    @DisplayName("Площадь квадрата всегда должна быть стороной в квадрате")
    void squareArea_ShouldBeSideSquared(Task2.Rectangle rect) {
        // Act
        var square = new Task2.Square(6);

        // Assert
        assertThat(square.area()).isEqualTo(36.0);
    }
}

