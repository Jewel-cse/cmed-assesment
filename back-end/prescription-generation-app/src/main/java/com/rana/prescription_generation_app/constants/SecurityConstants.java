package com.rana.prescription_generation_app.constants;



import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpMethod;

import static com.rana.prescription_generation_app.constants.API_V1.USERS_SIGNUP_URL;

/**
 * This class holds all security-related URL mappings constants.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
public final class SecurityConstants {


    public static final String API_ROOT_URL_MAPPING = "/api/**";
    public static final String BEARER = "Bearer";
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String LOGIN = "/login";
    public static final String ROOT_PATH = "/";
    public static final String SAME_SITE = "strict";

    public static final int DEFAULT_TOKEN_DURATION = 7;

    public static final int SECURITY_STRENGTH = 12;

    public static final List<String> ALLOWED_HTTP_METHODS =
            List.of(
                    HttpMethod.GET.name(),
                    HttpMethod.POST.name(),
                    HttpMethod.PUT.name(),
                    HttpMethod.DELETE.name(),
                    HttpMethod.PATCH.name(),
                    HttpMethod.OPTIONS.name());
    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/js/**",
            "/images/**",
            "/fonts/**",
            "/webjars/**",
            "/resources/**",
            "/static/**",
            "/console/**",
            "/actuator/health",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-ui.html",
            ROOT_PATH,
            String.join("/", SecurityConstants.LOGIN, "**"),
            String.join("/", USERS_SIGNUP_URL, "**")
    };
    public static final List<String> ALLOWED_ORIGINS= List.of("*");

    private SecurityConstants() {
        throw new AssertionError("This class cannot be instantiated");
    }

    /**
     * Public matchers to allow access to the application.
     *
     * @return public matchers.
     */
    public static Collection<String> getPublicMatchers() {
        return Collections.unmodifiableCollection(Arrays.asList(PUBLIC_MATCHERS));
    }

}

