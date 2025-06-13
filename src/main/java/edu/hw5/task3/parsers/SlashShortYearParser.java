package edu.hw5.task3.parsers;

import edu.hw5.task3.DateParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class SlashShortYearParser extends DateParser {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("M/d/yy");

    @Override
    protected Optional<LocalDate> tryParse(String input) {
        try {
            LocalDate date = LocalDate.parse(input, FORMATTER);
            return Optional.of(date);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }
}
