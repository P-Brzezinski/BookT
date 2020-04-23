package pl.brzezinski.bookt.service;

import java.util.List;

public interface GenericRepository<K, T> {

    T get(K id);

    void add(T obj);

    void remove(T obj);

    List<T> getAll();
}
