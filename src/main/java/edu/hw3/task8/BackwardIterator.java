package edu.hw3.task8;

import java.util.Iterator;
import java.util.List;

public class BackwardIterator<T> implements Iterator<T> {
    private final List<T> list;
    private int currentIndex;

    public BackwardIterator(List<T> list) {
        this.list = list;
        this.currentIndex = list.size() - 1; // начинаем с последнего элемента
    }

    @Override
    public boolean hasNext() {
        return currentIndex >= 0;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new java.util.NoSuchElementException("No more elements");
        }
        return list.get(currentIndex--);
    }
}
