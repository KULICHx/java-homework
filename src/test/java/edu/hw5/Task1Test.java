package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Task1Test {

    @Test
    @DisplayName("Корректный расчет среднего времени для одного сеанса")
    void calculateAverageSessionDuration_SingleSession_ReturnsCorrectDuration() {
        // Arrange
        List<String> sessions = List.of("2022-03-12, 20:20 - 2022-03-12, 23:50");

        // Act
        String result = Task1.calculateAverageSessionDuration(sessions);

        // Assert
        assertThat(result).isEqualTo("3ч 30м");
    }

    @ParameterizedTest
    @MethodSource("provideMultipleSessions")
    @DisplayName("Корректный расчет среднего времени для нескольких сеансов")
    void calculateAverageSessionDuration_MultipleSessions_ReturnsCorrectAverage(
        List<String> sessions, String expected) {
        // Act
        String result = Task1.calculateAverageSessionDuration(sessions);

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideMultipleSessions() {
        return Stream.of(
            Arguments.of(
                List.of(
                    "2022-03-12, 20:20 - 2022-03-12, 23:50",
                    "2022-04-01, 21:30 - 2022-04-02, 01:20"
                ),
                "3ч 40м"
            ),
            Arguments.of(
                List.of(
                    "2022-03-12, 10:00 - 2022-03-12, 12:30",
                    "2022-03-12, 14:00 - 2022-03-12, 15:45"
                ),
                "2ч 07м"
            )
        );
    }

    @Test
    @DisplayName("Сеанс с переходом через полночь")
    void calculateAverageSessionDuration_CrossMidnight_ReturnsCorrectDuration() {
        // Arrange
        List<String> sessions = List.of("2022-04-01, 23:00 - 2022-04-02, 01:30");

        // Act
        String result = Task1.calculateAverageSessionDuration(sessions);

        // Assert
        assertThat(result).isEqualTo("2ч 30м");
    }

    @Test
    @DisplayName("Крайний случай: сеанс длится ровно 24 часа")
    void calculateAverageSessionDuration_24HoursSession_Returns24h() {
        // Arrange
        List<String> sessions = List.of("2022-03-12, 00:00 - 2022-03-13, 00:00");

        // Act
        String result = Task1.calculateAverageSessionDuration(sessions);

        // Assert
        assertThat(result).isEqualTo("24ч 00м");
    }

    @Test
    @DisplayName("Пустой список сеансов должен вызывать исключение")
    void calculateAverageSessionDuration_EmptyList_ThrowsException() {
        // Arrange
        List<String> sessions = List.of();

        // Act & Assert
        assertThatThrownBy(() -> Task1.calculateAverageSessionDuration(sessions))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("empty");
    }

    @Test
    @DisplayName("Сеанс с отрицательной длительностью должен вызывать исключение")
    void calculateAverageSessionDuration_NegativeDuration_ThrowsException() {
        // Arrange
        List<String> sessions = List.of("2022-03-12, 23:50 - 2022-03-12, 20:20");

        // Act & Assert
        assertThatThrownBy(() -> Task1.calculateAverageSessionDuration(sessions))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Конец сеанса раньше начала");
    }
}
