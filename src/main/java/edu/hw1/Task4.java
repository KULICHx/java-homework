package edu.hw1;

public class Task4 {

    private Task4() {
    }

    public static String fixString(String brokenString) {
        if (brokenString == null || brokenString.isEmpty()) {
            return brokenString;
        }
        StringBuilder newStr = new StringBuilder(brokenString);
        for (int i = 0; i < newStr.length() - 1; i += 2) {
            char temp = newStr.charAt(i);
            newStr.setCharAt(i, newStr.charAt(i + 1));
            newStr.setCharAt(i + 1, temp);
        }
        return String.valueOf(newStr);
    }
}
