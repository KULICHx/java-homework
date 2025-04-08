package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {

    @ParameterizedTest
    @DisplayName("Числа с палиндромными потомками (примеры из условия)")
    @CsvSource({
        "11211230, true",    // 11211230 -> 2333 -> 56 -> 11
        "13001120, true",    // 13001120 -> 4022 -> 44
        "23336014, true",    // 23336014 -> 5665
        "11, true"           // 11 (уже палиндром)
    })
    void isPalindromeDescendant_WithPalindromeDescendants_ReturnsTrue(int number, boolean expected) {
        assertThat(Task5.isPalindromeDescendant(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Числа без палиндромных потомков")
    @ValueSource(ints = {123456, 1234, 12345, 12345678})
    void isPalindromeDescendant_WithoutPalindromeDescendants_ReturnsFalse(int number) {
        assertThat(Task5.isPalindromeDescendant(number)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("Односимвольные числа (не могут иметь потомков)")
    @ValueSource(ints = {0, 1, 5, 9})
    void isPalindromeDescendant_SingleDigitNumbers_ReturnsFalse(int number) {
        assertThat(Task5.isPalindromeDescendant(number)).isFalse();
    }

    @ParameterizedTest
    @DisplayName("Отрицательные числа (должны обрабатываться корректно)")
    @CsvSource({
        "-11211230, true",
        "-23336014, true",
        "-123456, false"
    })
    void isPalindromeDescendant_NegativeNumbers_ReturnsCorrectResult(int number, boolean expected) {
        assertThat(Task5.isPalindromeDescendant(number)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Числа, которые сами являются палиндромами")
    @ValueSource(ints = {121, 1331, 12321, 11, 99, 55555})
    void isPalindromeDescendant_DirectPalindromes_ReturnsTrue(int number) {
        assertThat(Task5.isPalindromeDescendant(number)).isTrue();
    }

    @Test
    @DisplayName("Число с нечетным количеством цифр при генерации потомка")
    void isPalindromeDescendant_OddLengthNumber_ReturnsCorrectResult() {
        assertThat(Task5.isPalindromeDescendant(123)).isFalse();  // 123 -> 35 (не палиндром)
        assertThat(Task5.isPalindromeDescendant(12345)).isFalse(); // 12345 -> 357 -> 812 (не палиндром)
    }

    @Test
    @DisplayName("Граничные случаи с минимальными числами")
    void isPalindromeDescendant_BoundaryCases_ReturnsCorrectResult() {
        assertThat(Task5.isPalindromeDescendant(10)).isFalse();  // 10 -> 1 (не палиндром)
        assertThat(Task5.isPalindromeDescendant(12)).isFalse();   // 12 -> 3 (не палиндром)
        assertThat(Task5.isPalindromeDescendant(22)).isTrue();    // 22 (палиндром)
        assertThat(Task5.isPalindromeDescendant(21)).isFalse();   // 21 -> 3 (не палиндром)
    }

    @Test
    @DisplayName("Сложные случаи с несколькими поколениями потомков")
    void isPalindromeDescendant_MultipleGenerations_ReturnsCorrectResult() {
        assertThat(Task5.isPalindromeDescendant(11211230)).isTrue();  // 11211230 -> 2333 -> 56 -> 11
        assertThat(Task5.isPalindromeDescendant(1234554321)).isTrue(); // 1234554321 (уже палиндром)
        assertThat(Task5.isPalindromeDescendant(1234554329)).isFalse(); // 1234554329 -> 35799751 -> 8161612 -> 97773 -> 16444 -> 7108 -> 819 -> 99
    }
}
