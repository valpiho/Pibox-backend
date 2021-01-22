package com.pibox.swan.service;

import com.pibox.swan.domain.User;
import com.pibox.swan.exception.domain.EmailExistException;
import com.pibox.swan.exception.domain.UserNotFoundException;
import com.pibox.swan.exception.domain.UsernameExistException;

public interface UserService {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User registerNewUser(String firstName, String lastName, String username, String email)
            throws UsernameExistException, EmailExistException, UserNotFoundException;

    User addNewUser(String firstName, String lastName, String username, String email);

    User updateUserById(Long id, String firstName, String lastName, String username, String email,
                    String role, boolean isActive);

    void deleteUserById(Long id);
}
