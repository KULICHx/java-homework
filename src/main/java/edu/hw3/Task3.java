package edu.hw3;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
        StringBuilder sb = new StringBuilder();
        sb.append("{");

        for (HashMap.Entry<T, Integer> entry : map.entrySet()) {
            String key = (entry.getKey() == null) ? "null" : entry.getKey().toString();
            sb.append(key).append(": ").append(entry.getValue()).append(", ");
        }
        if (sb.length() > 1) {
            sb.setLength(sb.length() - 2);
        }
        sb.append("}");

        return sb.toString();
    }
}
