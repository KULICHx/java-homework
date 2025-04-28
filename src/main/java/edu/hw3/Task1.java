package edu.hw3;

public class Task1 {
    private Task1() {
    }

    public static String atbash(String input) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            if (ch >= 'A' && ch <= 'Z') {
                result.append((char) ('Z' - (ch - 'A')));
            } else if (ch >= 'a' && ch <= 'z') {
                result.append((char) ('z' - (ch - 'a')));
            } else {
                result.append(ch);
            }
        }
        return result.toString();
    }
}
