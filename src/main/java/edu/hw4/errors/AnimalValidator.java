package edu.hw4.errors;

import edu.hw4.Animal;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AnimalValidator {
    private AnimalValidator() {

    }

    private static final int MAX_AGE = 100;

    // Метод для проверки всех животных
    public static Map<String, Set<ValidationError>> validateAnimals(List<Animal> animals) {
        Map<String, Set<ValidationError>> animalErrors = new HashMap<>();

        for (Animal animal : animals) {
            Set<ValidationError> animalErrorsSet = new HashSet<>();

            if (animal.name() == null || animal.name().isEmpty()) {
                animalErrorsSet.add(new ValidationError(
                    ValidationError.ErrorType.INVALID_NAME,
                    "Имя не может быть пустым"
                ));
            }

            if (animal.weight() < 0) {
                animalErrorsSet.add(new ValidationError(
                    ValidationError.ErrorType.NEGATIVE_WEIGHT,
                    "Вес не может быть отрицательным"
                ));
            }

            if (animal.age() < 0 || animal.age() > MAX_AGE) {
                animalErrorsSet.add(new ValidationError(
                    ValidationError.ErrorType.AGE_OUT_OF_BOUNDS,
                    "Возраст вне допустимого диапазона"
                ));
            }

            if (!animalErrorsSet.isEmpty()) {
                animalErrors.put(animal.name(), animalErrorsSet);
            }
        }

        return animalErrors;
    }
}
