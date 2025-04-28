package edu.hw3.task6;

import java.util.Objects;

public class Stock {
    String name;
    int price;

    public Stock(String name, int price) {
        this.name = Objects.requireNonNull(name, "Stock name cannot be null");
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Stock{"
            + "name='" + (name != null ? name : "null") + '\''
            + ", price=" + price
            + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Stock stock = (Stock) obj;
        return price == stock.price && Objects.equals(name, stock.name);
    }

    @Override
    public int hashCode() {
        return 31 * Objects.hashCode(name) + price;
    }
}
