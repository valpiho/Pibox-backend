package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.User;
import com.pibox.swan.domain.UserPrincipal;
import com.pibox.swan.enumeration.Role;
import com.pibox.swan.exception.domain.UserNotFoundException;
import com.pibox.swan.exception.domain.EmailExistException;
import com.pibox.swan.exception.domain.UsernameExistException;
import com.pibox.swan.repository.UserRepository;
import com.pibox.swan.service.UserService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.EMPTY;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserServiceImpl implements UserService, UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserServiceImpl(UserRepository userRepository,
                           BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Username is not found");
        } else {
            user.setLastLoginDate(new Date());
            userRepository.save(user);
            return new UserPrincipal(user);
        }
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public User registerNewUser(String firstName, String lastName, String username, String email)
            throws UsernameExistException, EmailExistException, UserNotFoundException {
        validateNewUsernameAndEmail(EMPTY, username, email);
        User user = new User();
        String password = generatePassword();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUsername(username);
        user.setEmail(email);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(password));
        user.setActive(true);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        userRepository.save(user);
        // TODO: Send email with password to the new user.
        LOGGER.info("New user password: " + password);
        return user;
    }

    @Override
    public User addNewUser(String firstName, String lastName, String username, String email) {
        // TODO: Add new user
        return null;
    }

    @Override
    public User updateUserById(Long id, String firstName, String lastName, String username, String email, String role, boolean isActive) {
        // TODO: User update
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteUserById(id);
    }

    private User validateNewUsernameAndEmail(String currentUsername, String newUsername, String newEmail)
            throws UsernameExistException, EmailExistException, UserNotFoundException {
        User userByNewUsername = findUserByUsername(newUsername);
        User userByNewEmail = findUserByEmail(newEmail);
        if (StringUtils.isNotBlank(currentUsername)) {
            User currentUser = findUserByUsername(currentUsername);
            if (currentUsername == null) {
                throw new UserNotFoundException("No user found by username");
            }
            if (userByNewUsername != null && !currentUser.getId().equals(userByNewUsername.getId())) {
                throw new UsernameExistException("Username already exists");
            }
            if (userByNewEmail != null && !currentUser.getId().equals(userByNewEmail.getId())) {
                throw new EmailExistException("Email already exists");
            }
            return currentUser;
        } else {
            if (userByNewUsername != null) {
                throw new UsernameExistException("Username already exists");
            }
            if (userByNewEmail != null) {
                throw new UsernameExistException("Username already exists");
            }
            return null;
        }
    }

    private String generatePassword() {
        return RandomStringUtils.randomAlphanumeric(10);
    }

    private String encodePassword(String password) {
        return bCryptPasswordEncoder.encode(password);
    }
}
