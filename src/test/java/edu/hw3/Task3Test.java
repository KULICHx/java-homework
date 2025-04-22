package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class Task3Test {

    @Test
    @DisplayName("Проверка на уникальные элементы")
    void freqDictShouldReturnCorrectFrequencyForUniqueElements() {
        // Arrange
        List<String> input = List.of("apple", "banana", "cherry");

        // Act
        String result = Task3.freqDict(input);

        // Assert
        assertThat(result).isEqualTo("{apple: 1, banana: 1, cherry: 1}");
    }

    @Test
    @DisplayName("Проверка на повторяющиеся элементы")
    void freqDictShouldReturnCorrectFrequencyForRepeatingElements() {
        // Arrange
        List<String> input = List.of("apple", "banana", "apple", "banana", "apple");

        // Act
        String result = Task3.freqDict(input);

        // Assert
        assertThat(result).isEqualTo("{apple: 3, banana: 2}");
    }

    @Test
    @DisplayName("Проверка на пустой список")
    void freqDictShouldReturnEmptyMapForEmptyList() {
        // Arrange
        List<String> input = List.of();

        // Act
        String result = Task3.freqDict(input);

        // Assert
        assertThat(result).isEqualTo("{}");
    }

    @Test
    @DisplayName("Проверка на одинаковые элементы")
    void freqDictShouldReturnCorrectFrequencyForIdenticalElements() {
        // Arrange
        List<String> input = List.of("apple", "apple", "apple");

        // Act
        String result = Task3.freqDict(input);

        // Assert
        assertThat(result).isEqualTo("{apple: 3}");
    }

    @Test
    @DisplayName("Проверка на использование других типов данных")
    void freqDictShouldReturnCorrectFrequencyForDifferentTypes() {
        // Arrange
        List<Integer> input = List.of(1, 2, 3, 1, 2, 1);

        // Act
        String result = Task3.freqDict(input);

        // Assert
        assertThat(result).isEqualTo("{1: 3, 2: 2, 3: 1}");
    }

    @Test
    @DisplayName("Проверка на пустые элементы в строках")
    void freqDictShouldHandleEmptyString() {
        // Arrange
        List<String> input = List.of("", "apple", "", "banana", "", "apple");

        // Act
        String result = Task3.freqDict(input);

        // Assert
        assertThat(result).isEqualTo("{: 3, apple: 2, banana: 1}");
    }

}
