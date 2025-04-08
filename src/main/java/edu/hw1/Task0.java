package edu.hw1;

import java.util.logging.Logger;

public final class Task0 {
    private static final Logger LOGGER = Logger.getLogger(Task0.class.getName());

    private Task0() {
        // Приватный конструктор для утилитного класса
    }

    /**
     * Entry point of the program. Logs the message "Привет, мир!" using the logger.
     */
    public static void main(String[] args) {
        LOGGER.info("Привет, мир!");
    }
}
