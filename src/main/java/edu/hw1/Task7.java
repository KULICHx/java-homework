package edu.hw1;

public class Task7 {
    private Task7() {
    }

    public static int rotateRight(int n, int shift) {
        int normalizedShift = calculateNormalizedShift(n, shift);
        int rightShifted = n >>> normalizedShift;
        int wrappedBits = n << (getBitLength(n) - normalizedShift);

        return (rightShifted | wrappedBits) & getBitMask(n);
    }

    public static int rotateLeft(int n, int shift) {
        int normalizedShift = calculateNormalizedShift(n, shift);
        int leftShifted = n << normalizedShift;
        int wrappedBits = n >>> (getBitLength(n) - normalizedShift);

        return (leftShifted | wrappedBits) & getBitMask(n);
    }

    private static int getBitLength(int n) {
        return Integer.bitCount(n) == 0 ? 1 : Integer.SIZE - Integer.numberOfLeadingZeros(n);
    }

    private static int calculateNormalizedShift(int n, int shift) {
        int bitLength = getBitLength(n);
        return shift % bitLength;
    }

    private static int getBitMask(int n) {
        return (1 << getBitLength(n)) - 1;
    }
}
