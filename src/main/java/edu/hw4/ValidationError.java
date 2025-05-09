package edu.hw4;

public record ValidationError(String field, String error) {

    @Override
    public String toString() {
        return String.format("%s: %s", field, error);
    }
}
