package edu.hw2;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class Task1Test {

    @Test
    @DisplayName("Constant should return its value")
    void constantEvaluate_ShouldReturnNumber() {
        // Arrange
        var constant = new Task1.Constant(5.0);

        // Act
        double result = constant.evaluate();

        // Assert
        assertThat(result).isEqualTo(5.0);
    }

    @Test
    @DisplayName("Negate should return negative value of expression")
    void negateEvaluate_ShouldReturnNegative() {
        // Arrange
        var expr = new Task1.Negate(new Task1.Constant(3));

        // Act
        double result = expr.evaluate();

        // Assert
        assertThat(result).isEqualTo(-3.0);
    }

    @Test
    @DisplayName("Addition should return sum of two expressions")
    void additionEvaluate_ShouldReturnSum() {
        // Arrange
        var expr = new Task1.Addition(
            new Task1.Constant(3),
            new Task1.Constant(7)
        );

        // Act
        double result = expr.evaluate();

        // Assert
        assertThat(result).isEqualTo(10.0);
    }

    @Test
    @DisplayName("Multiplication should return product of two expressions")
    void multiplicationEvaluate_ShouldReturnProduct() {
        // Arrange
        var expr = new Task1.Multiplication(
            new Task1.Constant(2),
            new Task1.Constant(5)
        );

        // Act
        double result = expr.evaluate();

        // Assert
        assertThat(result).isEqualTo(10.0);
    }

    @Test
    @DisplayName("Exponent should return number raised to power")
    void exponentEvaluate_ShouldReturnPower() {
        // Arrange
        var expr = new Task1.Exponent(
            new Task1.Constant(3),
            2
        );

        // Act
        double result = expr.evaluate();

        // Assert
        assertThat(result).isEqualTo(9.0);
    }

    @Test
    @DisplayName("Complex expression evaluation")
    void complexExpressionEvaluate_ShouldMatchExpectedResult() {
        // Arrange
        var two = new Task1.Constant(2);
        var four = new Task1.Constant(4);
        var negOne = new Task1.Negate(new Task1.Constant(1));
        var sumTwoFour = new Task1.Addition(two, four);
        var mult = new Task1.Multiplication(sumTwoFour, negOne);
        var exp = new Task1.Exponent(mult, 2);
        var res = new Task1.Addition(exp, new Task1.Constant(1));

        // Act
        double result = res.evaluate();

        // Assert
        // ((2 + 4) * -1)^2 + 1 = (-6)^2 + 1 = 36 + 1 = 37
        assertThat(result).isEqualTo(37.0);
    }
}

