package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Task4Test {

    @Test
    @DisplayName("Пароль с одним из требуемых символов")
    public void testPasswordWithSpecialCharacter() {
        assertTrue(Task4.passwordValidator("Password!123"));
    }

    @Test
    @DisplayName("Пароль без одного из требуемых символов")
    public void testPasswordWithoutSpecialCharacter() {
        assertFalse(Task4.passwordValidator("Password123"));
    }

    @Test
    @DisplayName("Пароль с символом '|'")
    public void testPasswordWithPipe() {
        assertTrue(Task4.passwordValidator("MyPass|word"));
    }

    @Test
    @DisplayName("Пароль с символом '~'")
    public void testPasswordWithTilde() {
        assertTrue(Task4.passwordValidator("~HelloWorld"));
    }

    @Test
    @DisplayName("Пароль с символом '!'")
    public void testPasswordWithExclamationMark() {
        assertTrue(Task4.passwordValidator("Hello!World"));
    }

    @Test
    @DisplayName("Пароль с несколькими требуемыми символами")
    public void testPasswordWithMultipleSpecialCharacters() {
        assertTrue(Task4.passwordValidator("Password@#123"));
    }

    @Test
    @DisplayName("Пустой пароль, не содержит символов")
    public void testEmptyPassword() {
        assertFalse(Task4.passwordValidator(""));
    }

    @Test
    @DisplayName("Пароль, состоящий только из пробелов, не содержит специальных символов")
    public void testPasswordWithOnlySpaces() {
        assertFalse(Task4.passwordValidator("     "));
    }

    @Test
    @DisplayName("Пароль с пробелами и одним специальным символом")
    public void testPasswordWithSpecialCharactersAndSpaces() {
        assertTrue(Task4.passwordValidator("Password 123 |"));
    }
}
