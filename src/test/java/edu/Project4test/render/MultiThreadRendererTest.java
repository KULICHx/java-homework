package edu.Project4test.render;

import edu.project4.model.FractalImage;
import edu.project4.model.Pixel;
import edu.project4.model.Rect;
import edu.project4.render.MultiThreadRenderer;
import edu.project4.render.RenderParams;
import edu.project4.transformations.LinearTransformation;
import edu.project4.transformations.Transformation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class MultiThreadRendererTest {

    private static final LinearTransformation TEST_TRANSFORMATION =
        new LinearTransformation(0.5, 0.5, 0.1, 0.5, 0.5, 0.1);


    @Test
    @DisplayName("Рендеринг должен корректно работать с валидными параметрами")
    void shouldRenderCorrectlyWithValidParameters() {
        // Arrange
        MultiThreadRenderer renderer = new MultiThreadRenderer();
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        List<Transformation> variations = List.of(TEST_TRANSFORMATION);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);

        // Act
        FractalImage result = renderer.render(canvas, world, variations, params);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.width()).isEqualTo(10);
        assertThat(result.height()).isEqualTo(10);
    }

    @Test
    @DisplayName("Должен корректно распределять задачи по потокам")
    void shouldDistributeTasksCorrectly() {
        // Arrange
        MultiThreadRenderer renderer = new MultiThreadRenderer();
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        List<Transformation> variations = List.of(TEST_TRANSFORMATION);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);

        // Act
        FractalImage result = renderer.render(canvas, world, variations, params);

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
        assertThat(hasUpdatedPixels).isTrue();
    }

    @Test
    @DisplayName("Должен корректно объединять результаты из разных потоков")
    void shouldMergeResultsCorrectly() {
        // Arrange
        MultiThreadRenderer renderer = new MultiThreadRenderer();
        FractalImage canvas = FractalImage.create(2, 2);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        List<Transformation> variations = List.of(TEST_TRANSFORMATION);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);

        // Act
        FractalImage result = renderer.render(canvas, world, variations, params);

        // Assert
        for (int y = 0; y < result.height(); y++) {
            for (int x = 0; x < result.width(); x++) {
                Pixel pixel = result.pixel(x, y);
                assertThat(pixel.r()).isBetween(0, 255);
                assertThat(pixel.g()).isBetween(0, 255);
                assertThat(pixel.b()).isBetween(0, 255);
            }
        }
    }

    @Test
    @DisplayName("Должен корректно обрабатывать прерывание выполнения")
    void shouldHandleInterruptionProperly() {
        // Arrange
        class TestRenderer extends MultiThreadRenderer {
            @Override public void waitForCompletion(ExecutorService executor) {
                Thread.currentThread().interrupt();
                try {
                    if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                        throw new RuntimeException("Время выполнения истекло");
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Выполнение прервано", e);
                }
            }
        }

        TestRenderer renderer = new TestRenderer();
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        List<Transformation> variations = List.of(TEST_TRANSFORMATION);
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);

        // Act & Assert
        assertThatThrownBy(() -> renderer.render(canvas, world, variations, params))
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Выполнение прервано")
            .hasCauseInstanceOf(InterruptedException.class);

        assertThat(Thread.interrupted()).isTrue();
    }

    @Test
    @DisplayName("Должен корректно работать с пустым списком трансформаций")
    void shouldHandleEmptyTransformationsList() {
        // Arrange
        MultiThreadRenderer renderer = new MultiThreadRenderer();
        FractalImage canvas = FractalImage.create(10, 10);
        Rect world = new Rect(-1.0, -1.0, 2.0, 2.0);
        List<Transformation> variations = List.of();
        RenderParams params = new RenderParams(100, 10, 12345L, 1, 1);

        // Act & Assert
        assertThatThrownBy(() -> renderer.render(canvas, world, variations, params))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
