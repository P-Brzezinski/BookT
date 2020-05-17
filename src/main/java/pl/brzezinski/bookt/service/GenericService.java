package pl.brzezinski.bookt.service;

import java.util.List;

public interface GenericService<K, T> {

    T get(K id);

    void add(T obj);

    void remove(T obj);

    void deleteById(K id);

    List<T> getAll();
}
