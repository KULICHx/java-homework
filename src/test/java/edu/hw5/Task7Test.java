package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Task7Test {

    @Test
    @DisplayName("Строка содержит минимум 3 символа с 0 на третьей позиции")
    public void shouldReturnTrueWhenValidBinaryWithThirdZero() {
        assertTrue(Task7.isValidBinaryWithThirdZero("110"));
        assertTrue(Task7.isValidBinaryWithThirdZero("000"));
        assertTrue(Task7.isValidBinaryWithThirdZero("1001"));
    }

    @Test
    @DisplayName("Строка не соответствует требованиям третьего символа")
    public void shouldReturnFalseWhenInvalidBinaryWithThirdZero() {
        assertFalse(Task7.isValidBinaryWithThirdZero(""));
        assertFalse(Task7.isValidBinaryWithThirdZero("10"));
        assertFalse(Task7.isValidBinaryWithThirdZero("111"));
    }

    @Test
    @DisplayName("Строка заканчивается первым символом")
    public void shouldReturnTrueWhenEndsWithFirstChar() {
        assertFalse(Task7.endsWithFirstChar("0"));
        assertFalse(Task7.endsWithFirstChar("1"));
        assertTrue(Task7.endsWithFirstChar("00"));
        assertTrue(Task7.endsWithFirstChar("101"));
    }

    @Test
    @DisplayName("Строка не заканчивается первым символом")
    public void shouldReturnFalseWhenNotEndsWithFirstChar() {
        assertFalse(Task7.endsWithFirstChar(""));
        assertFalse(Task7.endsWithFirstChar("01"));
        assertFalse(Task7.endsWithFirstChar("100"));
    }

    @Test
    @DisplayName("Длина строки от 1 до 3 символов")
    public void shouldReturnTrueWhenLengthValid() {
        assertTrue(Task7.isLengthValid("0"));
        assertTrue(Task7.isLengthValid("01"));
        assertTrue(Task7.isLengthValid("101"));
    }

    @Test
    @DisplayName("Длина строки не соответствует требованиям")
    public void shouldReturnFalseWhenLengthInvalid() {
        assertFalse(Task7.isLengthValid(""));
        assertFalse(Task7.isLengthValid("0110"));
        assertFalse(Task7.isLengthValid("a"));
    }
}
