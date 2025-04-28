package edu.hw4;

import edu.hw4.errors.ValidationError;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import java.util.*;
import static org.assertj.core.api.Assertions.assertThat;

class AnimalTasksTest {

    // Тестовые данные
    private final List<Animal> animals = List.of(
        new Animal("Барсик", Animal.Type.CAT, Animal.Sex.M, 3, 30, 5, false),
        new Animal("Рыжий кот Барсик", Animal.Type.CAT, Animal.Sex.M, 5, 115, 6, true),
        new Animal("Рекс", Animal.Type.DOG, Animal.Sex.M, 7, 120, 15, false),
        new Animal("Кеша", Animal.Type.BIRD, Animal.Sex.F, 2, 10, 1, false),
        new Animal("Немо", Animal.Type.FISH, Animal.Sex.F, 1, 5, 0, false),
        new Animal("Паук", Animal.Type.SPIDER, Animal.Sex.M, 8, 2, 0, true)
    );

    @Test
    @DisplayName("1. Сортировка животных по возрастанию роста")
    void sortByHeight() {
        // Act
        List<Animal> result = AnimalFunctionalTasks.sortByHeight(animals);

        // Assert
        assertThat(result).extracting(Animal::height)
            .containsExactly(2, 5, 10, 30, 115, 120);
    }

    @Test
    @DisplayName("2. Топ-3 самых тяжелых животных")
    void getTop3Heaviest() {
        // Act
        List<Animal> result = AnimalFunctionalTasks.getTopKHeaviest(animals, 3);

        // Assert
        assertThat(result).extracting(Animal::name)
            .containsExactly("Рекс", "Рыжий кот Барсик", "Барсик");
    }

    @Test
    @DisplayName("3. Количество животных каждого вида")
    void countByType() {
        // Act
        Map<Animal.Type, Integer> result = AnimalFunctionalTasks.countByType(animals);

        // Assert
        assertThat(result)
            .containsExactlyInAnyOrderEntriesOf(Map.of(
                Animal.Type.CAT, 2,
                Animal.Type.DOG, 1,
                Animal.Type.BIRD, 1,
                Animal.Type.FISH, 1,
                Animal.Type.SPIDER, 1
            ));
    }

    @Test
    @DisplayName("4. Животное с самым длинным именем")
    void findWithLongestName() {
        // Act
        Animal result = AnimalFunctionalTasks.findWithLongestName(animals);

        // Assert
        assertThat(result.name()).isEqualTo("Рыжий кот Барсик");
    }

    @Test
    @DisplayName("5. Определение преобладающего пола")
    void determineDominantSex() {
        // Act
        Animal.Sex result = AnimalFunctionalTasks.determineDominantSex(animals);

        // Assert
        assertThat(result).isEqualTo(Animal.Sex.M);
    }

    @Test
    @DisplayName("6. Самое тяжелое животное каждого вида")
    void findHeaviestByType() {
        // Act
        Map<Animal.Type, Animal> result = AnimalFunctionalTasks.findHeaviestByType(animals);

        // Assert
        assertThat(result)
            .hasSize(5)
            .extractingByKey(Animal.Type.CAT)
            .extracting(Animal::name)
            .isEqualTo("Рыжий кот Барсик");
    }

    @Test
    @DisplayName("7. 2-е самое старое животное")
    void find2ndOldest() {
        // Act
        Animal result = AnimalFunctionalTasks.findKthOldest(animals, 2);

        // Assert
        assertThat(result.name()).isEqualTo("Рекс");
    }

    @Test
    @DisplayName("8. Самое тяжелое животное ниже 40 см")
    void findHeaviestBelow40cm() {
        // Act
        Animal result = AnimalFunctionalTasks.findHeaviestBelowKcm(animals, 40);

        // Assert
        assertThat(result)
            .extracting(Animal::name)
            .isEqualTo("Барсик");
    }

    @Test
    @DisplayName("9. Суммарное количество лап")
    void sumPaws() {
        // Act
        int result = AnimalFunctionalTasks.getTotalPawsCount(animals);

        // Assert
        assertThat(result).isEqualTo(2 * 4 + 4 + 2 + 8);
    }

    @Test
    @DisplayName("10. Животные с несовпадающими возрастом и количеством лап")
    void getAnimalsWithAgePawsMismatch() {
        // Act
        List<Animal> result = AnimalFunctionalTasks.getAnimalsWithAgePawsMismatch(animals);

        // Assert
        assertThat(result)
            .extracting(Animal::name)
            .containsExactly("Барсик", "Рыжий кот Барсик", "Рекс", "Немо");
    }

    @Test
    @DisplayName("11. Высокие кусачие животные (рост > 100 см)")
    void getDangerousTallAnimals() {
        // Act
        List<Animal> result = AnimalFunctionalTasks.getDangerousTallAnimals(animals);

        // Assert
        assertThat(result)
            .extracting(Animal::name)
            .containsExactly("Рыжий кот Барсик");
    }

    @Test
    @DisplayName("12. Количество животных, у которых вес превышает рост")
    void getCountOfHeavyTallAnimals() {
        // Act
        Integer result = AnimalFunctionalTasks.getCountOfHeavyTallAnimals(animals);

        // Assert
        assertThat(result).isEqualTo(0);
    }

    @Test
    @DisplayName("13. Животные с составными именами (более 2 слов)")
    void getAnimalsWithComplexNames() {
        // Act
        List<Animal> result = AnimalFunctionalTasks.getAnimalsWithComplexNames(animals);

        // Assert
        assertThat(result)
            .extracting(Animal::name)
            .containsExactly("Рыжий кот Барсик");
    }

    @Test
    @DisplayName("14. Проверка наличия собак выше 40 см")
    void containsTallDog() {
        // Act
        Boolean result = AnimalFunctionalTasks.containsTallDog(animals, 40);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("15. Суммарный вес животных по видам в возрастном диапазоне (1-5 лет)")
    void getWeightSumByTypeAndAge() {
        // Act
        Map<Animal.Type, Integer> result =
            AnimalFunctionalTasks.getWeightSumByTypeAndAge(animals, 1);

        // Assert
        assertThat(result)
            .containsOnly(
                Map.entry(Animal.Type.CAT, 11),
                Map.entry(Animal.Type.DOG, 15),
                Map.entry(Animal.Type.BIRD, 1),
                Map.entry(Animal.Type.SPIDER, 0)
            );
    }

    @Test
    @DisplayName("16. Сортировка животных по виду, полу и имени")
    void getAnimalsSortedByMultipleFields() {
        // Act
        List<Animal> result =
            AnimalFunctionalTasks.getAnimalsSortedByMultipleFields(animals);

        // Assert
        assertThat(result)
            .extracting(Animal::type)
            .containsExactly(
                Animal.Type.CAT,
                Animal.Type.BIRD,
                Animal.Type.FISH,
                Animal.Type.SPIDER,
                Animal.Type.DOG,
                Animal.Type.CAT
            );
    }

    @Test
    @DisplayName("17. Проверка, что пауки кусаются чаще собак")
    void isSpiderBiteRateHigher() {
        // Act
        Boolean result = AnimalFunctionalTasks.isSpiderBiteRateHigher(animals);

        // Assert
        assertThat(result).isTrue();
    }

    @Test
    @DisplayName("18. Поиск самой тяжелой рыбы в двух списках")
    void getHeaviestFishInMultipleCollections() {
        // Arrange
        List<Animal> list1 = List.of(
            new Animal("Немо", Animal.Type.FISH, Animal.Sex.F, 1, 5, 1, false),
            new Animal("Дори", Animal.Type.FISH, Animal.Sex.F, 1, 5, 2, false)
        );
        List<Animal> list2 = List.of(
            new Animal("Золотая", Animal.Type.FISH, Animal.Sex.F, 1, 5, 3, false)
        );

        // Act
        Animal result = AnimalFunctionalTasks.getHeaviestFishInMultipleCollections(list1, list2);

        // Assert
        assertThat(result.name()).isEqualTo("Золотая");
        assertThat(result.weight()).isEqualTo(3);
    }

    @Test
    @DisplayName("19. Нахождение животных с ошибками данных")
    void getAnimalsWithDataErrors() {
        // Arrange
        List<Animal> invalidAnimals = List.of(
            new Animal("", Animal.Type.CAT, Animal.Sex.M, -1, 0, -1, true)
        );

        // Act
        Map<String, Set<ValidationError>> result =
            AnimalFunctionalTasks.getAnimalsWithDataErrors(invalidAnimals);

        // Assert
        assertThat(result.keySet()).containsExactly(""); // Пустое имя
        assertThat(result.get(""))
            .extracting(ValidationError::errorType)
            .containsExactlyInAnyOrder(
                ValidationError.ErrorType.INVALID_NAME,
                ValidationError.ErrorType.AGE_OUT_OF_BOUNDS,
                ValidationError.ErrorType.NEGATIVE_WEIGHT
            );
    }

    @Test
    @DisplayName("20. Форматирование ошибок - возвращает ошибки в читаемом формате")
    void getFormattedDataErrors_ReturnsFormattedErrors() {
        // Arrange
        List<Animal> animals = List.of(
            // Животное с ошибкой в имени и весе
            new Animal("", Animal.Type.CAT, Animal.Sex.M, 3, 30, -5, false),
            // Животное с ошибкой в возрасте
            new Animal("Рекс", Animal.Type.DOG, Animal.Sex.M, -2, 45, 20, true),
            // Валидное животное без ошибок
            new Animal("Барсик", Animal.Type.CAT, Animal.Sex.M, 5, 25, 4, false)
        );

        // Act
        Map<String, String> result = AnimalFunctionalTasks.getFormattedDataErrors(animals);

        // Assert
        assertThat(result).hasSize(2); // Два животных с ошибками

        assertThat(result.get(""))
            .contains("INVALID_NAME")
            .contains("NEGATIVE_WEIGHT")
            .containsPattern("INVALID_NAME.*NEGATIVE_WEIGHT|NEGATIVE_WEIGHT.*INVALID_NAME");

        assertThat(result.get("Рекс"))
            .isEqualTo("AGE_OUT_OF_BOUNDS");

        assertThat(result).doesNotContainKey("Барсик");
    }

}

