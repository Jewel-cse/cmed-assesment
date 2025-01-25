package com.rana.prescription_generation_app.mapper;

import com.rana.prescription_generation_app.dto.UserDto;
import com.rana.prescription_generation_app.entity.User;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;

/**
 * Mapper interface for converting between {@link User} and {@link UserDto}.
 *
 * <p>Uses MapStruct to generate the mapping implementation.</p>
 *
 * @author Md Jewel Rana
 * @version 1.0
 * @since 1.0
 */
@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    /**
     * Converts a User entity to a UserDto.
     *
     * @param user the User entity to convert.
     * @return the converted UserDto.
     */
    UserDto toUserDto(User user);

    /**
     * Converts a UserDto to a User entity.
     *
     * @param userDto the UserDto to convert.
     * @return the converted User entity.
     */
    User toUser(UserDto userDto);

    /**
     * Updates an existing User entity with values from a UserDto.
     *
     * @param userDto the UserDto containing updated values.
     * @param user the User entity to update.
     */
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromDto(UserDto userDto, @MappingTarget User user);
}

