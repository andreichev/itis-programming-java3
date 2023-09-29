package ru.itis.database2;

import java.util.List;

public interface UsersRepository extends CrudRepository<User, Integer> {
    List<User> getByCourseName(String courseName);
}
