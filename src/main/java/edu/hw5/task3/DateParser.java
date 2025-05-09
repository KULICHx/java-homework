package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;

public abstract class DateParser {
    private DateParser next;

    public DateParser setNext(DateParser next) {
        this.next = next;
        return next;
    }

    public Optional<LocalDate> handle(String request) {
        Optional<LocalDate> result = tryParse(request);
        if (result.isPresent()) {
            return result;
        }
        if (next != null) {
            return next.handle(request);
        }
        return Optional.empty();
    }

    protected abstract Optional<LocalDate> tryParse(String input);
}
