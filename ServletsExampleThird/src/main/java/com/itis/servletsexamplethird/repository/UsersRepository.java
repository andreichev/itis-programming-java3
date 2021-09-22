package com.itis.servletsexamplethird.repository;

import com.itis.servletsexamplethird.model.User;
import com.itis.servletsexamplethird.repository.base.CrudRepository;

public interface UsersRepository extends CrudRepository<User, Long> {}
