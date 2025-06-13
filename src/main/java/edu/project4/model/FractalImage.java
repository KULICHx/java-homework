package edu.project4.model;

import java.util.Arrays;

public record FractalImage(Pixel[] data, int width, int height) {

    public static FractalImage create(int width, int height) {
        Pixel[] pixels = new Pixel[width * height];
        Arrays.setAll(pixels, i -> new Pixel(0, 0, 0, 0));
        return new FractalImage(pixels, width, height);
    }

    public boolean contains(int x, int y) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }

    public Pixel pixel(int x, int y) {
        if (!contains(x, y)) {
            throw new IndexOutOfBoundsException(getOutOfBoundsMessage(x, y));
        }
        return data[y * width + x];
    }

    public void setPixel(int x, int y, Pixel pixel) {
        if (!contains(x, y)) {
            throw new IndexOutOfBoundsException(getOutOfBoundsMessage(x, y));
        }
        data[y * width + x] = pixel;
    }

    private static String getOutOfBoundsMessage(int x, int y) {
        return String.format("Координаты вне границ изображения: x=%d, y=%d", x, y);
    }

}
