package com.rana.prescription_generation_app.utils;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.rana.prescription_generation_app.constants.SecurityConstants;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

/**
 * This class models the format of the login response produced.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Data
@Builder
public class JwtResponseBuilder implements Serializable {
    @Serial private static final long serialVersionUID = -3625429150594757621L;

    private String accessToken;
    private String type;
    private String publicId;
    private String username;
    private String email;

    /**
     * Build jwtResponse object from the specified userDetails.
     *
     * @param jwToken the jwToken.
     * @param userDetails the userDetails.
     * @return the jwtResponse object.
     */
    public static JwtResponseBuilder buildJwtResponse(
            String jwToken, UserDetailsBuilder userDetails) {

        var localUserDetails = userDetails;
        if (Objects.isNull(localUserDetails)) {
            localUserDetails = SecurityUtils.getAuthenticatedUserDetails();
        }

        if (Objects.nonNull(localUserDetails)) {
            return JwtResponseBuilder.builder()
                    .accessToken(jwToken)
                    .email(localUserDetails.getEmail())
                    .username(localUserDetails.getUsername())
                    .publicId(localUserDetails.getPublicId())
                    .type( SecurityConstants.BEARER)
                    .build();
        }
        return JwtResponseBuilder.builder().build();
    }
}

