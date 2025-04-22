package edu.hw3;

import java.util.Comparator;
import java.util.TreeMap;

public class Task7 {
    private Task7() {
    }

    public static TreeMap<String, String> treeMapNull() {
        Comparator<String> nullFriendlyComparator = (a, b) -> {
            if (a == null && b == null) {
                return 0;
            }
            if (a == null) {
                return -1;
            }
            if (b == null) {
                return 1;
            }
            return a.compareTo(b);
        };

        TreeMap<String, String> tree = new TreeMap<>(nullFriendlyComparator);
        tree.put(null, "test");
        return tree;
    }
}
