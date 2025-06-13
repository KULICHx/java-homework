package edu.hw5;

import edu.hw5.task3.Task3;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

class Task3Test {
    private final Task3 dateParser = new Task3();

    @Test
    @DisplayName("Парсер строгого ISO формата (yyyy-MM-dd)")
    void parseStrictIsoFormat() {
        String date = "2020-10-10";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.of(2020, 10, 10));
    }

    @Test
    @DisplayName("Парсер нестрогого ISO формата (yyyy-M-d)")
    void parseLenientIsoFormat() {
        String date = "2020-12-2";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.of(2020, 12, 2));
    }

    @Test
    @DisplayName("Парсер формата месяц/день/год (M/d/yyyy)")
    void parseSlashMDYFormat() {
        String date = "1/3/1976";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.of(1976, 1, 3));
    }

    @Test
    @DisplayName("Парсер формата месяц/день/короткий год (M/d/yy)")
    void parseSlashShortYearFormat() {
        String date = "1/3/20";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.of(2020, 1, 3));
    }

    @Test
    @DisplayName("Парсер относительной даты 'tomorrow'")
    void parseTomorrow() {
        String date = "tomorrow";
        LocalDate today = LocalDate.now();
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(today.plusDays(1));
    }

    @Test
    @DisplayName("Парсер относительной даты 'today'")
    void parseToday() {
        String date = "today";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.now());
    }

    @Test
    @DisplayName("Парсер относительной даты 'yesterday'")
    void parseYesterday() {
        String date = "yesterday";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.now().minusDays(1));
    }

    @Test
    @DisplayName("Парсер относительной даты '1 day ago'")
    void parseOneDayAgo() {
        String date = "1 day ago";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.now().minusDays(1));
    }

    @Test
    @DisplayName("Парсер относительной даты '2234 days ago'")
    void parseManyDaysAgo() {
        String date = "2234 days ago";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).contains(LocalDate.now().minusDays(2234));
    }

    @Test
    @DisplayName("Невалидный формат даты")
    void parseInvalidFormat() {
        String date = "invalid date";
        Optional<LocalDate> result = dateParser.parseDate(date);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Null вместо строки")
    void parseNullInput() {
        Optional<LocalDate> result = dateParser.parseDate(null);
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Пустая строка")
    void parseEmptyString() {
        Optional<LocalDate> result = dateParser.parseDate("");
        assertThat(result).isEmpty();
    }

    @Test
    @DisplayName("Строка с пробелами")
    void parseBlankString() {
        Optional<LocalDate> result = dateParser.parseDate("   ");
        assertThat(result).isEmpty();
    }
}
