package com.rana.prescription_generation_app.service;

import com.rana.prescription_generation_app.dto.UserDto;
import com.rana.prescription_generation_app.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

/**
 * Service interface for managing users.
 *
 * Provides method definitions for CRUD operations and other user-related actions. Each method returns
 * DTOs to ensure data returned to the client is in the appropriate form and hides internal entity details.
 *
 * @Author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
public interface UserService {

    /**
     * Create a new user.
     *
     * This method creates a new user in the database based on the provided `UserDto`.
     * Before saving, it checks if a user with the given `email` already exists. If so, an
     * exception is thrown to avoid duplicate entries.
     *
     * @param userDto the DTO containing the user data.
     * @return the created `UserDto` object after it is saved in the database.
     * @throws RuntimeException if a user with the given email already exists.
     */
    UserDto createUser(UserDto userDto);

    /**
     * Checks if a user's email is valid.
     *
     * @param email the email to validate.
     * @return true if the email is valid, false otherwise.
     */
    boolean isEmailAvailable(String email);

    /**
     * Checks if a username is already taken.
     *
     * @param username the username to check.
     * @return true if the username is available, false otherwise.
     */
    boolean isUsernameAvailable(String username);


    /**
     * checks if email is valid
     *
     * @param email the email to check
     * @return true if it is null or valid
     */
    boolean isValidEmail(String email);
}
