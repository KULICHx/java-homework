package edu.hw5;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class Task5Test {

    @Test
    @DisplayName("Валидный номер: А123ВЕ777")
    void validNumber1() {
        String number = "А123ВЕ777";
        boolean result = Task5.trafficSignValidator(number);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Валидный номер: О777ОО177")
    void validNumber2() {
        String number = "О777ОО177";
        boolean result = Task5.trafficSignValidator(number);
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("Невалидный номер: начинается с цифры")
    void invalidNumber1() {
        String number = "123АВЕ777";
        boolean result = Task5.trafficSignValidator(number);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Невалидный номер: недопустимая буква")
    void invalidNumber2() {
        String number = "А123ВГ77";
        boolean result = Task5.trafficSignValidator(number);
        assertThat(result).isFalse();
    }

    @Test
    @DisplayName("Невалидный номер: слишком много цифр в конце")
    void invalidNumber3() {
        String number = "А123ВЕ7777";
        boolean result = Task5.trafficSignValidator(number);
        assertThat(result).isFalse();
    }
}
