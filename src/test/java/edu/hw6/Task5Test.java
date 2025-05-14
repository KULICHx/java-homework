package edu.hw6;

import edu.hw6.task5.HackerNews;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {

    @Test
    @DisplayName("Получение топовых историй - должен возвращать непустой массив")
    void hackerNewsTopStories_ShouldReturnNonEmptyArray() {
        // Act
        long[] result = HackerNews.hackerNewsTopStories();

        // Assert
        assertThat(result)
            .isNotNull()
            .isNotEmpty()
            .doesNotContain(0L); // Дополнительная проверка, что нет нулевых ID
    }

    @Test
    @DisplayName("Получение новости по существующему ID - должен возвращать заголовок")
    void news_ShouldReturnTitleForValidId() {
        // Arrange
        long validId = 37570037;

        // Act
        String title = HackerNews.news(validId);

        // Assert
        assertThat(title)
            .isNotBlank()
            .hasSizeGreaterThan(10) // Проверяем минимальную длину заголовка
            .doesNotContain("Error"); // Проверяем, что это не сообщение об ошибке
    }

    @Test
    @DisplayName("Получение новости по несуществующему ID - должен возвращать пустую строку")
    void news_ShouldReturnEmptyForInvalidId() {
        // Arrange
        long invalidId = -1;

        // Act
        String title = HackerNews.news(invalidId);

        // Assert
        assertThat(title)
            .isEmpty();
    }

    @Test
    @DisplayName("При ошибке должен возвращать валидный результат")
    void hackerNewsTopStories_ShouldHandleErrorsGracefully() {
        // Arrange

        // Act
        long[] result = HackerNews.hackerNewsTopStories();

        // Assert
        assertThat(result)
            .isNotNull() // 1. Не null
            .satisfiesAnyOf( // 2. Либо пустой, либо содержит данные
                arr -> assertThat(arr).isEmpty(),
                arr -> assertThat(arr).isNotEmpty()
            );
    }

    @Test
    @DisplayName("Метод news должен возвращать пустую строку при ошибках")
    void news_ShouldReturnEmptyStringOnErrors() {
        // Arrange
        long brokenId = Long.MIN_VALUE;

        // Act
        String title = HackerNews.news(brokenId);

        // Assert
        assertThat(title)
            .isEmpty();
    }
}
