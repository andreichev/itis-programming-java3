package ru.itis.database2;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    Optional<T> findById(K id);
    List<T> findAll();
    T save(T item);
    void delete(K id);
}
