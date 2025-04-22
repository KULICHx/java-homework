package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.TreeMap;

import static org.assertj.core.api.Assertions.*;

class Task7Test {

    @Test
    @DisplayName("Проверка добавления ключа null в TreeMap")
    void addNullKeyReturnsThatKey() {
        // Act
        TreeMap<String, String> tree = Task7.treeMapNull();

        // Assert
        assertThat(tree.containsKey(null)).isTrue();
    }

    @Test
    @DisplayName("Проверка значения для ключа null в TreeMap")
    void getValueForNullKeyReturnsCorrectValue() {

        // Act
        TreeMap<String, String> tree = Task7.treeMapNull();

        // Assert
        assertThat(tree.get(null)).isEqualTo("test");
    }


    @Test
    @DisplayName("TreeMap не выбрасывает исключение при добавлении null-ключа несколько раз")
    void addNullMultipleTimesDoesNotThrowException() {
        // Act
        TreeMap<String, String> tree = Task7.treeMapNull();
        tree.put(null, "test1");
        tree.put(null, "test2");

        // Assert
        assertThat(tree.get(null)).isEqualTo("test2");  // Проверяем, что последнее значение для null-ключа записано
    }
}
