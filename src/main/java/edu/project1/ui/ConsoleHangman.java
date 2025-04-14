package edu.project1.ui;

import edu.project1.logic.Session;
import java.util.Scanner;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsoleHangman {
    private static final Logger LOGGER = LogManager.getLogger(ConsoleHangman.class);  // Инициализация логгера
    private final Session session;
    private final Scanner scanner;

    public ConsoleHangman(Session session) {
        this.session = session;
        this.scanner = new Scanner(System.in);
    }

    public ConsoleHangman(Session session, Scanner scanner) {
        this.session = session;
        this.scanner = scanner;
    }

    // Метод для старта игры
    public void startGame() {
        LOGGER.info("Добро пожаловать в игру 'Виселица'!");

        while (session.getRemainingAttempts() > 0 && !session.isWordGuessed()) {
            LOGGER.info("Текущее слово: {}", session.getProgress());  // Логирование текущего состояния слова
            LOGGER.info("Оставшиеся попытки: {}", session.getRemainingAttempts());  // Логирование оставшихся попыток

            LOGGER.info("Введите букву: ");
            String input = scanner.nextLine().trim();

            // Проверка на корректность ввода
            if (input.length() != 1 || !Character.isLetter(input.charAt(0))) {
                LOGGER.warn("Пожалуйста, введите одну букву.");
                continue;
            }

            char guess = input.toLowerCase().charAt(0);  // Приводим к нижнему регистру

            // Попытка угадать букву
            boolean correct = session.guessLetter(guess);
            if (correct) {
                LOGGER.info("Правильно! Буква '{}' угадана.", guess);
            } else {
                LOGGER.info("Неправильно! Буква '{}' отсутствует.", guess);
            }
        }

        // Проверяем результат игры
        if (session.isWordGuessed()) {
            LOGGER.info("Поздравляем! Вы угадали слово: {}", session.getProgress());
        } else {
            LOGGER.info("У вас закончились попытки. Загаданное слово было: {}", session.getWordToGuess());
        }
    }
}
