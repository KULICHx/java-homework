package edu.project1Test;

import edu.project1.dictionary.FileDictionary;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class FileDictionaryTest {

    @Test
    @DisplayName("Словарь загружается корректно")
    void dictionaryLoadsCorrectly() {
        FileDictionary dictionary = new FileDictionary();
        assertThat(dictionary.randomWord()).isNotNull();
    }

    @Test
    @DisplayName("Слово из словаря соответствует ограничениям длины")
    void wordLengthWithinLimits() {
        FileDictionary dictionary = new FileDictionary();
        String word = dictionary.randomWord();
        assertThat(word.length()).isBetween(3, 12);
    }
}
