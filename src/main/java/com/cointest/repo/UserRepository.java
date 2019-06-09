package com.cointest.repo;

import com.cointest.model.User;
import org.springframework.data.repository.CrudRepository;

/**
 * The repository interfaces help us to talk to the underlining entity tables
 * for the model classes
**/

public interface UserRepository extends CrudRepository<User, Long> {
    User findByEmail(String username);
}
