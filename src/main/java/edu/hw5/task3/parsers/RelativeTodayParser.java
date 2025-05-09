package edu.hw5.task3.parsers;

import edu.hw5.task3.DateParser;
import java.time.LocalDate;
import java.util.Optional;

public class RelativeTodayParser extends DateParser {
    @Override
    protected Optional<LocalDate> tryParse(String input) {
        if ("today".equalsIgnoreCase(input)) {
            return Optional.of(LocalDate.now());
        }
        return Optional.empty();
    }
}
