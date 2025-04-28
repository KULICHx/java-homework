package edu.hw3.task5;

public class Contact {
    private final String fullName;
    private final String lastName;

    public Contact(String fullName, String lastName) {
        this.fullName = fullName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLastName() {
        return lastName;
    }
}
