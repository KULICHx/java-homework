package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

public class Task2Test {
    @Test
    @DisplayName("кластеры1")
    void clusterize1() {
        // Arrange
        String input = "()()()";

        // Act
        List<String> result = Task2.clusterize(input);

        // Assert
        assertThat(result).isEqualTo(List.of("()", "()", "()"));
    }
    @Test
    @DisplayName("кластеры2")
    void clusterize2() {
        // Arrange
        String input = "((()))";

        // Act
        List<String> result = Task2.clusterize(input);

        // Assert
        assertThat(result).isEqualTo(List.of("((()))"));
    }
    @Test
    @DisplayName("кластеры2")
    void clusterize3() {
        // Arrange
        String input = "((()))(())()()(()())";

        // Act
        List<String> result = Task2.clusterize(input);

        // Assert
        assertThat(result).isEqualTo(List.of("((()))", "(())", "()", "()", "(()())"));
    }
}
