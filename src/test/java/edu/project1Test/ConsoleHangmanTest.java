package edu.project1Test;

import edu.project1.logic.Session;
import edu.project1.ui.ConsoleHangman;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class ConsoleHangmanTest {

    @Test
    @DisplayName("Пользователь угадывает слово правильно с первой попытки")
    void userWinsGameQuickly() {
        // given: подставим слово "код" и введем буквы: к, о, д
        String input = String.join(System.lineSeparator(), "к", "о", "д") + System.lineSeparator();
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Session session = new Session("код", 6);
        ConsoleHangman game = new ConsoleHangman(session, scanner);

        // when: запускаем игру
        game.startGame();

        // then: проверяем, что слово угадано и попытки не кончились
        assertThat(session.isWordGuessed()).isTrue();
        assertThat(session.getRemainingAttempts()).isGreaterThan(0);
    }

    @Test
    @DisplayName("Пользователь проигрывает, делая неправильные ходы")
    void userLosesGame() {
        // given: неправильные буквы (не из слова "код")
        String input = String.join(System.lineSeparator(), "а", "б", "в", "г", "е", "ж") + System.lineSeparator();
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Session session = new Session("код", 6);
        ConsoleHangman game = new ConsoleHangman(session, scanner);

        // when
        game.startGame();

        // then
        assertThat(session.isWordGuessed()).isFalse();
        assertThat(session.getRemainingAttempts()).isEqualTo(0);
    }

    @Test
    @DisplayName("Ввод строки длиннее 1 символа не влияет на игру")
    void invalidInputIsIgnored() {
        // given: сначала неверный ввод, потом верный
        String input = String.join(System.lineSeparator(), "что?", "к", "о", "д") + System.lineSeparator();
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes()));
        Session session = new Session("код", 6);
        ConsoleHangman game = new ConsoleHangman(session, scanner);

        // when
        game.startGame();

        // then
        assertThat(session.isWordGuessed()).isTrue();
        assertThat(session.getRemainingAttempts()).isGreaterThan(0); // попытки не теряются на ерунду
    }

}
