package com.pibox.core.repository;

import com.pibox.core.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findUserByUsername(String username);

    User findUserByUserId(String userId);

    User findUserByEmail(String email);

    void deleteUserById(Long id);
}