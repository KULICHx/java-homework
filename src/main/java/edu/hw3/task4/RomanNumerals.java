package edu.hw3.task4;

import java.util.Map;

public final class RomanNumerals {
    private RomanNumerals() {}

    public static final int MIN_VALUE = 1;
    public static final int MAX_VALUE = 3999;

    public static final Map<Integer, String> VALUES = Map.ofEntries(
        Map.entry(1000, "M"),
        Map.entry(900, "CM"),
        Map.entry(500, "D"),
        Map.entry(400, "CD"),
        Map.entry(100, "C"),
        Map.entry(90, "XC"),
        Map.entry(50, "L"),
        Map.entry(40, "XL"),
        Map.entry(10, "X"),
        Map.entry(9, "IX"),
        Map.entry(5, "V"),
        Map.entry(4, "IV"),
        Map.entry(1, "I")
    );
}
