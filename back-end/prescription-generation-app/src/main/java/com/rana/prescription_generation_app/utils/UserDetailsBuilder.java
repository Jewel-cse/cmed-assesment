package com.rana.prescription_generation_app.utils;

import java.io.Serial;
import java.util.Collection;

import com.rana.prescription_generation_app.entity.User;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.Validate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * UserDetailsBuilder builds the userDetails to be used by the application security context.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public final class UserDetailsBuilder implements UserDetails {
    @Serial private static final long serialVersionUID = -8755873164632782035L;

    private Long id;
    @EqualsAndHashCode.Include private String email;
    @EqualsAndHashCode.Include private String publicId;
    @EqualsAndHashCode.Include private String username;
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    /**
     * Builds userDetails object from the specified user.
     *
     * @param user the user
     * @throws NullPointerException if the user is null
     * @return the userDetails
     */
    public static UserDetailsBuilder buildUserDetails(final User user) {
        Validate.notNull(user, "User must not be null");

        return UserDetailsBuilder.builder()
                .id(user.getId())
                .email(user.getEmail())
                .username(user.getUsername())
                .password(user.getPassword())
                .publicId(user.getPublicId())
                .build();
    }
}

