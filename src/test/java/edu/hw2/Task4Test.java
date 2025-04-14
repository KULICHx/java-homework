package edu.hw2;

import edu.hw2.Task4.CallingInfo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

public class Task4Test {

    @Test
    @DisplayName("Метод getCallingInfo возвращает корректное имя класса и метода")
    void getCallingInfo_ReturnsCorrectCaller() {
        // Arrange -> Act
        CallingInfo info = helperMethod();

        // Assert
        assertThat(info.className()).isEqualTo(this.getClass().getName());
        assertThat(info.methodName()).isEqualTo("helperMethod");
    }

    private CallingInfo helperMethod() {
        return Task4.getCallingInfo();
    }

    @Test
    @DisplayName("CallingInfo не принимает null в качестве аргументов конструктора")
    void callingInfo_ThrowsOnNullArguments() {
        assertThatThrownBy(() -> new CallingInfo(null, "method"))
            .isInstanceOf(NullPointerException.class);

        assertThatThrownBy(() -> new CallingInfo("Class", null))
            .isInstanceOf(NullPointerException.class);
    }
}
