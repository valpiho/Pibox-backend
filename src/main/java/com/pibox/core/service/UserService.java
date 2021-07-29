package com.pibox.core.service;

import com.pibox.core.exception.domain.EmailExistException;
import com.pibox.core.exception.domain.EmailNotFoundException;
import com.pibox.core.exception.domain.UserNotFoundException;
import com.pibox.core.exception.domain.UsernameExistException;
import com.pibox.core.domain.model.User;

import javax.mail.MessagingException;

public interface UserService {

    User findUserByUsername(String username);

    User findUserByEmail(String email);

    User registerNewUser(String firstName, String lastName, String username, String email)
            throws UsernameExistException, EmailExistException, UserNotFoundException, MessagingException;


    User addNewUser(String firstName, String lastName, String username, String email);

    User updateUserById(Long id, String firstName, String lastName, String username, String email,
                    String role, boolean isActive);

    void deleteUserById(Long id);

    void resetPassword(String email) throws MessagingException, EmailNotFoundException;
}
