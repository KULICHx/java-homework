package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class Task6Test {

    @Test
    @DisplayName("Число 3524 требует 3 шага для получения 6174")
    void countK_ValidInput_ReturnsCorrectStepCountFor3524() {
        // Arrange & Act
        int result = Task6.countK(3524);

        // Assert
        assertThat(result).isEqualTo(3);
    }

    @Test
    @DisplayName("Число 6621 требует 5 шагов для получения 6174")
    void countK_ValidInput_ReturnsCorrectStepCountFor6621() {
        // Act & Assert
        assertThat(Task6.countK(6621)).isEqualTo(5);
    }

    @Test
    @DisplayName("Число 6554 требует 4 шага для получения 6174")
    void countK_ValidInput_ReturnsCorrectStepCountFor6554() {
        // Act & Assert
        assertThat(Task6.countK(6554)).isEqualTo(4);
    }

    @Test
    @DisplayName("Число 1234 требует 3 шага для получения 6174")
    void countK_ValidInput_ReturnsCorrectStepCountFor1234() {
        // Act & Assert
        assertThat(Task6.countK(1234)).isEqualTo(3);
    }

    @Test
    @DisplayName("Число 1111 вызывает исключение: все цифры одинаковы")
    void countK_AllDigitsEqual_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> Task6.countK(1111))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Все цифры в числе одинаковы!");
    }

    @Test
    @DisplayName("Число меньше 1000 вызывает исключение: должно быть четырехзначным")
    void countK_NumberLessThan1000_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> Task6.countK(999))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Число должно быть четырехзначным!");
    }

    @Test
    @DisplayName("Число больше 9999 вызывает исключение: должно быть четырехзначным")
    void countK_NumberGreaterThan9999_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> Task6.countK(10000))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Число должно быть четырехзначным!");
    }

    @Test
    @DisplayName("Число 0 вызывает исключение: должно быть четырехзначным")
    void countK_NumberZero_ThrowsException() {
        // Act & Assert
        assertThatThrownBy(() -> Task6.countK(0))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Число должно быть четырехзначным!");
    }
}
