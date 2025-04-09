package edu.hw1;

import java.util.logging.Logger;

@SuppressWarnings("uncommentedmain")
public final class Task0 {
    private static final Logger LOGGER = Logger.getLogger(Task0.class.getName());

    private Task0() {
        // Приватный конструктор для утилитного класса
    }

    public static void main(String[] args) {
        LOGGER.info("Привет, мир!");
    }
}
