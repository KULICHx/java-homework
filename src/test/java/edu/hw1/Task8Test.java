package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task8Test {

    @Test
    @DisplayName("Конь не может захватить другого коня: правильная расстановка")
    void knightBoardCapture_ValidBoard_NoCapture() {
        int[][] board = {
            {0, 0, 0, 1, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 1, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 0, 0, 0},
            {0, 1, 0, 0, 0, 0, 0, 1},
            {0, 0, 0, 0, 1, 0, 0, 0}
        };

        // Act & Assert
        assertThat(Task8.knightBoardCapture(board)).isTrue();
    }

    @Test
    @DisplayName("Конь может захватить другого коня: неправильная расстановка")
    void knightBoardCapture_InvalidBoard_Capture() {
        int[][] board = {
            {1, 0, 1, 0, 1, 0, 1, 0},
            {0, 1, 0, 1, 0, 1, 0, 1},
            {0, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 0, 0, 1, 0, 1},
            {1, 0, 0, 0, 1, 0, 1, 0},
            {0, 0, 0, 1, 0, 1, 0, 1}
        };

        // Act & Assert
        assertThat(Task8.knightBoardCapture(board)).isFalse();
    }

    @Test
    @DisplayName("Конь может захватить другого коня: неправильная расстановка")
    void knightBoardCapture_InvalidBoard_Capture_AnotherCase() {
        int[][] board = {
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 0}
        };

        // Act & Assert
        assertThat(Task8.knightBoardCapture(board)).isFalse();
    }

    @Test
    @DisplayName("Все клетки пустые: ни один конь не поставлен")
    void knightBoardCapture_EmptyBoard() {
        int[][] board = new int[8][8];

        // Act & Assert
        assertThat(Task8.knightBoardCapture(board)).isTrue();
    }

    @Test
    @DisplayName("Все клетки заняты, но конь не может захватить другого")
    void knightBoardCapture_FilledBoard_NoCapture() {
        int[][] board = {
            {1, 0, 0, 0, 0, 0, 0, 1},
            {0, 1, 0, 0, 0, 0, 1, 0},
            {0, 0, 1, 0, 0, 1, 0, 0},
            {0, 0, 0, 1, 0, 0, 1, 0},
            {0, 0, 0, 0, 1, 0, 0, 0},
            {0, 0, 0, 0, 0, 1, 0, 0},
            {0, 1, 0, 0, 1, 0, 0, 0},
            {1, 0, 0, 0, 0, 0, 0, 1}
        };

        // Act & Assert
        assertThat(Task8.knightBoardCapture(board)).isFalse();
    }
}
