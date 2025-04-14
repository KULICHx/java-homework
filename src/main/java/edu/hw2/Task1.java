package edu.hw2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Task1 {
    private static final Logger LOGGER = LogManager.getLogger();

    private static final int BASE_VALUE = 1;
    private static final int FIRST_OPERAND = 2;
    private static final int SECOND_OPERAND = 4;
    private static final int POWER = 2;

    private Task1() {
    }

    public sealed interface Expr {
        double evaluate();
    }

    public record Constant(double number) implements Expr {

        @Override
        public double evaluate() {
            return number;
        }
    }

    public record Negate(Expr number) implements Expr {

        @Override
        public double evaluate() {
            return -number.evaluate();
        }
    }

    public record Exponent(Expr number, int degree) implements Expr {

        @Override
        public double evaluate() {
            return Math.pow(number.evaluate(), degree);
        }
    }

    public record Addition(Expr firstNumber, Expr secondNumber) implements Expr {

        @Override
        public double evaluate() {
            return firstNumber.evaluate() + secondNumber.evaluate();
        }
    }

    public record Multiplication(Expr firstNumber, Expr secondNumber) implements Expr {

        @Override
        public double evaluate() {
            return firstNumber.evaluate() * secondNumber.evaluate();
        }
    }

}
