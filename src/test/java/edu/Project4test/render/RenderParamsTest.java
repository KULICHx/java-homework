package edu.Project4test.render;

import edu.project4.render.RenderParams;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.*;

class RenderParamsTest {

    @Test
    @DisplayName("Создание с корректными параметрами")
    void shouldCreateWithCorrectParameters() {
        // Arrange & Act
        RenderParams params = new RenderParams(1000, 50, 123L, 3, 10);

        // Assert
        assertThat(params.samples()).isEqualTo(1000);
        assertThat(params.iterPerSample()).isEqualTo(50);
        assertThat(params.seed()).isEqualTo(123L);
        assertThat(params.symmetry()).isEqualTo(3);
        assertThat(params.skipSteps()).isEqualTo(10);
    }

    @Test
    @DisplayName("Метод withSamples должен создавать новый объект с обновленным samples")
    void shouldCreateNewInstanceWithUpdatedSamples() {
        // Arrange
        RenderParams original = new RenderParams(1000, 50, 123L, 3, 10);

        // Act
        RenderParams updated = original.withSamples(2000);

        // Assert
        assertThat(updated.samples()).isEqualTo(2000);
        assertThat(updated.iterPerSample()).isEqualTo(50); // остальные параметры не изменились
        assertThat(updated.seed()).isEqualTo(123L);
        assertThat(updated.symmetry()).isEqualTo(3);
        assertThat(updated.skipSteps()).isEqualTo(10);

        // Проверяем что оригинальный объект не изменился
        assertThat(original.samples()).isEqualTo(1000);
    }

    @Test
    @DisplayName("Должен корректно работать equals/hashCode")
    void shouldHaveCorrectEqualsAndHashCode() {
        // Arrange
        RenderParams params1 = new RenderParams(1000, 50, 123L, 3, 10);
        RenderParams params2 = new RenderParams(1000, 50, 123L, 3, 10);
        RenderParams params3 = new RenderParams(2000, 50, 123L, 3, 10);

        // Assert
        assertThat(params1)
            .isEqualTo(params2)
            .isNotEqualTo(params3)
            .hasSameHashCodeAs(params2);
    }
}
