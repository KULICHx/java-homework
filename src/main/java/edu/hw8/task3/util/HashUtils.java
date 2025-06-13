package edu.hw8.task3.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

public class HashUtils {
    private HashUtils() {
    }

    public static String hash(String input) {
        try {
            byte[] bytesOfMessage = input.getBytes(StandardCharsets.UTF_8);
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytesOfMessage);

            StringBuilder hexString = new StringBuilder();
            for (byte b : digest) {
                hexString.append(String.format("%02x", b));
            }
            return hexString.toString();

        } catch (Exception e) {
            throw new RuntimeException("MD5 hashing failed", e);
        }
    }
}
