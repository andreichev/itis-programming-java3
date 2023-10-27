package ru.itis.car_parking.repositories.generic;

import java.util.List;
import java.util.Optional;

public interface CrudRepository<T, K> {
    Optional<T> findById(K id);
    List<T> findAll();
    /// Если нет id - сохраняет новую сущность, если есть - обновляет существующую
    T save(T item);
    void delete(K id);
}
