package edu.hw1;

import java.util.Arrays;

public class Task6 {

    private static final int KAPREKAR_CONSTANT = 6174;
    private static final int MIN_FOUR_DIGIT = 1001;
    private static final int MAX_FOUR_DIGIT = 9998;
    private static final int MAX_STEPS = 7;

    private Task6() {
    }

    public static int countK(int number) {
        validateInput(number);
        int currentNumber = number;
        int count = 0;

        while (currentNumber != KAPREKAR_CONSTANT && count < MAX_STEPS) {
            int[] maxAndMin = computeMaxAndMinNumber(currentNumber);
            currentNumber = maxAndMin[0] - maxAndMin[1];
            count++;
        }

        if (currentNumber != KAPREKAR_CONSTANT) {
            throw new IllegalStateException("Не сошлось к " + KAPREKAR_CONSTANT + " за " + MAX_STEPS + " шагов!");
        }
        return count;
    }

    private static void validateInput(int number) {
        if (number < MIN_FOUR_DIGIT || number > MAX_FOUR_DIGIT) {
            throw new IllegalArgumentException("Число должно быть четырехзначным!");
        }
        if (hasAllDigitsEqual(number)) {
            throw new IllegalArgumentException("Все цифры в числе одинаковы!");
        }
    }

    private static int[] computeMaxAndMinNumber(int number) {
        String str = String.format("%04d", number);
        char[] digits = str.toCharArray();
        Arrays.sort(digits);
        String asc = new String(digits);
        String desc = new StringBuilder(asc).reverse().toString();
        return new int[] {Integer.parseInt(desc), Integer.parseInt(asc)};
    }

    public static boolean hasAllDigitsEqual(int number) {
        String str = String.valueOf(number);
        char firstDigit = str.charAt(0);

        for (int i = 1; i < str.length(); i++) {
            if (str.charAt(i) != firstDigit) {
                return false;
            }
        }
        return true;
    }
}
