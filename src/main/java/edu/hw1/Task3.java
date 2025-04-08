package edu.hw1;

public class Task3 {

    private Task3() {
    }

    public static boolean isNestable(int[] a1, int[] a2) {
        if (a1 == null || a2 == null || a1.length == 0 || a2.length == 0) {
            return false;
        }
        return ((findMin(a1) > findMin(a2)) && (findMax(a1) < findMax(a2)));
    }

    public static int findMax(int[] arr) {
        int max = arr[0];
        for (int num : arr) {
            if (num > max) {
                max = num;
            }
        }
        return max;
    }

    public static int findMin(int[] arr) {
        int min = arr[0];
        for (int num : arr) {
            if (num < min) {
                min = num;
            }
        }
        return min;
    }

}
