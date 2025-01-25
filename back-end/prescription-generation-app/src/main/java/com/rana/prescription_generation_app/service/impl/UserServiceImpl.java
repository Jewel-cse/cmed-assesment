package com.rana.prescription_generation_app.service.impl;

import com.rana.prescription_generation_app.dto.UserDto;
import com.rana.prescription_generation_app.entity.User;
import com.rana.prescription_generation_app.exception.classes.CustomAlreadyExistsException;
import com.rana.prescription_generation_app.mapper.UserMapper;
import com.rana.prescription_generation_app.repository.UserRepository;
import com.rana.prescription_generation_app.service.UserService;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if(isUsernameAvailable(userDto.getUsername())) {
            throw new CustomAlreadyExistsException("This username is already taken");
        }
        if(isEmailAvailable(userDto.getEmail())){
            throw new CustomAlreadyExistsException("An account already exists with this email");
        }
        if(!isValidEmail(userDto.getEmail())) {
            throw new ValidationException("Invalid email format");
        }
        User user = userRepository.save(UserMapper.INSTANCE.toUser(userDto));
        return UserMapper.INSTANCE.toUserDto(user);
    }

    @Override
    public boolean isEmailAvailable(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public boolean isUsernameAvailable(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean isValidEmail(String email) {
        String EMAIL_REGEX =
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            return Pattern.matches(EMAIL_REGEX, email);
    }
}
