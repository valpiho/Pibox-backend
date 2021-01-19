package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.User;
import com.pibox.swan.domain.UserPrincipal;
import com.pibox.swan.repository.UserRepository;
import com.pibox.swan.service.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public User registerNewUser(String firstName, String lastName, String username, String email) {
        // TODO: New user registration
        return null;
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
}
