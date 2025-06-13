package edu.hw5.task3;

import edu.hw5.task3.parsers.LenientIsoDateParser;
import edu.hw5.task3.parsers.RelativeDaysAgoParser;
import edu.hw5.task3.parsers.RelativeTodayParser;
import edu.hw5.task3.parsers.RelativeTomorrowParser;
import edu.hw5.task3.parsers.RelativeYesterdayParser;
import edu.hw5.task3.parsers.SlashMDYParser;
import edu.hw5.task3.parsers.SlashShortYearParser;
import edu.hw5.task3.parsers.StrictIsoDateParser;
import java.time.LocalDate;
import java.util.Optional;

public class Task3 {
    private DateParser chain;

    public Task3() {
        buildDefaultChain();
    }

    private void buildDefaultChain() {
        this.chain = new RelativeDaysAgoParser();
        chain.setNext(new StrictIsoDateParser())
            .setNext(new RelativeTodayParser())
            .setNext(new RelativeYesterdayParser())
            .setNext(new RelativeTomorrowParser())
            .setNext(new SlashMDYParser())
            .setNext(new SlashShortYearParser())
            .setNext(new LenientIsoDateParser());
    }

    public Optional<LocalDate> parseDate(String dateString) {
        if (dateString == null || dateString.isBlank() || chain == null) {
            return Optional.empty();
        }
        return chain.handle(dateString.trim());
    }
}
