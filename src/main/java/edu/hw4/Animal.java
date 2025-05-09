package edu.hw4;

import java.util.HashSet;
import java.util.Set;

public record Animal(
    String name,
    Type type,
    Sex sex,
    int age,
    int height,
    int weight,
    boolean bites
) {
    enum Type {
        CAT,
        DOG,
        BIRD,
        FISH,
        SPIDER
    }

    enum Sex {
        M,
        F
    }

    // Константы для лап
    private static final int CAT_DOG_PAWS = 4;
    private static final int BIRD_PAWS = 2;
    private static final int FISH_PAWS = 0;
    private static final int SPIDER_PAWS = 8;

    // Константы для валидации
    private static final int MAX_AGE = 100;
    private static final int MIN_HEIGHT = 1;
    private static final int MIN_WEIGHT = 1;

    public int paws() {
        return switch (type) {
            case CAT, DOG -> CAT_DOG_PAWS;
            case BIRD -> BIRD_PAWS;
            case FISH -> FISH_PAWS;
            case SPIDER -> SPIDER_PAWS;
        };
    }

    public Set<ValidationError> validate() {
        Set<ValidationError> errors = new HashSet<>();

        if (name == null || name.isBlank()) {
            errors.add(new ValidationError("name", "Имя не может быть пустым"));
        }

        if (age < 0 || age > MAX_AGE) {
            errors.add(new ValidationError("age", "Возраст должен быть от 0 до " + MAX_AGE));
        }

        if (height < MIN_HEIGHT) {
            errors.add(new ValidationError("height", "Рост должен быть положительным"));
        }

        if (weight < MIN_WEIGHT) {
            errors.add(new ValidationError("weight", "Вес должен быть положительным"));
        }

        if (type == null) {
            errors.add(new ValidationError("type", "Тип животного должен быть указан"));
        }

        if (sex == null) {
            errors.add(new ValidationError("sex", "Пол животного должен быть указан"));
        }

        return errors;
    }

    public boolean hasErrors() {
        return !validate().isEmpty();
    }

    public record ValidationError(String field, String message) {
    }
}
