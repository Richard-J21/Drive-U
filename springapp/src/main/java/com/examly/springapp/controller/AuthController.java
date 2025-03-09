package com.examly.springapp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.examly.springapp.exceptions.DuplicateUserException;
import com.examly.springapp.model.TokenDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.UserDTO;
import com.examly.springapp.service.UserServiceImpl;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Tag(name = "Auth", description = "Auth controller used for register and login")
public class AuthController {

    private final UserServiceImpl userServiceImpl;

    /**
     * Registers a new user.
     *
     * @param user The user to be registered.
     * @return The registered user.
     * @throws DuplicateUserException if the user already exists.
     */
    @Operation(summary = "Register new User", description = "Registers a new user in the system. Throws DuplicateUserException if the user already exists.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully"),
            @ApiResponse(responseCode = "409", description = "Duplicate user detected")
    })
    @PostMapping("/register")
    public ResponseEntity<User> createUser(@RequestBody User user) throws DuplicateUserException {
        User user1 = userServiceImpl.createUser(user);
        return ResponseEntity.status(201).body(user1);
    }

    /**
     * Logs in a user.
     *
     * @param user The user credentials.
     * @return The login details.
     * @throws Exception if login fails.
     */
    @Operation(summary = "Login User", description = "Logs in a user with the provided credentials. Throws Exception if login fails.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User logged in successfully"),
            @ApiResponse(responseCode = "401", description = "Invalid credentials")
    })
    @PostMapping("/login")
    public ResponseEntity<TokenDTO> loginUser(@RequestBody UserDTO user) {
        TokenDTO tokenDto = userServiceImpl.loginUser(user);
        return ResponseEntity.status(201).body(tokenDto);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) throws EntityNotFoundException {
        return ResponseEntity.status(200).body(userServiceImpl.getUserById(userId));
    }

}
