package com.pibox.swan.controller;

import com.pibox.swan.domain.User;
import com.pibox.swan.domain.UserPrincipal;
import com.pibox.swan.exception.domain.EmailExistException;
import com.pibox.swan.exception.domain.UserNotFoundException;
import com.pibox.swan.exception.domain.UsernameExistException;
import com.pibox.swan.service.UserService;
import com.pibox.swan.utility.JWTTokenProvider;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import static com.pibox.swan.constant.SecurityConstant.JWT_TOKEN_HEADER;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/{username}")
    public User getUser(@PathVariable("username") String username) {
        return userService.findUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user) throws UsernameExistException, EmailExistException, UserNotFoundException {
        User newUser = userService.registerNewUser(user.getFirstName(), user.getLastName(), user.getUsername(), user.getEmail());
        return new ResponseEntity<>(newUser, HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<User> login(@RequestBody User user) {
        authenticate(user.getUsername(), user.getPassword());
        User loginUser = userService.findUserByUsername(user.getUsername());
        UserPrincipal userPrincipal = new UserPrincipal(loginUser);
        HttpHeaders jwtHeader = getJwtHeader(userPrincipal);
        return new ResponseEntity<>(loginUser, jwtHeader, HttpStatus.OK);
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }

    private HttpHeaders getJwtHeader(UserPrincipal userPrincipal) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(JWT_TOKEN_HEADER, jwtTokenProvider.generateJwtToken(userPrincipal));
        return headers;
    }
}
