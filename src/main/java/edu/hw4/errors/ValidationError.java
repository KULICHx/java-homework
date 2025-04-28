package edu.hw4.errors;

/**
 * @param errorType   Тип ошибки
 * @param description Описание ошибки
 */
public record ValidationError(ValidationError.ErrorType errorType, String description) {

    @Override
    public String toString() {
        return "ValidationError{"
            + "errorType=" + errorType
            + ", description='" + description
            + '\''
            + '}';
    }

    public enum ErrorType {
        INVALID_NAME,
        NEGATIVE_WEIGHT,
        INVALID_TYPE,
        AGE_OUT_OF_BOUNDS,
        MISSING_FIELD
    }
}
