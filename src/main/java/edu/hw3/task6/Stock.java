package edu.hw3.task6;

public class Stock {
    String name;
    int price;

    public Stock(String name, int price) {
        this.name = name;
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
            + "name='" + name + '\''
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
        return price == stock.price && name.equals(stock.name);
    }

    @Override
    public int hashCode() {
        return 31 * name.hashCode() + price;
    }
}
