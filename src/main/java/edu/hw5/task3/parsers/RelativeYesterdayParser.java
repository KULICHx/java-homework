package edu.hw5.task3.parsers;

import edu.hw5.task3.DateParser;
import java.time.LocalDate;
import java.util.Optional;

public class RelativeYesterdayParser extends DateParser {
    @Override
    protected Optional<LocalDate> tryParse(String input) {
        if ("yesterday".equalsIgnoreCase(input)) {
            return Optional.of(LocalDate.now().minusDays(1));
        }
        return Optional.empty();
    }

}
