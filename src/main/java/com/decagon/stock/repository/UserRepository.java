package com.decagon.stock.repository;

import com.decagon.stock.model.User;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author Victor.Komolafe
 */
@Repository
public interface UserRepository extends _BaseRepository<User, Long> {

    /**
     * Retrieve a User by username
     * @param name
     * @return
     */
    Optional<User> findFirstByUsernameIgnoreCase(String username);
}
