package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task7Test {

    @Test
    @DisplayName("Циклический сдвиг вправо: 8 >> 1 = 4 (1000 -> 0100)")
    void rotateRight_ValidInput_ReturnsCorrectResultFor8Shift1() {
        // Act & Assert
        assertThat(Task7.rotateRight(8, 1)).isEqualTo(4);
    }

    @Test
    @DisplayName("Циклический сдвиг влево: 16 << 1 = 1 (10000 -> 00001)")
    void rotateLeft_ValidInput_ReturnsCorrectResultFor16Shift1() {
        // Act & Assert
        assertThat(Task7.rotateLeft(16, 1)).isEqualTo(1);
    }

    @Test
    @DisplayName("Циклический сдвиг влево: 17 << 2 = 6 (10001 -> 00110)")
    void rotateLeft_ValidInput_ReturnsCorrectResultFor17Shift2() {
        // Act & Assert
        assertThat(Task7.rotateLeft(17, 2)).isEqualTo(6);
    }

    @Test
    @DisplayName("Циклический сдвиг вправо: 4 >> 2 = 1 (0100 -> 0001)")
    void rotateRight_ValidInput_ReturnsCorrectResultFor4Shift2() {
        // Act & Assert
        assertThat(Task7.rotateRight(4, 2)).isEqualTo(1);
    }

    @Test
    @DisplayName("Циклический сдвиг вправо: 32 >> 5 = 1 (100000 -> 00001)")
    void rotateRight_ValidInput_ReturnsCorrectResultFor32Shift5() {
        // Act & Assert
        assertThat(Task7.rotateRight(32, 5)).isEqualTo(1);
    }

    @Test
    @DisplayName("Циклический сдвиг вправо: 1024 >> 10 = 1 (10000000000 -> 00000000001)")
    void rotateRight_ValidInput_ReturnsCorrectResultFor1024Shift10() {
        // Act & Assert
        assertThat(Task7.rotateRight(1024, 10)).isEqualTo(1);
    }

}
