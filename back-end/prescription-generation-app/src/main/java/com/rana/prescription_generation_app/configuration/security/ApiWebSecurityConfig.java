package com.rana.prescription_generation_app.configuration.security;


import com.rana.prescription_generation_app.configuration.security.jwt.JwtAuthTokenFilter;
import com.rana.prescription_generation_app.configuration.security.jwt.JwtAuthenticationEntryPoint;
import com.rana.prescription_generation_app.constants.API_V1;
import com.rana.prescription_generation_app.constants.SecurityConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


/**
 * This configuration handles api web requests with stateless session.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Configuration
@RequiredArgsConstructor
public class ApiWebSecurityConfig {

    private final JwtAuthTokenFilter jwtAuthTokenFilter;
    private final JwtAuthenticationEntryPoint unauthorizedHandler;
    private final AuthenticationManager authenticationManager;

    /**
     * Configure the {@link HttpSecurity}. Typically, subclasses should not call super as it may
     * override their configuration.
     *
     * @param http the {@link HttpSecurity} to modify.
     * @throws Exception thrown when error happens during authentication.
     */
    @Bean
    @Order
    public SecurityFilterChain apiFilterChain(HttpSecurity http,CorsFilter corsFilter) throws Exception {
        http.securityMatcher(SecurityConstants.API_ROOT_URL_MAPPING);
        http.exceptionHandling((exceptionHandling) -> exceptionHandling.authenticationEntryPoint(unauthorizedHandler))
                .sessionManagement((sessionManagement) -> sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)).authorizeHttpRequests(requests -> {
                    requests.requestMatchers(new AntPathRequestMatcher("/v3/api-docs/**"), new AntPathRequestMatcher("/swagger-ui/**"), new AntPathRequestMatcher("/swagger-ui.html")).permitAll();
                    requests.requestMatchers(new AntPathRequestMatcher(API_V1.USERS_SIGNUP_URL)).permitAll();
                    requests.requestMatchers(new AntPathRequestMatcher(API_V1.USERS_LOGIN_URL)).permitAll();
                    requests.requestMatchers(new AntPathRequestMatcher("/api/v1/doc-view/**", HttpMethod.GET.name())).permitAll();
                    requests.anyRequest().permitAll();
                })
                .csrf(AbstractHttpConfigurer::disable)
                .authenticationManager(authenticationManager)
                .addFilterBefore(jwtAuthTokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}

