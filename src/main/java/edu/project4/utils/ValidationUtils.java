package edu.project4.utils;

import edu.project4.model.FractalImage;
import edu.project4.model.Rect;
import edu.project4.transformations.Transformation;
import java.util.List;

public final class ValidationUtils {
    private ValidationUtils() {
    }

    public static void validateRenderParams(
        FractalImage canvas,
        Rect world,
        List<Transformation> variations,
        int symmetry
    ) {
        if (canvas == null || world == null || variations == null || variations.isEmpty()) {
            throw new IllegalArgumentException(
                "Параметры Canvas, world и variations не должны быть нулевыми или пустыми");
        }
        if (symmetry <= 0) {
            throw new IllegalArgumentException("Симметрия должна быть положительной");
        }
    }
}
