package edu.hw1;

public final class Task1 {
    private static final int SECONDS_IN_MINUTE = 60;
    private static final int MAX_SECONDS = 59;

    private Task1() {
    }

    public static int minutesToSeconds(String input) {
        int result = -1;  // Значение по умолчанию при ошибке

        if (input != null && input.contains(":")) {
            String[] parts = input.split(":");
            if (parts.length == 2) {
                try {
                    int minutes = Integer.parseInt(parts[0]);
                    int seconds = Integer.parseInt(parts[1]);

                    if (seconds <= MAX_SECONDS && seconds >= 0 && minutes >= 0) {
                        result = minutes * SECONDS_IN_MINUTE + seconds;
                    }
                } catch (NumberFormatException ignored) {
                    // Оставляем result = -1
                }
            }
        }

        return result;
    }
}
