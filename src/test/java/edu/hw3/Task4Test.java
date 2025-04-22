package edu.hw3;

import edu.hw3.task4.Task4;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task4Test {
    @Test
    @DisplayName("Конвертация 2 → II")
    void convert2() {
        // Arrange
        int input = 2;

        // Act
        String result = Task4.convertToRoman(input);

        // Assert
        assertThat(result).isEqualTo("II");
    }

    @Test
    @DisplayName("Конвертация 12 → XII")
    void convert12() {
        // Arrange
        int input = 12;

        // Act
        String result = Task4.convertToRoman(input);

        // Assert
        assertThat(result).isEqualTo("XII");
    }

    @Test
    @DisplayName("Конвертация 16 → XVI")
    void convert16() {
        // Arrange
        int input = 16;

        // Act
        String result = Task4.convertToRoman(input);

        // Assert
        assertThat(result).isEqualTo("XVI");
    }

    @Test
    @DisplayName("Конвертация 4 → IV")
    void convert4() {
        // Arrange
        int input = 4;

        // Act
        String result = Task4.convertToRoman(input);

        // Assert
        assertThat(result).isEqualTo("IV");
    }

    @Test
    @DisplayName("Конвертация 1987 → MCMLXXXVII")
    void convert1987() {
        // Arrange
        int input = 1987;

        // Act
        String result = Task4.convertToRoman(input);

        // Assert
        assertThat(result).isEqualTo("MCMLXXXVII");
    }

    @Test
    @DisplayName("Конвертация 3999 → MMMCMXCIX")
    void convert3999() {
        // Arrange
        int input = 3999;

        // Act
        String result = Task4.convertToRoman(input);

        // Assert
        assertThat(result).isEqualTo("MMMCMXCIX");
    }

    @Test
    @DisplayName("Ошибка при нулевом значении")
    void convert0() {
        // Arrange
        int input = 0;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(input));
    }

    @Test
    @DisplayName("Ошибка при отрицательном значении")
    void convertNegative() {
        // Arrange
        int input = -10;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(input));
    }

    @Test
    @DisplayName("Ошибка при значении > 3999")
    void convert4000() {
        // Arrange
        int input = 4000;

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> Task4.convertToRoman(input));
    }
}
