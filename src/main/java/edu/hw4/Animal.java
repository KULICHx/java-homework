package edu.hw4;

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

    private static final int CAT_DOG_PAWS = 4;
    private static final int BIRD_PAWS = 2;
    private static final int FISH_PAWS = 0;
    private static final int SPIDER_PAWS = 8;

    public int paws() {
        return switch (type) {
            case CAT, DOG -> CAT_DOG_PAWS;
            case BIRD -> BIRD_PAWS;
            case FISH -> FISH_PAWS;
            case SPIDER -> SPIDER_PAWS;
        };
    }

}
