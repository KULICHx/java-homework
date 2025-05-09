package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class Task8Test {

    @Test
    @DisplayName("Проверка нечетной длины строки")
    void isOddLengthBinaryTest() {
        assertTrue(Task8.isOddLengthBinary("1"));
        assertTrue(Task8.isOddLengthBinary("101"));
        assertTrue(Task8.isOddLengthBinary("11111"));

        assertFalse(Task8.isOddLengthBinary(""));
        assertFalse(Task8.isOddLengthBinary("10"));
        assertFalse(Task8.isOddLengthBinary("1100"));
    }

    @Test
    @DisplayName("Проверка паттерна: начинается с 0 и нечетная длина или с 1 и четная")
    void matchesZeroOddOrOneEvenPatternTest() {
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("0"));
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("000"));
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("01010"));
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("01101"));

        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("10"));
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("1111"));
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("100001"));
        assertTrue(Task8.matchesZeroOddOrOneEvenPattern("101010"));

        assertFalse(Task8.matchesZeroOddOrOneEvenPattern(""));    // пустая строка
        assertFalse(Task8.matchesZeroOddOrOneEvenPattern("1"));
    }

    @Test
    @DisplayName("Проверка количества 0 кратно 3")
    void hasZeroCountMultipleOfThreeTest() {

        assertTrue(Task8.hasZeroCountMultipleOfThree("000"));
        assertTrue(Task8.hasZeroCountMultipleOfThree("101001"));
        assertTrue(Task8.hasZeroCountMultipleOfThree("100010010"));
        assertTrue(Task8.hasZeroCountMultipleOfThree("010101000"));
        assertTrue(Task8.hasZeroCountMultipleOfThree("000111000"));

        assertFalse(Task8.hasZeroCountMultipleOfThree("0"));
        assertFalse(Task8.hasZeroCountMultipleOfThree("00"));
        assertFalse(Task8.hasZeroCountMultipleOfThree("1001"));
        assertFalse(Task8.hasZeroCountMultipleOfThree("0110"));
        assertFalse(Task8.hasZeroCountMultipleOfThree("00100"));
        assertFalse(Task8.hasZeroCountMultipleOfThree("00010"));
    }

    @Test
    @DisplayName("Проверка что строка не 11 или 111")
    void isNotElevenOrOneElevenTest() {
        assertTrue(Task8.isNotElevenOrOneEleven(""));
        assertTrue(Task8.isNotElevenOrOneEleven("0"));
        assertTrue(Task8.isNotElevenOrOneEleven("101"));
        assertTrue(Task8.isNotElevenOrOneEleven("1111"));

        assertFalse(Task8.isNotElevenOrOneEleven("11"));
        assertFalse(Task8.isNotElevenOrOneEleven("111"));
    }

    @Test
    @DisplayName("Проверка что каждый нечетный символ равен 1")
    void hasOnesAtOddPositionsTest() {
        assertTrue(Task8.hasOnesAtOddPositions("1"));
        assertTrue(Task8.hasOnesAtOddPositions("10"));
        assertTrue(Task8.hasOnesAtOddPositions("101"));
        assertTrue(Task8.hasOnesAtOddPositions("1010"));

        assertFalse(Task8.hasOnesAtOddPositions("0"));
        assertFalse(Task8.hasOnesAtOddPositions("01"));
        assertFalse(Task8.hasOnesAtOddPositions("100"));
        assertFalse(Task8.hasOnesAtOddPositions("110"));
    }

    @Test
    @DisplayName("Проверка наличия ≥2 нулей и ≤1 единицы")
    void hasTwoOrMoreZerosAndAtMostOneOneTest() {
        assertTrue(Task8.hasTwoOrMoreZerosAndAtMostOneOne("00"));
        assertTrue(Task8.hasTwoOrMoreZerosAndAtMostOneOne("001"));
        assertTrue(Task8.hasTwoOrMoreZerosAndAtMostOneOne("010"));
        assertTrue(Task8.hasTwoOrMoreZerosAndAtMostOneOne("000"));

        assertFalse(Task8.hasTwoOrMoreZerosAndAtMostOneOne("0"));
        assertFalse(Task8.hasTwoOrMoreZerosAndAtMostOneOne("11"));
    }

    @Test
    @DisplayName("Проверка отсутствия последовательных 1")
    void hasNoConsecutiveOnesTest() {
        assertTrue(Task8.hasNoConsecutiveOnes("0"));
        assertTrue(Task8.hasNoConsecutiveOnes("1"));
        assertTrue(Task8.hasNoConsecutiveOnes("010"));
        assertTrue(Task8.hasNoConsecutiveOnes("10101"));
        assertTrue(Task8.hasNoConsecutiveOnes("000"));

        assertFalse(Task8.hasNoConsecutiveOnes("11"));
        assertFalse(Task8.hasNoConsecutiveOnes("0110"));
        assertFalse(Task8.hasNoConsecutiveOnes("110"));
        assertFalse(Task8.hasNoConsecutiveOnes("1011"));
    }
}
