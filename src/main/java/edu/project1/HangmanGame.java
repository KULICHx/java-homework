package edu.project1;

import edu.project1.dictionary.FileDictionary;
import edu.project1.logic.Session;
import edu.project1.ui.ConsoleHangman;
import org.jetbrains.annotations.NotNull;

public class HangmanGame {
    private HangmanGame() {
    }

    private static final int MAX_ATTEMPTS = 6;

    @SuppressWarnings("uncommentedmain")
    public static void main(String[] args) {
        // Загружаем случайное слово из словаря
        FileDictionary dictionary = new FileDictionary();
        @NotNull String word = dictionary.randomWord();

        // Создаём игру с максимальным количеством попыток
        Session session = new Session(word, MAX_ATTEMPTS);

        // Запускаем игру с консолью
        ConsoleHangman console = new ConsoleHangman(session);
        console.startGame();
    }
}
