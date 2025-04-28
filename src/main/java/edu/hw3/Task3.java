package edu.hw3;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

public class Task3 {
    private Task3() {
    }

    public static <T> String freqDict(List<T> list) {
        LinkedHashMap<T, Integer> map = new LinkedHashMap<>();

        for (T element : list) {
            map.put(element, map.getOrDefault(element, 0) + 1);
        }

        return formatFreqDict(map);
    }

    public static <T> String formatFreqDict(LinkedHashMap<T, Integer> map) {
        return map.entrySet().stream()
            .map(entry -> {
                String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();
                return key + ": " + entry.getValue();
            })
            .collect(Collectors.joining(", ", "{", "}"));
    }
}
