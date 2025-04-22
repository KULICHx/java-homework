package edu.hw3.task4;

import java.util.Collections;
import java.util.Map;
import java.util.TreeMap;

public class Task4 {
    private static final Map<Integer, String> ROMAN_MAP = createRomanMap();

    private static Map<Integer, String> createRomanMap() {
        Map<Integer, String> map = new TreeMap<>(Collections.reverseOrder());
        map.putAll(RomanNumerals.VALUES);
        return Collections.unmodifiableMap(map);
    }

    private Task4() {
    }

    public static String convertToRoman(int number) {
        if (number < RomanNumerals.MIN_VALUE || number > RomanNumerals.MAX_VALUE) {
            throw new IllegalArgumentException(
                String.format("Number must be between %d and %d",
                    RomanNumerals.MIN_VALUE, RomanNumerals.MAX_VALUE)
            );
        }

        StringBuilder roman = new StringBuilder();
        int remaining = number;

        for (Map.Entry<Integer, String> entry : ROMAN_MAP.entrySet()) {
            while (remaining >= entry.getKey()) {
                roman.append(entry.getValue());
                remaining -= entry.getKey();
            }
        }

        return roman.toString();
    }
}
