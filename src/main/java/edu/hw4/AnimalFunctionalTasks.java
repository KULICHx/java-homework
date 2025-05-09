package edu.hw4;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;


public class    AnimalFunctionalTasks {
    private AnimalFunctionalTasks() {

    }

    public static final int DANGER_HEIGHT = 100;

    // 1. Сортировка животных по росту (по возрастанию)
    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparingInt(Animal::height)).toList();
    }

    // 2. Топ-K самых тяжелых животных
    public static List<Animal> getTopKHeaviest(List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("Параметр k должен быть положительным числом, получено: " + k);
        }

        return animals.stream()
            .sorted(Comparator.comparingInt(Animal::weight).reversed())
            .limit(k)
            .collect(Collectors.toList());
    }

    // 3. Подсчитывает количество животных каждого вида
    public static Map<Animal.Type, Integer> countByType(List<Animal> animals) {
        return animals.stream().collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(a -> 1)));
    }

    // 4. Находит животное с самым длинным именем
    public static Animal findWithLongestName(List<Animal> animals) {
        return animals.stream().max(Comparator.comparing(animal -> animal.name().length())).orElse(null);
    }

    // 5. Определяет, каких животных больше: самцов или самок
    public static Animal.Sex determineDominantSex(List<Animal> animals) {
        Map<Animal.Sex, Long> sexCount = animals.stream()
            .collect(Collectors.groupingBy(Animal::sex, Collectors.counting()));

        long females = sexCount.getOrDefault(Animal.Sex.F, 0L);
        long males = sexCount.getOrDefault(Animal.Sex.M, 0L);

        if (females == males) {
            throw new IllegalStateException("Невозможно определить доминирующий пол: равное количество");
        }
        return females > males ? Animal.Sex.F : Animal.Sex.M;
    }

    // 6. Находит самое тяжелое животное для каждого вида
    public static Map<Animal.Type, Animal> findHeaviestByType(List<Animal> animals) {
        return animals.stream()
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.collectingAndThen(
                    Collectors.maxBy(Comparator.comparing(Animal::weight)),
                    optional -> optional.orElse(null)
                )
            ));
    }

    // 7. Находит K-е по возрасту животное
    public static Animal findKthOldest(List<Animal> animals, int k) {

        if (k <= 0) {
            throw new IllegalArgumentException("Параметр k должен быть положительным числом. Получено: " + k);
        }
        if (k > animals.size()) {
            throw new IllegalArgumentException(
                String.format("Запрошенный индекс (%d) превышает размер списка (%d)", k, animals.size())
            );
        }
        return animals.stream()
            .sorted(Comparator.comparing(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    // 8. Находит самое тяжелое животное ниже K см
    public static Animal findHeaviestBelowKcm(List<Animal> animals, int k) {
        if (k <= 0) {
            throw new IllegalArgumentException("K должен быть больше 0. Получено: " + k);
        }
        return animals.stream().filter(animal -> animal.height() < k)
            .max(Comparator.comparing(Animal::weight)).orElse(null);
    }

    // 9. Вычисляет суммарное количество лап
    public static Integer getTotalPawsCount(List<Animal> animals) {
        return animals.stream().mapToInt(Animal::paws).sum();
    }

    // 10. Находит животных с несовпадающими возрастом и количеством лап
    public static List<Animal> getAnimalsWithAgePawsMismatch(List<Animal> animals) {
        return animals.stream().filter(animal -> animal.age() != animal.paws()).toList();
    }

    // 11. Получает высоких кусачих животных
    public static List<Animal> getDangerousTallAnimals(List<Animal> animals) {
        return animals.stream().filter(Animal::bites).filter(animal -> animal.height() > DANGER_HEIGHT).toList();
    }

    // 12. Считает животных тяжелее их роста
    public static Integer getCountOfHeavyTallAnimals(List<Animal> animals) {
        return (int) (animals.stream().filter(animal -> animal.weight() > animal.height()).count());
    }

    // 13. Находит животных с составными именами
    public static List<Animal> getAnimalsWithComplexNames(List<Animal> animals) {
        return animals.stream().filter(animal -> {
            String[] words = animal.name().trim().split(" ");
            return words.length > 2;
        }).toList();
    }

    // 14. Проверяет наличие высоких собак
    public static Boolean containsTallDog(List<Animal> animals, int k) {
        return animals.stream()
            .anyMatch(animal -> animal.type() == Animal.Type.DOG && animal.height() > k);
    }

    // 15. Считает суммарный вес по видам в возрастном диапазоне
    public static Map<Animal.Type, Integer> getWeightSumByTypeAndAge(List<Animal> animals, int minAge, int maxAge) {
        return animals.stream().filter(animal -> animal.age() > minAge && animal.age() < maxAge)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    // 16. Сортировка по нескольким полям
    public static List<Animal> getAnimalsSortedByMultipleFields(List<Animal> animals) {
        if (animals == null) {
            return List.of();
        }

        return animals.stream()
            .sorted(Comparator
                .comparing(Animal::type)
                .thenComparing(Animal::sex)
                .thenComparing(Animal::name)
            )
            .toList();
    }

    // 17. Сравнивает частоту укусов пауков и собак
    public static Boolean isSpiderBiteRateHigher(List<Animal> animals) {
        if (animals == null || animals.isEmpty()) {
            return false;
        }

        Map<Animal.Type, Integer> biteCounts = animals.stream()
            .filter(animal -> animal.type() == Animal.Type.SPIDER || animal.type() == Animal.Type.DOG)
            .collect(Collectors.groupingBy(
                Animal::type,
                Collectors.summingInt(animal -> animal.bites() ? 1 : 0)
            ));

        int spiderBites = biteCounts.getOrDefault(Animal.Type.SPIDER, 0);
        int dogBites = biteCounts.getOrDefault(Animal.Type.DOG, 0);

        if (spiderBites == 0 && dogBites == 0) {
            return false;
        }

        return spiderBites > dogBites;
    }

    // 18. Ищет тяжелую рыбу в нескольких коллекциях
    public static Animal getHeaviestFishInMultipleCollections(List<List<Animal>> animalsLists) {
        if (animalsLists == null || animalsLists.isEmpty()) {
            return null;
        }

        return animalsLists.stream()
            .filter(Objects::nonNull)
            .flatMap(List::stream)
            .filter(animal -> animal != null && animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight))
            .orElse(null);
    }

    // 19. Находит записи с ошибками данных (с защитой от null)
    public static @NotNull Map<String, @NotNull Set<Animal.ValidationError>>
    getAnimalsWithDataErrors(List<Animal> animals) {
        if (animals == null) {
            return Collections.emptyMap();
        }

        return animals.stream()
            .filter(Objects::nonNull)
            .collect(Collectors.toMap(
                animal -> animal.name() != null ? animal.name() : "unnamed",
                Animal::validate
            ))
            .entrySet().stream()
            .filter(entry -> !entry.getValue().isEmpty())
            .collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue()
            ));
    }

    // 20. Возвращает отформатированные ошибки данных
    public static Map<String, String> getFormattedDataErrors(List<Animal> animals) {
        return getAnimalsWithDataErrors(animals).entrySet().stream()
            .collect(Collectors.toMap(
                entry -> entry.getKey(),
                entry -> entry.getValue().stream()
                    .filter(Objects::nonNull)
                    .map(error -> String.format(
                        "%s: %s",
                        error.field() != null ? error.field() : "unknown",
                        error.message() != null ? error.message() : "validation failed"
                    ))
                    .collect(Collectors.joining("; "))
            ));
    }
}
