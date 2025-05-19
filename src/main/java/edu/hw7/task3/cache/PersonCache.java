package edu.hw7.task3.cache;

import edu.hw7.task3.model.Person;
import edu.hw7.task3.repository.PersonDatabase;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class PersonCache implements PersonDatabase {
    private PersonCache() {

    }

    public static PersonCache create() {
        return new PersonCache();
    }

    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    private final Map<Integer, Person> storage = new HashMap<>();
    private final Map<String, List<Person>> nameIndex = new HashMap<>();
    private final Map<String, List<Person>> addressIndex = new HashMap<>();
    private final Map<String, List<Person>> phoneIndex = new HashMap<>();

    @Override
    public void add(Person person) {
        if (person == null || person.name() == null || person.address() == null
            || person.phoneNumber() == null) {
            throw new IllegalArgumentException("n должно быть неотрицательным, все поля person должны быть ненулевыми");
        }
        lock.writeLock().lock();
        try {
            if (storage.containsKey(person.id())) {
                throw new IllegalStateException("Person уже существует");
            }
            storage.put(person.id(), person);
            updateIndex(nameIndex, person.name(), person);
            updateIndex(addressIndex, person.address(), person);
            updateIndex(phoneIndex, person.phoneNumber(), person);
        } catch (IllegalStateException e) {
            storage.remove(person.id());
            removeFromIndexes(person);
            throw e;
        } catch (Exception e) {
            storage.remove(person.id());
            removeFromIndexes(person);
            throw new RuntimeException("Не удалось добавить person", e);
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public void delete(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("Неверные person data");
        }
        lock.writeLock().lock();
        try {
            Person person = storage.remove(id);
            if (person != null) {
                removeFromIndexes(person);
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public List<Person> findByName(String name) {
        if (name == null) {
            return Collections.emptyList();
        }
        lock.readLock().lock();
        try {
            return nameIndex.getOrDefault(name, Collections.emptyList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByAddress(String address) {
        if (address == null) {
            return Collections.emptyList();
        }
        lock.readLock().lock();
        try {
            return addressIndex.getOrDefault(address, Collections.emptyList());
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public List<Person> findByPhone(String phone) {
        if (phone == null) {
            return Collections.emptyList();
        }
        lock.readLock().lock();
        try {
            return phoneIndex.getOrDefault(phone, Collections.emptyList());
        } finally {
            lock.readLock().unlock();
        }
    }

    private void updateIndex(Map<String, List<Person>> index, String key, Person person) {
        index.computeIfAbsent(key, k -> Collections.synchronizedList(new ArrayList<>())).add(person);
    }

    private void removeFromIndexes(Person person) {
        removeFromIndex(nameIndex, person.name(), person);
        removeFromIndex(addressIndex, person.address(), person);
        removeFromIndex(phoneIndex, person.phoneNumber(), person);
    }

    private void removeFromIndex(Map<String, List<Person>> index, String key, Person person) {
        List<Person> list = index.get(key);
        if (list != null) {
            synchronized (list) {
                list.remove(person);
                if (list.isEmpty()) {
                    index.remove(key);
                }
            }
        }
    }
}
