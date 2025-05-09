package edu.hw5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task7 {
    private Task7() {
    }

    public static boolean isValidBinaryWithThirdZero(String number) {
        String regex = "^[01]{2}0[01]*$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean endsWithFirstChar(String number) {
        String regex = "^(\\d)[01]*\\1$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }

    public static boolean isLengthValid(String number) {
        String regex = "^[01]{1,3}$";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(number);

        return matcher.matches();
    }
}
