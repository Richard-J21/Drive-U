package com.examly.springapp.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examly.springapp.config.JwtUtils;
import com.examly.springapp.exceptions.DuplicateUserException;
import com.examly.springapp.model.TokenDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.UserDTO;
import com.examly.springapp.repository.UserRepo;

import jakarta.persistence.EntityNotFoundException;

import org.springframework.security.core.Authentication;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtUtils jwtUtils;

    @Override
    public User createUser(User user) throws DuplicateUserException {
        if (userRepo.findByEmail(user.getEmail()).isPresent()) {
            throw new DuplicateUserException("A User with this email already exists!");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepo.save(user);
    }

    @Override
    public TokenDTO loginUser(UserDTO user) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        TokenDTO dto = new TokenDTO();

        User userExist = userRepo.findByEmail(user.getEmail()).get();

        Map<String, Object> claims = new HashMap<>();
        claims.put("role", userExist.getUserRole());
        claims.put("username", userExist.getUsername());
        claims.put("user_id", userExist.getUserId());

        dto.setToken(jwtUtils.generateToken(userDetails, claims));
        return dto;

    }

    @Override
    public User getUserById(Long userId) throws EntityNotFoundException {
        return userRepo.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with ID: " + userId + " is not found."));
    }

}