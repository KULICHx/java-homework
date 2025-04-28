package edu.hw3;

import java.util.Comparator;
import java.util.TreeMap;

public class Task7 {
    private Task7() {
    }

    public static TreeMap<String, String> treeMapNull() {
        Comparator<String> nullFriendlyComparator =
            Comparator.nullsFirst(Comparator.naturalOrder());

        TreeMap<String, String> tree = new TreeMap<>(nullFriendlyComparator);
        tree.put(null, "test");
        return tree;
    }
}
