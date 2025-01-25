package com.rana.prescription_generation_app.utils;

import com.rana.prescription_generation_app.entity.User;
import com.rana.prescription_generation_app.exception.classes.CustomNotFoundException;
import com.rana.prescription_generation_app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * The implementation of service used to query user details during login.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Primary
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        if (StringUtils.isNotBlank(username)) {
            User storedUser = userRepository.findByUsername(username);
            if (Objects.isNull(storedUser)) {
                log.warn("No record found for storedUser with usernameOrEmail {}", username);
                throw new CustomNotFoundException(
                        "User with username: " + username + " not found");
            }
            return UserDetailsBuilder.buildUserDetails(storedUser);
        }
        return null;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<Role> roles) {
        return null;
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        return null;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        return null;
    }
}