package edu.project1Test;

import edu.project1.logic.Session;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class SessionTest {


    @Test
    @DisplayName("Игра завершается поражением после превышения количества попыток")
    void gameLosesAfterTooManyMistakes() {
        Session session = new Session("дом", 3);

        session.guessLetter('а'); // 1
        session.guessLetter('б'); // 2
        session.guessLetter('в'); // 3

        assertThat(session.getRemainingAttempts()).isEqualTo(0);
        assertThat(session.isWordGuessed()).isFalse();
    }

    @Test
    @DisplayName("Состояние игры корректно изменяется при угадывании и промахе")
    void gameStateChangesOnGuess() {
        Session session = new Session("кот", 5);

        // Угадали
        boolean firstTry = session.guessLetter('о');
        assertThat(firstTry).isTrue();
        assertThat(session.getProgress()).isEqualTo("_о_");
        assertThat(session.getRemainingAttempts()).isEqualTo(5);

        // Промах
        boolean secondTry = session.guessLetter('ы');
        assertThat(secondTry).isFalse();
        assertThat(session.getProgress()).isEqualTo("_о_");
        assertThat(session.getRemainingAttempts()).isEqualTo(4);
    }

    @Test
    @DisplayName("Ввод строки длиной больше 1 символа (опечатка) не влияет на игру")
    void multiCharInputIgnored() {
        Session session = new Session("код", 5);


        int beforeAttempts = session.getRemainingAttempts();
        String beforeProgress = session.getProgress();


        // Проверка, что состояние не изменилось
        assertThat(session.getRemainingAttempts()).isEqualTo(beforeAttempts);
        assertThat(session.getProgress()).isEqualTo(beforeProgress);
    }
}
