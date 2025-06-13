package edu.project4.model;

public record Pixel(int r, int g, int b, int hitCount) {
    private static final double SMOOTHING_FACTOR = 50.0;
    private static final double RED_PHASE_SHIFT = 0.4;
    private static final double GREEN_PHASE_SHIFT = 2.0;
    private static final double BLUE_PHASE_SHIFT = 4.0;
    private static final int COLOR_AMPLITUDE = 127;
    private static final int COLOR_BASE = 128;
    private static final double ALPHA_FACTOR = 0.7;

    private static final int MIN_COLOR_VALUE = 0;
    private static final int MAX_COLOR_VALUE = 255;

    public static Pixel updatePixel(Pixel pixel, int initialHitCount) {
        final int updatedHitCount = initialHitCount + 1;

        double t = (double) updatedHitCount / (updatedHitCount + SMOOTHING_FACTOR);

        int r = calculateColorChannel(t, RED_PHASE_SHIFT);
        int g = calculateColorChannel(t, GREEN_PHASE_SHIFT);
        int b = calculateColorChannel(t, BLUE_PHASE_SHIFT);

        double alpha = ALPHA_FACTOR / Math.sqrt(updatedHitCount);
        int newR = blendColorChannel(pixel.r(), r, alpha);
        int newG = blendColorChannel(pixel.g(), g, alpha);
        int newB = blendColorChannel(pixel.b(), b, alpha);

        return new Pixel(newR, newG, newB, updatedHitCount);
    }

    private static int calculateColorChannel(double t, double phaseShift) {
        return (int) (Math.sin(2 * Math.PI * t + phaseShift) * COLOR_AMPLITUDE + COLOR_BASE);
    }

    private static int blendColorChannel(int oldValue, int newValue, double alpha) {
        return clamp((int) (oldValue * (1 - alpha) + newValue * alpha));
    }

    private static int clamp(int value) {
        return Math.max(MIN_COLOR_VALUE, Math.min(MAX_COLOR_VALUE, value));
    }
}
