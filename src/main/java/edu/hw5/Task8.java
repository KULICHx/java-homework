package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task8 {
    private Task8() {
    }

    public static boolean isOddLengthBinary(String number) {
        String regex = "^[01]([01]{2})*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean matchesZeroOddOrOneEvenPattern(String number) {
        String regex = "^[0]([01]{2})*$|^[1][01]{1}([01]{2})*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean hasZeroCountMultipleOfThree(String number) {
        String regex = "^(1*01*01*01*)*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean isNotElevenOrOneEleven(String number) {
        String regex = "^(?!11$)(?!111$)[01]*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean hasOnesAtOddPositions(String number) {
        String regex = "^(1[01])*1?$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean hasTwoOrMoreZerosAndAtMostOneOne(String number) {
        String regex = "^(0.*0.*[^1]*1?[^1]*)$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean hasNoConsecutiveOnes(String number) {
        String regex = "^(0|10)*1?$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }
}
