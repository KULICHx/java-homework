package edu.hw6.task5;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.regex.Pattern;

public class HackerNews {
    private static final HttpClient HTTP_CLIENT = HttpClient.newHttpClient();

    private HackerNews() {
    }

    public static long[] hackerNewsTopStories() {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://hacker-news.firebaseio.com/v0/topstories.json"))
            .GET()
            .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body().trim();

            if (!json.startsWith("[") || !json.endsWith("]")) {
                return new long[0];
            }

            String[] parts = json.substring(1, json.length() - 1).split(",");
            long[] numbers = new long[parts.length];

            for (int i = 0; i < parts.length; i++) {
                numbers[i] = Long.parseLong(parts[i].trim());
            }
            return numbers;
        } catch (Exception e) {
            return new long[0];
        }
    }

    public static String news(long id) {
        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://hacker-news.firebaseio.com/v0/item/" + id + ".json"))
            .GET()
            .build();

        try {
            HttpResponse<String> response = HTTP_CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            String json = response.body();

            if (json == null || json.isBlank()) {
                return "";
            }

            Pattern pattern = Pattern.compile("\"title\"\\s*:\\s*\"([^\"]*)\"");
            var matcher = pattern.matcher(json);

            return matcher.find() ? matcher.group(1) : "";
        } catch (Exception e) {
            return "";
        }
    }
}
