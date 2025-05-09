package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class Task2Test {

    @ParameterizedTest
    @MethodSource("provideYearsWithFriday13ths")
    @DisplayName("Поиск пятниц 13-го для заданного года")
    void findFriday13ths_ShouldReturnCorrectDates(int year, List<LocalDate> expected) {
        // Act
        List<LocalDate> result = Task2.findFriday13ths(year);

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideYearsWithFriday13ths() {
        return Stream.of(
            Arguments.of(
                2024,
                List.of(
                    LocalDate.of(2024, 9, 13),
                    LocalDate.of(2024, 12, 13)
                )
            ),
            Arguments.of(
                1925,
                List.of(
                    LocalDate.of(1925, 2, 13),
                    LocalDate.of(1925, 3, 13),
                    LocalDate.of(1925, 11, 13)
                )
            ),
            Arguments.of(
                2023,
                List.of(
                    LocalDate.of(2023, 1, 13),
                    LocalDate.of(2023, 10, 13)
                )
            ),
            Arguments.of(
                2025,
                List.of(
                    LocalDate.of(2025, 6, 13)
                )
            )
        );
    }

    @ParameterizedTest
    @MethodSource("provideDatesWithNextFriday13")
    @DisplayName("Поиск следующей пятницы 13-го от заданной даты")
    void findNextFriday13_ShouldReturnCorrectDate(LocalDate startDate, LocalDate expected) {
        // Act
        LocalDate result = Task2.findNextFriday13(startDate);

        // Assert
        assertThat(result).isEqualTo(expected);
    }

    private static Stream<Arguments> provideDatesWithNextFriday13() {
        return Stream.of(
            Arguments.of(
                LocalDate.of(2023, 1, 1),
                LocalDate.of(2023, 1, 13)
            ),
            Arguments.of(
                LocalDate.of(2023, 1, 14),
                LocalDate.of(2023, 10, 13)
            ),
            Arguments.of(
                LocalDate.of(2024, 1, 1),
                LocalDate.of(2024, 9, 13)
            ),
            Arguments.of(
                LocalDate.of(2024, 9, 13),
                LocalDate.of(2024, 9, 13)
            ),
            Arguments.of(
                LocalDate.of(2024, 12, 14),
                LocalDate.of(2025, 6, 13)
            )
        );
    }

    @Test
    @DisplayName("Поиск следующей пятницы 13-го при передаче null")
    void findNextFriday13_NullInput_ShouldThrowException() {
        // Act & Assert
        assertThatThrownBy(() -> Task2.findNextFriday13(null))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Дата не может быть null");
    }
}
