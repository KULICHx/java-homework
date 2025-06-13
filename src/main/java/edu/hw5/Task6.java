package edu.hw5;

import java.util.regex.Pattern;

public class Task6 {
    private Task6() {
    }

    public static boolean isSubsequenceRegex(String s, String t) {
        if (s == null || t == null) {
            return false;
        }
        if (s.isEmpty()) {
            return true;
        }
        if (s.length() > t.length()) {
            return false;
        }

        // Строим регулярное выражение
        StringBuilder regex = new StringBuilder("(?s)^");
        for (char c : s.toCharArray()) {
            regex.append(".*?").append(Pattern.quote(String.valueOf(c)));
        }
        regex.append(".*$");

        return Pattern.compile(regex.toString()).matcher(t).matches();
    }
}
