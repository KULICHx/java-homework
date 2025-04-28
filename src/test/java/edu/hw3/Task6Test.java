package edu.hw3;

import edu.hw3.task6.Stock;
import edu.hw3.task6.StockMarket;
import edu.hw3.task6.StockMarketImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class Task6Test {

    @Test
    @DisplayName("Добавление одной акции и получение самой дорогой")
    void addOneStockReturnsThatStockAsMostValuable() {
        // Arrange
        StockMarket market = new StockMarketImpl();
        Stock stock = new Stock("AAPL", 150);

        // Act
        market.add(stock);
        Stock mostValuable = market.mostValuableStock();

        // Assert
        assertThat(mostValuable).isEqualTo(stock);
    }

    @Test
    @DisplayName("Добавление нескольких акций и получение самой дорогой")
    void addMultipleStocksReturnsMostExpensive() {
        // Arrange
        StockMarket market = new StockMarketImpl();
        Stock stock1 = new Stock("AAPL", 150);
        Stock stock2 = new Stock("GOOG", 2800);
        Stock stock3 = new Stock("MSFT", 300);

        // Act
        market.add(stock1);
        market.add(stock2);
        market.add(stock3);
        Stock mostValuable = market.mostValuableStock();

        // Assert
        assertThat(mostValuable).isEqualTo(stock2);
    }

    @Test
    @DisplayName("Удаление самой дорогой акции меняет результат mostValuableStock")
    void removeMostValuableUpdatesResult() {
        // Arrange
        StockMarket market = new StockMarketImpl();
        Stock expensive = new Stock("TSLA", 1000);
        Stock other = new Stock("NFLX", 600);
        market.add(expensive);
        market.add(other);

        // Act
        market.remove(expensive);
        Stock mostValuable = market.mostValuableStock();

        // Assert
        assertThat(mostValuable).isEqualTo(other);
    }

    @Test
    @DisplayName("Удаление акции, которой нет в очереди, не вызывает исключений")
    void removeNonExistentStockDoesNothing() {
        // Arrange
        StockMarket market = new StockMarketImpl();
        Stock stock = new Stock("AAPL", 150);
        Stock fake = new Stock("FAKE", 999);
        market.add(stock);

        // Act & Assert
        assertThatCode(() -> market.remove(fake)).doesNotThrowAnyException();
        assertThat(market.mostValuableStock()).isEqualTo(stock);
    }

    @Test
    @DisplayName("mostValuableStock() на пустом рынке возвращает null")
    void mostValuableOnEmptyMarketReturnsNull() {
        // Arrange
        StockMarket market = new StockMarketImpl();

        // Act
        Stock result = market.mostValuableStock();

        // Assert
        assertThat(result).isNull();
    }
}
