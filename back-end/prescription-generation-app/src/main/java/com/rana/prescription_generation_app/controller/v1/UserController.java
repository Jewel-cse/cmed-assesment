package com.rana.prescription_generation_app.controller.v1;

import com.rana.prescription_generation_app.dto.UserDto;
import com.rana.prescription_generation_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.rana.prescription_generation_app.constants.API_V1.USER_URL;

/**
 * REST controller for managing {@link com.rana.prescription_generation_app.entity.User} entities.
 *
 * <p>This controller provides an endpoint to create a new User.</p>
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@RestController
@RequestMapping(USER_URL)
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Create a new user.
     *
     * @param userDto the DTO containing data for the new user.
     * @return the ResponseEntity with status 201 (Created) and with body the new userDto.
     */
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        UserDto createdUser = userService.createUser(userDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }
}

