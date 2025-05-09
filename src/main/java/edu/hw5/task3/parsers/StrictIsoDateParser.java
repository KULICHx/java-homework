package edu.hw5.task3.parsers;

import edu.hw5.task3.DateParser;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class StrictIsoDateParser extends DateParser {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    protected Optional<LocalDate> tryParse(String input) {
        try {
            return Optional.of(LocalDate.parse(input, FORMATTER));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

}
