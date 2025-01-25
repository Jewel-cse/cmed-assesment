package com.rana.prescription_generation_app.controller.v1;

import com.rana.prescription_generation_app.dto.UserDto;
import com.rana.prescription_generation_app.repository.UserRepository;
import com.rana.prescription_generation_app.service.security.CookieService;
import com.rana.prescription_generation_app.service.security.EncryptionService;
import com.rana.prescription_generation_app.service.security.JwtService;
import com.rana.prescription_generation_app.utils.JwtResponseBuilder;
import com.rana.prescription_generation_app.utils.SecurityUtils;
import com.rana.prescription_generation_app.utils.UserDetailsBuilder;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.rana.prescription_generation_app.constants.API_V1.USER_URL;

@RestController
@RequestMapping(USER_URL)
@RequiredArgsConstructor
public class AuthController {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final CookieService cookieService;
    private final EncryptionService encryptionService;
    private final AuthenticationManager authenticationManager;

    /**
     * Handles user login and generates a JWT token.
     *
     * @param userDto the login credentials (username and password).
     * @return a jwtResponseBuilder containing the JWT token and user details.
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody UserDto userDto) {

        //check user exists
        if (!userRepository.existsByUsername(userDto.getUsername())) {
            throw new UsernameNotFoundException("User not found with user name: " + userDto.getUsername());
        }
        SecurityUtils.authenticateUser(authenticationManager, userDto.getUsername(), userDto.getPassword());
        UserDetailsBuilder userDetailsBuilder = SecurityUtils.getAuthenticatedUserDetails();


        String newAccessToken = jwtService.generateJwtToken(userDto.getUsername());
        String encryptedAccessToken = encryptionService.encrypt(newAccessToken);

//        var responseHeaders = new HttpHeaders();
//        cookieService.addCookieToHeaders(responseHeaders, TokenType.ACCESS, encryptedAccessToken, Duration.ofDays(5));
        return ResponseEntity.ok()
//                .headers(responseHeaders)
                .body(JwtResponseBuilder.buildJwtResponse(encryptedAccessToken, userDetailsBuilder));

    }


}
