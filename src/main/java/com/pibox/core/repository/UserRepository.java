package com.pibox.core.repository;

import com.pibox.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

    User findUserByUsername(String username);

    User findUserById(UUID Id);

    User findUserByEmail(String email);

    void deleteUserById(UUID id);
}
