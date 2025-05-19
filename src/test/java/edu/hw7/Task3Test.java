package edu.hw7;

import edu.hw7.task3.cache.PersonCache;
import edu.hw7.task3.model.Person;
import edu.hw7.task3.repository.PersonDatabase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class Task3Test {

    private PersonDatabase cache;

    @BeforeEach
    void setUp() {
        cache = PersonCache.create();
    }

    @Test
    @DisplayName("Добавление и поиск по имени")
    void testAddAndFindByName() {
        Person person = new Person(1, "Ivan", "Moscow", "123");
        cache.add(person);

        List<Person> found = cache.findByName("Ivan");
        assertEquals(1, found.size());
        assertEquals(person, found.getFirst());
    }

    @Test
    @DisplayName("Добавление и поиск по адресу")
    void testAddAndFindByAddress() {
        Person person = new Person(2, "Petr", "Saint Petersburg", "456");
        cache.add(person);

        List<Person> found = cache.findByAddress("Saint Petersburg");
        assertEquals(1, found.size());
        assertEquals(person, found.getFirst());
    }

    @Test
    @DisplayName("Добавление и поиск по телефону")
    void testAddAndFindByPhone() {
        Person person = new Person(3, "Anna", "Kazan", "789");
        cache.add(person);

        List<Person> found = cache.findByPhone("789");
        assertEquals(1, found.size());
        assertEquals(person, found.getFirst());
    }

    @Test
    @DisplayName("Удаление человека")
    void testDelete() {
        Person person = new Person(4, "Olga", "Novosibirsk", "101");
        cache.add(person);

        cache.delete(4);

        assertTrue(cache.findByName("Olga").isEmpty());
        assertTrue(cache.findByAddress("Novosibirsk").isEmpty());
        assertTrue(cache.findByPhone("101").isEmpty());
    }

    @Test
    @DisplayName("Исключение при добавлении null или неполного Person")
    void testAddInvalidPerson() {
        assertThrows(IllegalArgumentException.class, () -> cache.add(null));
        assertThrows(IllegalArgumentException.class, () -> cache.add(new Person(5, null, "Addr", "Phone")));
        assertThrows(IllegalArgumentException.class, () -> cache.add(new Person(6, "Name", null, "Phone")));
        assertThrows(IllegalArgumentException.class, () -> cache.add(new Person(7, "Name", "Addr", null)));
    }

    @Test
    @DisplayName("Исключение при добавлении дублирующегося Person")
    void testAddDuplicatePerson() {
        Person person = new Person(8, "Dup", "Addr", "Phone");
        cache.add(person);
        assertThrows(IllegalStateException.class, () -> cache.add(person));
    }

    @Test
    @DisplayName("Поиск с null возвращает пустой список")
    void testFindWithNull() {
        assertTrue(cache.findByName(null).isEmpty());
        assertTrue(cache.findByAddress(null).isEmpty());
        assertTrue(cache.findByPhone(null).isEmpty());
    }

    @Test
    @DisplayName("Удаление с отрицательным id бросает исключение")
    void testDeleteInvalidId() {
        assertThrows(IllegalArgumentException.class, () -> cache.delete(-1));
    }

    @Test
    @DisplayName("Потокобезопасность при параллельном добавлении и поиске")
    void testConcurrentAddAndFind() throws InterruptedException {
        int threadCount = 10;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);

        // Добавляем 1000 уникальных person
        IntStream.range(0, 1000).forEach(i ->
            cache.add(new Person(i, "Name" + i, "Addr" + i, "Phone" + i))
        );

        CountDownLatch latch = new CountDownLatch(threadCount);

        Runnable task = () -> {
            for (int i = 0; i < 1000; i++) {
                assertFalse(cache.findByName("Name" + i).isEmpty());
                assertFalse(cache.findByAddress("Addr" + i).isEmpty());
                assertFalse(cache.findByPhone("Phone" + i).isEmpty());
            }
            latch.countDown();
        };

        for (int i = 0; i < threadCount; i++) {
            executor.submit(task);
        }

        latch.await();
        executor.shutdownNow();

        int foundCount = 0;
        for (int i = 0; i < 1000; i++) {
            foundCount += cache.findByName("Name" + i).size();
        }

        assertEquals(1000, foundCount);
    }

}
