package edu.hw5;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class Task1 {

    private Task1() {
    }

    private static final DateTimeFormatter FORMATTER =
        DateTimeFormatter.ofPattern("yyyy-MM-dd, HH:mm");

    public static String calculateAverageSessionDuration(List<String> sessions) {

        if (sessions == null) {
            throw new IllegalArgumentException("Список сеансов не может быть null");
        }
        if (sessions.isEmpty()) {
            throw new IllegalArgumentException("Список сеансов не может быть empty");
        }

        Duration totalDuration = Duration.ZERO;

        for (String session : sessions) {
            if (session == null || session.trim().isEmpty()) {
                throw new IllegalArgumentException("Сеанс не может быть null или пустым");
            }
            String[] parts = session.split(" - ");
            if (parts.length != 2) {
                throw new IllegalArgumentException("Неправильный формат строки: " + session);
            }

            LocalDateTime start = LocalDateTime.parse(parts[0].trim(), FORMATTER);
            LocalDateTime end = LocalDateTime.parse(parts[1].trim(), FORMATTER);

            if (end.isBefore(start)) {
                throw new IllegalArgumentException("Конец сеанса раньше начала: " + session);
            }

            totalDuration = totalDuration.plus(Duration.between(start, end));
        }

        Duration averageDuration = totalDuration.dividedBy(sessions.size());

        long hours = averageDuration.toHours();
        long minutes = averageDuration.toMinutesPart();

        return String.format("%dч %02dм", hours, minutes);
    }
}
