package edu.hw3;

import edu.hw3.task5.Task5;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

class Task5Test {

    @Test
    @DisplayName("Сортировка по фамилии в порядке возрастания (ASC)")
    void sortContactsAsc() {
        // Arrange
        List<String> input = List.of("John Smith", "Alice Brown", "Bob Johnson");

        // Act
        List<String> result = Task5.parseContacts(input, "ASC");

        // Assert
        assertThat(result).containsExactly("Alice Brown", "Bob Johnson", "John Smith");
    }

    @Test
    @DisplayName("Сортировка по фамилии в порядке убывания (DESC)")
    void sortContactsDesc() {
        // Arrange
        List<String> input = List.of("John Smith", "Alice Brown", "Bob Johnson");

        // Act
        List<String> result = Task5.parseContacts(input, "DESC");

        // Assert
        assertThat(result).containsExactly("John Smith", "Bob Johnson", "Alice Brown");
    }

    @Test
    @DisplayName("Пустой список должен возвращать пустой результат")
    void emptyListReturnsEmpty() {
        // Arrange
        List<String> input = List.of();

        // Act
        List<String> result = Task5.parseContacts(input, "ASC");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Если передать null в качестве списка, должен вернуться пустой список")
    void nullInputReturnsEmpty() {
        // Act
        List<String> result = Task5.parseContacts(null, "ASC");

        // Assert
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Неверный параметр sortOrder должен выбрасывать исключение")
    void invalidSortOrderThrows() {
        // Arrange
        List<String> input = List.of("John Smith", "Alice Brown");

        // Assert
        assertThatThrownBy(() -> Task5.parseContacts(input, "INVALID"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("sortOrder должен быть 'ASC' или 'DESC'");
    }
}
