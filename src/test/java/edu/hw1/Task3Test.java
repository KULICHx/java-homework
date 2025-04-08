package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {

    @Test
    @DisplayName("Первый массив может быть вложен во второй (пример 1)")
    void isNestable_ShouldReturnTrueForNestableArrays1() {
        // Arrange
        int[] a1 = {1, 2, 3, 4};
        int[] a2 = {0, 6};

        // Act
        boolean result = Task3.isNestable(a1, a2);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Первый массив может быть вложен во второй (пример 2)")
    void isNestable_ShouldReturnTrueForNestableArrays2() {
        int[] a1 = {3, 1};
        int[] a2 = {4, 0};

        assertThat(Task3.isNestable(a1, a2)).isTrue();
    }

    @Test
    @DisplayName("Первый массив НЕ может быть вложен во второй (пример 3)")
    void isNestable_ShouldReturnFalseForNonNestableArrays1() {
        int[] a1 = {9, 9, 8};
        int[] a2 = {8, 9};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Первый массив НЕ может быть вложен во второй (пример 4)")
    void isNestable_ShouldReturnFalseForNonNestableArrays2() {
        int[] a1 = {1, 2, 3, 4};
        int[] a2 = {2, 3};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Пустой первый массив - возвращает false")
    void isNestable_EmptyFirstArray_ReturnsFalse() {
        int[] a1 = {};
        int[] a2 = {1, 2, 3};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Пустой второй массив - возвращает false")
    void isNestable_EmptySecondArray_ReturnsFalse() {
        int[] a1 = {1, 2, 3};
        int[] a2 = {};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Оба массива пустые - возвращает false")
    void isNestable_BothArraysEmpty_ReturnsFalse() {
        int[] a1 = {};
        int[] a2 = {};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Первый массив null - возвращает false")
    void isNestable_FirstArrayNull_ReturnsFalse() {
        int[] a1 = null;
        int[] a2 = {1, 2, 3};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Второй массив null - возвращает false")
    void isNestable_SecondArrayNull_ReturnsFalse() {
        int[] a1 = {1, 2, 3};
        int[] a2 = null;

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Оба массива null - возвращает false")
    void isNestable_BothArraysNull_ReturnsFalse() {
        int[] a1 = null;
        int[] a2 = null;

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Массивы с одинаковыми границами - возвращает false")
    void isNestable_ArraysWithSameBounds_ReturnsFalse() {
        int[] a1 = {2, 3};
        int[] a2 = {1, 2, 3, 4};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Первый массив с одним элементом - проверка вложенности")
    void isNestable_SingleElementFirstArray_ReturnsCorrectResult() {
        int[] a1 = {5};
        int[] a2 = {4, 6};

        assertThat(Task3.isNestable(a1, a2)).isTrue();
    }

    @Test
    @DisplayName("Второй массив с одним элементом - проверка вложенности")
    void isNestable_SingleElementSecondArray_ReturnsCorrectResult() {
        int[] a1 = {5, 6};
        int[] a2 = {4};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }

    @Test
    @DisplayName("Первый массив полностью внутри второго, но границы равны - возвращает false")
    void isNestable_ArraysWithEqualMinOrMax_ReturnsFalse() {
        int[] a1 = {1, 3};
        int[] a2 = {1, 4};

        assertThat(Task3.isNestable(a1, a2)).isFalse();
    }
}
