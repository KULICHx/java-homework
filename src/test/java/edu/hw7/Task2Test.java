package edu.hw7;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class Task2Test {

    @Test
    @DisplayName("Факториал 0 должен быть 1")
    void testFactorialZero() {
        assertEquals(BigInteger.ONE, Task2.factorial(0));
    }

    @Test
    @DisplayName("Факториал 1 должен быть 1")
    void testFactorialOne() {
        assertEquals(BigInteger.ONE, Task2.factorial(1));
    }

    @Test
    @DisplayName("Факториал 5 должен быть 120")
    void testFactorialFive() {
        assertEquals(BigInteger.valueOf(120), Task2.factorial(5));
    }

    @Test
    @DisplayName("Факториал 10 должен быть 3628800")
    void testFactorialTen() {
        assertEquals(new BigInteger("3628800"), Task2.factorial(10));
    }

    @Test
    @DisplayName("Передача отрицательного числа должна выбрасывать IllegalArgumentException")
    void testFactorialNegative() {
        assertThrows(IllegalArgumentException.class, () -> Task2.factorial(-1));
    }
}
