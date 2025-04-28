package edu.hw3;

import java.util.ArrayList;
import java.util.List;

public class Task2 {
    private Task2() {
    }

    public static List<String> clusterize(String input) {
        List<String> clusters = new ArrayList<>();
        StringBuilder currentCluster = new StringBuilder();
        int balance = 0;

        for (int i = 0; i < input.length(); i++) {
            char ch = input.charAt(i);
            currentCluster.append(ch);

            if (ch == '(') {
                balance++;
            } else if (ch == ')') {
                balance--;
            }

            if (balance == 0) {
                clusters.add(currentCluster.toString());
                currentCluster.setLength(0);
            }
        }

        return clusters;
    }
}


