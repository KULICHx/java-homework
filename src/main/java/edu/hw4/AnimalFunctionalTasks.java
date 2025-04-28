package edu.hw4;

import edu.hw4.errors.AnimalValidator;
import edu.hw4.errors.ValidationError;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalFunctionalTasks {
    private AnimalFunctionalTasks() {

    }

    public static final int MAX_AGE = 10;
    public static final int DANGER_HEIGHT = 100;

    // 1. Сортировка животных по росту (по возрастанию)
    public static List<Animal> sortByHeight(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparingInt(Animal::height)).toList();
    }

    // 2. Топ-K самых тяжелых животных
    public static List<Animal> getTopKHeaviest(List<Animal> animals, int k) {
        if (k <= 0) {
            return List.of();
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

        return sexCount.getOrDefault(Animal.Sex.F, 0L) > sexCount.getOrDefault(Animal.Sex.M, 0L)
            ? Animal.Sex.F
            : Animal.Sex.M;
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
        return animals.stream()
            .sorted(Comparator.comparing(Animal::age).reversed())
            .skip(k - 1)
            .findFirst()
            .orElse(null);
    }

    // 8. Находит самое тяжелое животное ниже K см
    public static Animal findHeaviestBelowKcm(List<Animal> animals, int k) {
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
        return Math.toIntExact(animals.stream().filter(animal -> animal.weight() > animal.height()).count());
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
    public static Map<Animal.Type, Integer> getWeightSumByTypeAndAge(List<Animal> animals, int k) {
        return animals.stream().filter(animal -> animal.age() > k && animal.age() < MAX_AGE)
            .collect(Collectors.groupingBy(Animal::type, Collectors.summingInt(Animal::weight)));
    }

    // 16. Сортировка по нескольким полям
    public static List<Animal> getAnimalsSortedByMultipleFields(List<Animal> animals) {
        return animals.stream().sorted(Comparator.comparing(Animal::type))
            .sorted(Comparator.comparing(Animal::sex))
            .sorted(Comparator.comparing(Animal::name))
            .toList();
    }

    // 17. Сравнивает частоту укусов пауков и собак
    public static Boolean isSpiderBiteRateHigher(List<Animal> animals) {
        int bitesSpider =
            animals.stream()
                .filter(animal -> animal.type() == Animal.Type.SPIDER)
                .mapToInt(animal -> animal.bites() ? 1 : 0)
                .sum();

        int bitesDog =
            animals.stream()
                .filter(animal -> animal.type() == Animal.Type.DOG)
                .mapToInt(animal -> animal.bites() ? 1 : 0)
                .sum();

        if (bitesSpider == 0 && bitesDog == 0) {
            return false;
        }

        return bitesSpider > bitesDog;
    }

    // 18. Ищет тяжелую рыбу в нескольких коллекциях
    public static Animal getHeaviestFishInMultipleCollections(List<Animal> firstAnimals, List<Animal> secondAnimals) {
        Stream<Animal> combinedStream = Stream.concat(firstAnimals.stream(), secondAnimals.stream());
        return combinedStream.filter(animal -> animal.type() == Animal.Type.FISH)
            .max(Comparator.comparing(Animal::weight)).orElse(null);
    }

    // 19. Находит записи с ошибками данных
    public static Map<String, Set<ValidationError>> getAnimalsWithDataErrors(List<Animal> animals) {
        Map<String, Set<ValidationError>> allErrors = AnimalValidator.validateAnimals(animals);

        Map<String, Set<ValidationError>> animalsWithErrors = new HashMap<>();

        for (Map.Entry<String, Set<ValidationError>> entry : allErrors.entrySet()) {
            if (!entry.getValue().isEmpty()) {
                animalsWithErrors.put(entry.getKey(), entry.getValue());
            }
        }

        return animalsWithErrors;
    }

    // 20. Возвращает отформатированные ошибки данных
    public static Map<String, String> getFormattedDataErrors(List<Animal> animals) {
        Map<String, Set<ValidationError>> allErrors = AnimalValidator.validateAnimals(animals);

        Map<String, String> animalsWithErrors = new HashMap<>();

        for (Map.Entry<String, Set<ValidationError>> entry : allErrors.entrySet()) {
            String name = entry.getKey();
            Set<ValidationError> errorSet = entry.getValue();

            String errors = errorSet.stream()
                .map(error -> error.errorType().name())
                .collect(Collectors.joining(", "));

            animalsWithErrors.put(name, errors);
        }

        return animalsWithErrors;
    }

}
