package com.pibox.core.service;

import com.pibox.core.constant.FileConstant;
import com.pibox.core.constant.UserImplConstant;
import com.pibox.core.enumeration.Role;
import com.pibox.core.exception.domain.*;
import com.pibox.core.repository.UserRepository;
import com.pibox.core.domain.model.User;
import com.pibox.core.domain.UserPrincipal;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.mail.MessagingException;
import javax.transaction.Transactional;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Date;
import java.util.UUID;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.springframework.http.MediaType.*;

@Service
@Transactional
@Qualifier("userDetailsService")
public class UserService implements UserDetailsService {

    private final Logger LOGGER = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final EmailService emailService;

    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder, EmailService emailService) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.emailService = emailService;
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

    public User findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    public User registerNewUser(String newFirstName, String newLastName, String newUsername, String newPassword, String newEmail)
            throws UsernameExistException, EmailExistException, UserNotFoundException, MessagingException {
        validateNewUsernameAndEmail(EMPTY, newUsername, newEmail);
        User user = new User();
        user.setFirstName(newFirstName);
        user.setLastName(newLastName);
        user.setUsername(newUsername);
        user.setEmail(newEmail);
        user.setJoinDate(new Date());
        user.setPassword(encodePassword(newPassword));
        user.setActive(true);
        user.setRole(Role.ROLE_USER.name());
        user.setAuthorities(Role.ROLE_USER.getAuthorities());
        user.setProfileImgUrl(getTemporaryProfileImageUrl(newUsername));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(newFirstName, newPassword, newEmail);
        return user;
    }

    public User addNewUser(String firstName, String lastName, String username, String email) {
        // TODO: Add new user
        return null;
    }

    public User updateUserByUserId(UUID userId, String firstName, String lastName, String username, String email, String role, boolean isActive) {
        // TODO: User update
        return null;
    }

    public void deleteUserByUserId(UUID userId) {
        userRepository.deleteUserByUserId(userId);
    }

    public void resetPassword(String email) throws MessagingException, EmailNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new EmailNotFoundException(UserImplConstant.NO_USER_FOUND_BY_EMAIL + email);
        }
        String password = generatePassword();
        user.setPassword(encodePassword(password));
        userRepository.save(user);
        emailService.sendNewPasswordEmail(user.getFirstName(), password, user.getEmail());
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
            if (userByNewUsername != null && !currentUser.getUserId().equals(userByNewUsername.getUserId())) {
                throw new UsernameExistException("Username already exists");
            }
            if (userByNewEmail != null && !currentUser.getUserId().equals(userByNewEmail.getUserId())) {
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

    private String getTemporaryProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.DEFAULT_USER_IMAGE_PATH + username).toUriString();
    }

    private void saveProfileImage(User user, MultipartFile profileImage) throws IOException, NotAnImageFileException {
        if (profileImage != null) {
            if(!Arrays.asList(IMAGE_JPEG_VALUE, IMAGE_PNG_VALUE, IMAGE_GIF_VALUE).contains(profileImage.getContentType())) {
                throw new NotAnImageFileException(profileImage.getOriginalFilename() + FileConstant.NOT_AN_IMAGE_FILE);
            }
            Path userFolder = Paths.get(FileConstant.USER_FOLDER + user.getUsername()).toAbsolutePath().normalize();
            if(!Files.exists(userFolder)) {
                Files.createDirectories(userFolder);
                LOGGER.info(FileConstant.DIRECTORY_CREATED + userFolder);
            }
            Files.deleteIfExists(Paths.get(userFolder + user.getUsername() + FileConstant.DOT + FileConstant.JPG_EXTENSION));
            Files.copy(profileImage.getInputStream(), userFolder.resolve(user.getUsername() + FileConstant.DOT + FileConstant.JPG_EXTENSION), REPLACE_EXISTING);
            user.setProfileImgUrl(setProfileImageUrl(user.getUsername()));
            userRepository.save(user);
            LOGGER.info(FileConstant.FILE_SAVED_IN_FILE_SYSTEM + profileImage.getOriginalFilename());
        }
    }

    private String setProfileImageUrl(String username) {
        return ServletUriComponentsBuilder.fromCurrentContextPath().path(FileConstant.USER_IMAGE_PATH + username + FileConstant.FORWARD_SLASH
                + username + FileConstant.DOT + FileConstant.JPG_EXTENSION).toUriString();
    }
}
