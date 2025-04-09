package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task1Test {

    @Test
    @DisplayName("Корректное время: 01:00 -> 60 секунд")
    void minutesToSeconds_ValidInput_ReturnsCorrectResult1() {
        // Arrange
        String input = "01:00";

        // Act
        int result = Task1.minutesToSeconds(input);

        // Assert
        assertThat(result).isEqualTo(60);
    }

    @Test
    @DisplayName("Корректное время: 13:56 -> 836 секунд")
    void minutesToSeconds_ValidInput_ReturnsCorrectResult2() {
        assertThat(Task1.minutesToSeconds("13:56")).isEqualTo(836);
    }

    @Test
    @DisplayName("Некорректные секунды: 10:60 -> -1")
    void minutesToSeconds_InvalidSeconds_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("10:60")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Граничное значение: 999:59 -> 59999 секунд")
    void minutesToSeconds_LargeMinutes_ReturnsCorrectResult() {
        assertThat(Task1.minutesToSeconds("999:59")).isEqualTo(59999);
    }

    @Test
    @DisplayName("Нулевое время: 00:00 -> 0 секунд")
    void minutesToSeconds_ZeroTime_ReturnsZero() {
        assertThat(Task1.minutesToSeconds("00:00")).isEqualTo(0);
    }

    @Test
    @DisplayName("Отрицательные минуты: -01:30 -> -1")
    void minutesToSeconds_NegativeMinutes_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("-01:30")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Отрицательные секунды: 01:-30 -> -1")
    void minutesToSeconds_NegativeSeconds_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("01:-30")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Некорректный формат (отсутствует двоеточие) -> -1")
    void minutesToSeconds_NoColon_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("0130")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Некорректный формат (много двоеточий) -> -1")
    void minutesToSeconds_MultipleColons_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("01:30:45")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Пустая строка -> -1")
    void minutesToSeconds_EmptyString_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Null вместо строки -> -1")
    void minutesToSeconds_NullInput_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds(null)).isEqualTo(-1);
    }

    @Test
    @DisplayName("Некорректные символы вместо чисел -> -1")
    void minutesToSeconds_NonNumericInput_ReturnsMinus1() {
        assertThat(Task1.minutesToSeconds("ab:cd")).isEqualTo(-1);
    }

    @Test
    @DisplayName("Корректное время с ведущими нулями: 001:005 -> 65 секунд")
    void minutesToSeconds_LeadingZeros_ReturnsCorrectResult() {
        assertThat(Task1.minutesToSeconds("001:005")).isEqualTo(65);
    }
}
