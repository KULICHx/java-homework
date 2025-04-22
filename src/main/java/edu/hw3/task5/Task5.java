package edu.hw3.task5;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Task5 {
    private Task5() {
    }

    public static final String DESK = "DESC";
    public static final String ASC = "ASC";


    public static List<String> parseContacts(List<String> contacts, String sortOrder) {
        if (contacts == null || contacts.isEmpty()) {
            return List.of();
        }

        // Проверка допустимых значений sortOrder
        if (sortOrder == null || !(sortOrder.equalsIgnoreCase(ASC)
            || sortOrder.equalsIgnoreCase(DESK))) {
            throw new IllegalArgumentException(
                "Параметр sortOrder должен быть 'ASC' или 'DESC'"
            );
        }

        Comparator<String> comparator = sortOrder.equalsIgnoreCase(DESK)
            ? Comparator.reverseOrder()
            : Comparator.naturalOrder();

        return contacts.stream()
            .map(name -> {
                String[] parts = name.trim().split("\\s+");
                String lastName = parts[parts.length - 1];
                return new Contact(name, lastName);
            })
            .sorted(Comparator.comparing(Contact::getLastName, comparator))
            .map(Contact::getFullName)
            .collect(Collectors.toList());
    }
}
