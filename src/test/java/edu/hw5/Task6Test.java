package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Task6Test {

    @Test
    @DisplayName("Пустая строка s является подпоследовательностью любой строки t")
    public void shouldReturnTrueForEmptyS() {
        // Arrange
        String s = "";
        String t = "any string";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Пустая строка s является подпоследовательностью пустой строки t")
    public void shouldReturnTrueForEmptyTIfSIsEmpty() {
        // Arrange
        String s = "";
        String t = "";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Непустая строка s не является подпоследовательностью пустой строки t")
    public void shouldReturnFalseForNonEmptySAndEmptyT() {
        // Arrange
        String s = "abc";
        String t = "";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Корректная подпоследовательность")
    public void shouldReturnTrueForValidSubsequence() {
        // Arrange
        String s = "abc";
        String t = "ahbgdc";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Некорректная подпоследовательность")
    public void shouldReturnFalseForInvalidSubsequence() {
        // Arrange
        String s = "axc";
        String t = "ahbgdc";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertFalse(result);
    }

    @Test
    @DisplayName("Полное совпадение строк")
    public void shouldReturnTrueForExactMatch() {
        // Arrange
        String s = "hello";
        String t = "hello";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Подпоследовательность с повторяющимися символами")
    public void shouldReturnTrueForSubsequenceWithRepeatedChars() {
        // Arrange
        String s = "aabb";
        String t = "caaabbb";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertTrue(result);
    }

    @Test
    @DisplayName("Проверка порядка символов")
    public void shouldReturnFalseIfOrderIsWrong() {
        // Arrange
        String s = "bac";
        String t = "abc";

        // Act
        boolean result = Task6.isSubsequenceRegex(s, t);

        // Assert
        assertFalse(result);
    }

}
