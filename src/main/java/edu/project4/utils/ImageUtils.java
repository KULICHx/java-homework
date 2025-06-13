package edu.project4.utils;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    private static final int RED_SHIFT = 16;
    private static final int GREEN_SHIFT = 8;
    private static final int BLUE_MASK = 0xFF;
    private static final int IMAGE_TYPE = BufferedImage.TYPE_INT_RGB;

    private ImageUtils() {
    }

    public static void save(FractalImage image, Path path, ImageFormat format) {
        if (image == null || path == null || format == null) {
            throw new IllegalArgumentException("Изображение, путь или формат не должны быть пустыми");
        }

        BufferedImage output = new BufferedImage(
            image.width(),
            image.height(),
            IMAGE_TYPE
        );

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel p = image.pixel(x, y);
                int rgb = ((p.r() & BLUE_MASK) << RED_SHIFT)
                    | ((p.g() & BLUE_MASK) << GREEN_SHIFT)
                    | (p.b() & BLUE_MASK);
                output.setRGB(x, y, rgb);
            }
        }

        try {
            ImageIO.write(output, format.name().toLowerCase(), path.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Ошибка при сохранении изображения в файл", e);
        }
    }
}
