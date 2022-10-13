package com.itis.forms_servlet_example.dao;

import java.util.List;
import java.util.Optional;

// T - Тип сущности
// K - Тип первичного ключа
public interface CrudRepository<T, K> {
    T save(T item);
    List<T> getAll();
    Optional<T> getById(K id);
    void delete(K id);
}
