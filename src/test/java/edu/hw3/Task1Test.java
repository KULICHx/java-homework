package edu.hw3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {
    @Test
    @DisplayName("Шифр Атбаш: 1")
    void atbash1() {
        // Arrange
        String input = "Hello world!";

        // Act
        String result = Task1.atbash(input);

        // Assert
        assertThat(result).isEqualTo("Svool dliow!");
    }
    @Test
    @DisplayName("Шифр Атбаш: 2")
    void atbash2() {
        // Arrange
        String input = "Any fool can write code that a computer can understand. Good programmers write code that humans can understand. ― Martin Fowler";

        // Act
        String result = Task1.atbash(input);

        // Assert
        assertThat(result).isEqualTo("Zmb ullo xzm dirgv xlwv gszg z xlnkfgvi xzm fmwvihgzmw. Tllw kiltiznnvih dirgv xlwv gszg sfnzmh xzm fmwvihgzmw. ― Nzigrm Uldovi");
    }
    @Test
    @DisplayName("Шифр Атбаш: 3")
    void atbash3() {
        // Arrange
        String input = "AbcXYZ";

        // Act
        String result = Task1.atbash(input);

        // Assert
        assertThat(result).isEqualTo("ZyxCBA");
    }
}
