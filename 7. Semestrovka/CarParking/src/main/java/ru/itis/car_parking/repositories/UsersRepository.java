package ru.itis.car_parking.repositories;

import ru.itis.car_parking.model.User;
import ru.itis.car_parking.repositories.generic.CrudRepository;

import java.util.Optional;

public interface UsersRepository extends CrudRepository<User, Integer> {
    Optional<User> findByEmail(String email);
}
