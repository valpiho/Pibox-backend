package com.pibox.core.controller;

import com.pibox.core.domain.HttpResponse;
import com.pibox.core.domain.dto.UserDto;
import com.pibox.core.domain.dto.UserLoginDto;
import com.pibox.core.domain.dto.UserRegistrationDto;
import com.pibox.core.domain.model.User;
import com.pibox.core.domain.UserPrincipal;
import com.pibox.core.exception.domain.EmailExistException;
import com.pibox.core.exception.domain.EmailNotFoundException;
import com.pibox.core.exception.domain.UserNotFoundException;
import com.pibox.core.exception.domain.UsernameExistException;
import com.pibox.core.mapper.UserMapper;
import com.pibox.core.service.UserService;
import com.pibox.core.utility.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;

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

    @GetMapping("/{username}")
    public ResponseEntity<UserDto> getUser(@PathVariable("username") String username) {
        User user = userService.findUserByUsername(username);
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
    }

    @PutMapping("/{username}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("username") String username,
                                            @RequestBody UserDto userDto) throws UserNotFoundException, EmailExistException, UsernameExistException {
        User user = userService.updateUserByUsername(username, userDto);
        return new ResponseEntity<>(userMapper.toUserDto(user), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<HttpResponse> register(@RequestBody UserRegistrationDto user)
            throws UsernameExistException, EmailExistException, UserNotFoundException, MessagingException {
        userService.registerNewUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getPassword(), user.getEmail());
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
