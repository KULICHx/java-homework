package edu.hw5.task3.parsers;

import edu.hw5.task3.DateParser;
import java.time.LocalDate;
import java.util.Optional;
import java.util.regex.Pattern;

public class RelativeDaysAgoParser extends DateParser {
    private static final Pattern PATTERN = Pattern.compile("^(\\d+) days? ago$", Pattern.CASE_INSENSITIVE);

    @Override
    protected Optional<LocalDate> tryParse(String input) {
        var matcher = PATTERN.matcher(input);
        if (matcher.matches()) {
            try {
                int days = Integer.parseInt(matcher.group(1));
                return Optional.of(LocalDate.now().minusDays(days));
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }
        return Optional.empty();
    }
}

