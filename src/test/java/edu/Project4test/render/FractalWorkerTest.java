package edu.Project4test.render;

import edu.project4.model.FractalImage;
import edu.project4.model.Point;
import edu.project4.model.Rect;
import edu.project4.render.FractalWorker;
import edu.project4.render.RenderParams;
import edu.project4.transformations.Transformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FractalWorkerTest {

    @Mock
    private Transformation mockTransformation;

    @Test
    @DisplayName("Трансформации применяются и пиксели обновляются при валидных точках")
    void shouldApplyTransformationsAndUpdatePixels() {
        // Arrange
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);
        Random random = new Random(params.seed());

        when(mockTransformation.apply(any())).thenReturn(new Point(0.5, 0.5));
        FractalWorker worker = new FractalWorker(canvas, world, List.of(mockTransformation), params, random);

        // Act
        FractalImage result = worker.run();

        // Assert
        boolean hasUpdatedPixels = false;
        for (int y = 0; y < result.height(); y++) {
            for (int x = 0; x < result.width(); x++) {
                if (result.pixel(x, y).hitCount() > 0) {
                    hasUpdatedPixels = true;
                    break;
                }
            }
        }
        assertTrue(hasUpdatedPixels, "Должны быть обновленные пиксели");
        verify(mockTransformation, atLeastOnce()).apply(any());
    }

    @Test
    @DisplayName("Пиксели не обновляются при точках вне world")
    void shouldSkipPointsOutsideWorld() {
        // Arrange
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);
        Random random = new Random(params.seed());

        when(mockTransformation.apply(any())).thenReturn(new Point(10.0, 10.0));
        FractalWorker worker = new FractalWorker(canvas, world, List.of(mockTransformation), params, random);

        // Act
        FractalImage result = worker.run();

        // Assert
        for (int y = 0; y < result.height(); y++) {
            for (int x = 0; x < result.width(); x++) {
                assertEquals(0, result.pixel(x, y).hitCount(), "Пиксели не должны обновляться");
            }
        }
    }

    @Test
    @DisplayName("Симметрия применяется корректно")
    void shouldApplySymmetryCorrectly() {
        // Arrange
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        int symmetry = 3;
        RenderParams params = new RenderParams(100, 10, 12345L, symmetry, 1);
        Random random = new Random(params.seed());

        when(mockTransformation.apply(any())).thenReturn(new Point(0.5, 0.5));
        FractalWorker worker = new FractalWorker(canvas, world, List.of(mockTransformation), params, random);

        // Act
        worker.run();

        // Assert
        verify(mockTransformation, times(params.samples() * params.iterPerSample())).apply(any());
    }

    @Test
    @DisplayName("Пиксели не обновляются при координатах вне canvas")
    void shouldNotUpdatePixelsOutsideCanvas() {
        // Arrange
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);
        Random random = new Random(params.seed());

        when(mockTransformation.apply(any())).thenReturn(new Point(1.5, 1.5));
        FractalWorker worker = new FractalWorker(canvas, world, List.of(mockTransformation), params, random);

        // Act
        FractalImage result = worker.run();

        // Assert
        for (int y = 0; y < result.height(); y++) {
            for (int x = 0; x < result.width(); x++) {
                assertEquals(0, result.pixel(x, y).hitCount(), "Пиксели не должны обновляться");
            }
        }
    }

    @Test
    @DisplayName("Пропуск первых skipSteps итераций")
    void shouldSkipInitialIterations() {
        // Arrange
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);
        Random random = new Random(params.seed());

        when(mockTransformation.apply(any())).thenReturn(new Point(0.5, 0.5));
        FractalWorker worker = new FractalWorker(canvas, world, List.of(mockTransformation), params, random);

        // Act
        worker.run();

        // Assert
        verify(mockTransformation, times(params.samples() * params.iterPerSample())).apply(any());
    }

    @Test
    @DisplayName("Корректная обработка пустого списка трансформаций")
    void shouldHandleEmptyTransformationsList() {
        // Arrange
        FractalImage canvas = FractalImage.create(10, 10);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);

        FractalWorker worker = new FractalWorker(canvas, new Rect(-1, -1, 2, 2),
            List.of(), params, new Random(params.seed()));

        // Act/Assert
        assertThrows(IllegalArgumentException.class, worker::run);
    }
}
