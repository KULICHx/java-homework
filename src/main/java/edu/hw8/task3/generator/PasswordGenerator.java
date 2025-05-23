package edu.hw8.task3.generator;

public class PasswordGenerator {
    private static final char[] CHARSET =
        "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
    private static final int MAX_SUPPORTED_LENGTH = 6;

    private final int maxLength;
    private long currentIndex;
    private final long[] lengthOffsets;

    public PasswordGenerator(int maxLength) {
        if (maxLength < 1 || maxLength > MAX_SUPPORTED_LENGTH) {
            throw new IllegalArgumentException(
                "Password length must be between 1 and " + MAX_SUPPORTED_LENGTH
            );
        }
        this.maxLength = maxLength;
        this.currentIndex = 0;
        this.lengthOffsets = precomputeOffsets();
    }

    private long[] precomputeOffsets() {
        long[] offsets = new long[maxLength + 1];
        long sum = 0;
        for (int len = 1; len <= maxLength; len++) {
            offsets[len] = sum;
            sum += (long) Math.pow(CHARSET.length, len);
        }
        return offsets;
    }

    public synchronized String nextPassword() {
        if (!hasNext()) {
            throw new IllegalStateException("No more passwords to generate");
        }
        return indexToPassword(currentIndex++);
    }

    public synchronized boolean hasNext() {
        return currentIndex < lengthOffsets[maxLength]
            + (long) Math.pow(CHARSET.length, maxLength);
    }

    private String indexToPassword(long index) {
        int length = 1;
        while (length < maxLength && index >= lengthOffsets[length + 1]) {
            length++;
        }

        long adjustedIndex = index - lengthOffsets[length];

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.insert(0, CHARSET[(int) (adjustedIndex % CHARSET.length)]);
            adjustedIndex /= CHARSET.length;
        }

        return password.toString();
    }

    public static char[] getCharset() {
        return CHARSET;
    }

    public static int getMaxLength() {
        return MAX_SUPPORTED_LENGTH;
    }
}
