package edu.hw5;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjuster;
import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private Task2() {

    }

    private static final int MONTHS_IN_YEAR = 12;
    private static final int UNLUCKY_DAY = 13;
    private static final DayOfWeek TARGET_DAY_OF_WEEK = DayOfWeek.FRIDAY;

    public static List<LocalDate> findFriday13ths(int year) {
        List<LocalDate> result = new ArrayList<>();

        for (int month = 1; month <= MONTHS_IN_YEAR; month++) {
            LocalDate date = LocalDate.of(year, month, UNLUCKY_DAY);
            if (date.getDayOfWeek() == TARGET_DAY_OF_WEEK) {
                result.add(date);
            }
        }
        return result;
    }

    public static LocalDate findNextFriday13(LocalDate startDate) {
        if (startDate == null) {
            throw new IllegalArgumentException("Дата не может быть null");
        }

        TemporalAdjuster nextFriday13 = temporal -> {
            LocalDate date = LocalDate.from(temporal)
                .withDayOfMonth(UNLUCKY_DAY);

            if (date.isBefore(startDate)) {
                date =
                    date.plusMonths(1);
            }
            while (date.getDayOfWeek() != TARGET_DAY_OF_WEEK) {
                date = date.plusMonths(1).withDayOfMonth(UNLUCKY_DAY);
            }

            return date;
        };

        return startDate.with(nextFriday13);
    }
}
