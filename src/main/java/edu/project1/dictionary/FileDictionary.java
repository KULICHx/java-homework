package edu.project1.dictionary;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class FileDictionary {
    private static final int MIN_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 12;
    private static final String[] FALLBACK_WORDS = {"джава", "код", "игра", "кулич", "потеря"};

    private final List<String> words;
    private final Random random = new Random();

    public FileDictionary() {
        this.words = loadWords();
    }

    private List<String> loadWords() {
        List<String> wordList = new ArrayList<>();

        try (InputStream is = getClass().getResourceAsStream("/10000-russian-words.txt");
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim().toLowerCase();

                if (
                        line.length() >= MIN_WORD_LENGTH
                                && line.length() <= MAX_WORD_LENGTH
                                && line.matches("[а-я]+")
                ) {
                    wordList.add(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Ошибка загрузки словаря: " + e.getMessage());
        }

        if (wordList.isEmpty()) {
            Collections.addAll(wordList, FALLBACK_WORDS);
        }

        return wordList;
    }

    public String randomWord() {
        return words.get(random.nextInt(words.size()));
    }
}
