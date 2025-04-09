package edu.hw1;

public class Task5 {

    private Task5() {
    }

    public static boolean isPalindromeDescendant(int number) {
        int absNumber = Math.abs(number);
        String str = String.valueOf(absNumber);

        while (str.length() >= 2) {
            if (isPalindrome(str)) {
                return true;
            }

            str = generateDescendant(str);
        }

        return false;
    }

    private static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    private static String generateDescendant(String str) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < str.length() - 1; i += 2) {
            int sum = Character.getNumericValue(str.charAt(i)) + Character.getNumericValue(str.charAt(i + 1));
            result.append(sum);
        }
        return result.toString();
    }
}
