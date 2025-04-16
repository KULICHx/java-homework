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
        FileDictionary dictionary = new FileDictionary();
        @NotNull String word = dictionary.randomWord();

        Session session = new Session(word, MAX_ATTEMPTS);

        ConsoleHangman console = new ConsoleHangman(session);
        console.startGame();
    }
}
