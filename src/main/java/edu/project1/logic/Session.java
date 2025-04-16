package edu.project1.logic;

public class Session {
    private static final String HIDDEN_CHAR = "_";
    private final String wordToGuess;
    private int remainingAttempts;
    StringBuilder progress;

    public Session(String word, int maxAttempts) {
        this.wordToGuess = word;
        this.progress = new StringBuilder(HIDDEN_CHAR.repeat(word.length()));
        this.remainingAttempts = maxAttempts;
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
        return progress.indexOf(HIDDEN_CHAR) == -1;
    }

    public String getProgress() {
        return progress.toString();
    }

    public int getRemainingAttempts() {
        return remainingAttempts;
    }

    public String getWordToGuess() {
        return wordToGuess;
    }
}
