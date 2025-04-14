package edu.hw1;

public final class Task2 {
    private static final int DECIMAL_BASE = 10;  // Основание десятичной системы
    private static final int MIN_DIGITS_IN_INT = 10;  // Количество цифр в Integer.MIN_VALUE


    private Task2() {
    }

    /**
     * Подсчитывает количество цифр в десятичной записи числа.
     *
     * @param number Входное число (может быть отрицательным).
     * @return Количество цифр в числе. Для 0 возвращает 1.
     */
    public static int countDigits(int number) {
        if (number == 0) {
            return 1;
        }
        if (number == Integer.MIN_VALUE) {
            return MIN_DIGITS_IN_INT;
        }


        int currentNumber = Math.abs(number);
        int count = 0;

        while (currentNumber > 0) {
            currentNumber /= DECIMAL_BASE;
            count++;
        }

        return count;
    }
}
