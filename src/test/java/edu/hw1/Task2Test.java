package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task2Test {

    @Test
    @DisplayName("Подсчёт цифр для нуля")
    void countDigits_Zero_ReturnsOne() {
        // Arrange
        int number = 0;

        // Act
        int result = Task2.countDigits(number);

        // Assert
        assertThat(result).isEqualTo(1);
    }

    @ParameterizedTest
    @DisplayName("Подсчёт цифр для положительных чисел")
    @CsvSource({
        "1, 1",
        "9, 1",
        "10, 2",
        "99, 2",
        "100, 3",
        "999, 3",
        "1000, 4",
        "4666, 4",
        "544, 3",
        "123456789, 9",
        "2147483647, 10"  // Integer.MAX_VALUE
    })
    void countDigits_PositiveNumbers_ReturnsCorrectCount(int number, int expected) {
        assertThat(Task2.countDigits(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Подсчёт цифр для отрицательных чисел")
    @CsvSource({
        "-1, 1",
        "-9, 1",
        "-10, 2",
        "-99, 2",
        "-100, 3",
        "-999, 3",
        "-1000, 4",
        "-4666, 4",
        "-544, 3",
        "-123456789, 9",
        "-2147483648, 10"  // Integer.MIN_VALUE
    })
    void countDigits_NegativeNumbers_ReturnsCorrectCount(int number, int expected) {
        assertThat(Task2.countDigits(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Подсчёт цифр для чисел с одной цифрой")
    @ValueSource(ints = {1, 2, 5, 7, 9})
    void countDigits_SingleDigitNumbers_ReturnsOne(int number) {
        assertThat(Task2.countDigits(number)).isEqualTo(1);
    }

    @ParameterizedTest
    @DisplayName("Подсчёт цифр для чисел с одной цифрой (отрицательные)")
    @ValueSource(ints = {-1, -3, -5, -7, -9})
    void countDigits_SingleDigitNegativeNumbers_ReturnsOne(int number) {
        assertThat(Task2.countDigits(number)).isEqualTo(1);
    }

    @Test
    @DisplayName("Подсчёт цифр для максимального int значения")
    void countDigits_MaxIntValue_ReturnsCorrectCount() {
        assertThat(Task2.countDigits(Integer.MAX_VALUE)).isEqualTo(10);
    }

    @Test
    @DisplayName("Подсчёт цифр для минимального int значения")
    void countDigits_MinIntValue_ReturnsCorrectCount() {
        assertThat(Task2.countDigits(Integer.MIN_VALUE)).isEqualTo(10);
    }
}
