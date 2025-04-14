package edu.project1.logic;

public class Session {
    private final String wordToGuess;
    private int remainingAttempts;
    StringBuilder progress;

    public Session(String word, int maxAttempts) {
        this.wordToGuess = word;
        this.progress = new StringBuilder("_".repeat(word.length()));  // Инициализируем прогресс
        this.remainingAttempts = maxAttempts;  // Устанавливаем количество попыток
    }

    public boolean guessLetter(char letter) {

        boolean correctGuess = false;

        for (
            int i = 0; i < wordToGuess.length(); i++) {
            if (wordToGuess.charAt(i) == letter && progress.charAt(i) == '_') {
                progress.setCharAt(i, letter);
                correctGuess = true;
            }
        }

        if (!correctGuess) {
            remainingAttempts--;
        }
        return correctGuess;

    }

    public boolean isWordGuessed() {
        return progress.indexOf("_") == -1;  // Если нет незаполненных мест, значит слово угадано
    }

    // Получение текущего состояния прогресса
    public String getProgress() {
        return progress.toString();
    }

    // Получение оставшихся попыток
    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}
