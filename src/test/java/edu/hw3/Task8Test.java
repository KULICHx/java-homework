package edu.hw3;

import edu.hw3.task8.BackwardIterator;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.assertj.core.api.Assertions.*;

class Task8Test {

    @Test
    void iteratorShouldReturnElementsInReverseOrder() {
        // Arrange
        List<Integer> list = List.of(1, 2, 3);
        BackwardIterator<Integer> iterator = new BackwardIterator<>(list);

        // Act & Assert
        assertThat(iterator.next()).isEqualTo(3);
        assertThat(iterator.next()).isEqualTo(2);
        assertThat(iterator.next()).isEqualTo(1);
    }

    @Test
    void hasNextShouldReturnFalseWhenIteratorExceedsBounds() {
        // Arrange
        List<Integer> list = List.of(1, 2, 3);
        BackwardIterator<Integer> iterator = new BackwardIterator<>(list);

        // Act
        iterator.next();
        iterator.next();
        iterator.next();

        // Assert
        assertThat(iterator.hasNext()).isFalse();  // Нет следующего элемента
    }

    @Test
    void nextShouldThrowExceptionWhenNoElementsLeft() {
        // Arrange
        List<Integer> list = List.of(1, 2, 3);
        BackwardIterator<Integer> iterator = new BackwardIterator<>(list);

        // Act
        iterator.next();
        iterator.next();
        iterator.next();

        // Assert
        assertThatThrownBy(iterator::next)
            .isInstanceOf(java.util.NoSuchElementException.class)
            .hasMessage("No more elements");
    }
}
