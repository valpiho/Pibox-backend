package com.pibox.core.controller;

import com.pibox.core.domain.HttpResponse;
import com.pibox.core.domain.dto.UserDto;
import com.pibox.core.domain.dto.UserLoginDto;
import com.pibox.core.domain.dto.UserRegistrationDto;
import com.pibox.core.domain.model.User;
import com.pibox.core.domain.UserPrincipal;
import com.pibox.core.exception.domain.*;
import com.pibox.core.mapper.UserMapper;
import com.pibox.core.service.UserService;
import com.pibox.core.utility.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;

import java.io.IOException;
import java.util.UUID;

import static com.pibox.core.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public UserController(UserService userService,
                          UserMapper userMapper,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegistrationDto user)
            throws UsernameExistException, EmailExistException, NotFoundException {
        userService.registerNewUser(user);
        return response(HttpStatus.CREATED, "New user has been created");
    }

    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody UserLoginDto user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(userMapper.toUserDto(loginUser), jwtHeader, HttpStatus.OK);
    }

    @GetMapping("/reset-password/{email}")
    public ResponseEntity<HttpResponse> resetPassword(@PathVariable("email") String email) throws MessagingException, EmailNotFoundException {
        userService.resetPassword(email);
        return response(HttpStatus.OK, "An email with a new password was sent to: " + email);
    }

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
    }

    @PatchMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username,
                                              @RequestBody UserDto userDto,
                                              @RequestParam(value = "profileImage", required = false) MultipartFile profileImage)
            throws NotFoundException, EmailExistException, IOException, UsernameExistException, NotAnImageFileException {
        User user = userService.updateUserByUsername(username, userDto, profileImage);
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
    }

    @PostMapping("/updateProfileImage")
    public ResponseEntity<UserDto> updateProfileImage(@RequestParam("username") String username,
                                                   @RequestParam(value = "profileImage") MultipartFile profileImage)
            throws NotFoundException, IOException, NotAnImageFileException {
        User user = userService.updateProfileImage(username, profileImage);
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
    }

    @DeleteMapping("{userId}")
    @PreAuthorize("hasAnyAuthority('user:delete')")
    public ResponseEntity<HttpResponse> deleteUser(@PathVariable("userId")UUID userId)
            throws IOException, NotFoundException {
        userService.deleteUserByUserId(userId);
        return response(HttpStatus.NO_CONTENT, "User was deleted");
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }

    private ResponseEntity<HttpResponse> response(HttpStatus httpStatus, String message) {
        return new ResponseEntity<>(new HttpResponse(httpStatus.value(),
                httpStatus, httpStatus.getReasonPhrase(), message), httpStatus);
    }
}
