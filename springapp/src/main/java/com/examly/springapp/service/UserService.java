package com.examly.springapp.service;

import com.examly.springapp.exceptions.DuplicateUserException;
import com.examly.springapp.model.TokenDTO;
import com.examly.springapp.model.User;
import com.examly.springapp.model.UserDTO;

import jakarta.persistence.EntityNotFoundException;

public interface UserService {
    User createUser(User user) throws DuplicateUserException;

    TokenDTO loginUser(UserDTO user);

    User getUserById(Long userId) throws EntityNotFoundException;
}
