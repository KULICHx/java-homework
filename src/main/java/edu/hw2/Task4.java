package edu.hw2;

import java.util.Objects;

/**
 * Утилита для получения информации о вызывающем коде.
 */
public final class Task4 {
    // Константы
    private static final String UNKNOWN = "Unknown";
    private static final Throwable LAZY_THROWABLE = new Throwable();

    // Приватный конструктор
    private Task4() {
    }

    /**
     * Возвращает информацию о вызывающем методе.
     * @return информация о вызове (класс и метод)
     * @throws IllegalStateException если не удалось получить stack trace
     */
    public static CallingInfo getCallingInfo() {
        try {
            StackTraceElement[] stack = LAZY_THROWABLE.getStackTrace();
            if (stack.length >= 2) {
                StackTraceElement caller = stack[1];
                return new CallingInfo(caller.getClassName(), caller.getMethodName());
            }
            return new CallingInfo(UNKNOWN, UNKNOWN);
        } catch (Exception e) {
            throw new IllegalStateException("Failed to get stack trace", e);
        }
    }

    /**
     * Информация о вызове.
     * @param className полное имя класса
     * @param methodName имя метода
     */
    public record CallingInfo(String className, String methodName) {
        public CallingInfo {
            Objects.requireNonNull(className, "ClassName cannot be null");
            Objects.requireNonNull(methodName, "MethodName cannot be null");
        }
    }
}
