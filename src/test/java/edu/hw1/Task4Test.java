package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import static org.assertj.core.api.Assertions.assertThat;

class Task4Test {

    @ParameterizedTest
    @DisplayName("Корректное исправление строк с четным количеством символов")
    @CsvSource({
        "123456, 214365",
        "hTsii  s aimex dpus rtni.g, This is a mixed up string.",
        "оПомигети псаривьтс ртко!и, Помогите исправить строки!",
        "ABCDEF, BADCFE"
    })
    void fixString_EvenLengthStrings_ReturnsFixedString(String input, String expected) {
        assertThat(Task4.fixString(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Корректное исправление строк с нечетным количеством символов")
    @CsvSource({
        "badce, abcde",
        "x, x",
        "abc, bac",
        "hello, ehllo",
        "12345, 21435"
    })
    void fixString_OddLengthStrings_ReturnsFixedString(String input, String expected) {
        assertThat(Task4.fixString(input)).isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Краевые случаи: пустая строка и null")
    @NullAndEmptySource
    void fixString_NullAndEmpty_ReturnsSame(String input) {
        assertThat(Task4.fixString(input)).isEqualTo(input);
    }

    @Test
    @DisplayName("Строка из одного символа возвращается без изменений")
    void fixString_SingleCharacter_ReturnsSame() {
        String input = "a";
        assertThat(Task4.fixString(input)).isEqualTo(input);
    }

    @Test
    @DisplayName("Строка из двух символов правильно меняет их местами")
    void fixString_TwoCharacters_ReturnsSwapped() {
        assertThat(Task4.fixString("ab")).isEqualTo("ba");
    }

}
