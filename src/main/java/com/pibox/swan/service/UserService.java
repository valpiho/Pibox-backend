package com.pibox.swan.service;

import com.pibox.swan.domain.User;

public interface UserService {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User register(String firstName, String lastName, String username, String email);

    User addNewUser(String firstName, String lastName, String username, String email);

    User updateUser(Long id, String firstName, String lastName, String username, String email,
                    String role, boolean isActive);

    void deleteUser(Long id);
}
