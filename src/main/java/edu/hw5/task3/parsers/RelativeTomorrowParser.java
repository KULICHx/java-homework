package edu.hw5.task3.parsers;

import edu.hw5.task3.DateParser;
import java.time.LocalDate;
import java.util.Optional;

public class RelativeTomorrowParser extends DateParser {
    @Override
    protected Optional<LocalDate> tryParse(String input) {
        if ("tomorrow".equalsIgnoreCase(input)) {
            return Optional.of(LocalDate.now().plusDays(1));
        }
        return Optional.empty();
    }
}
