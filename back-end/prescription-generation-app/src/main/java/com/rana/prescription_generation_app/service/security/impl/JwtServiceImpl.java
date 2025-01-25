package com.rana.prescription_generation_app.service.security.impl;


import com.rana.prescription_generation_app.constants.SecurityConstants;
import com.rana.prescription_generation_app.enums.TokenType;
import com.rana.prescription_generation_app.service.security.JwtService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.NotBlank;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Objects;

/**
 * This is the implementation of the jwt service.
 *
 * @author Md Jewel
 * @version 1.0
 * @since 1.0
 */
@Slf4j
@Service
public class JwtServiceImpl implements JwtService {

    private static final Logger LOG = LoggerFactory.getLogger(JwtServiceImpl.class);

    private static final int NUMBER_OF_DAYS_TO_EXPIRE = 1;
    private final transient String jwtSecret;

    public JwtServiceImpl(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    /**
     * Generate a JwtToken for the specified username.
     *
     * @param username the username
     * @return the token
     */
    @Override
    public String generateJwtToken(final String username) {
        Validate.notBlank(username, "Username cannot be blank");

        return generateJwtToken(username, DateUtils.addDays(new Date(), NUMBER_OF_DAYS_TO_EXPIRE));
    }

    /**
     * Generate a JwtToken for the specified username.
     *
     * @param username   the username
     * @param expiration the expiration date
     * @return the token
     */
    @Override
    public String generateJwtToken(@NotBlank final String username, final Date expiration) {

        var key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date())
                .expiration(expiration)
                .signWith(key)
                .compact();
    }

    /**
     * Retrieve username from the token.
     *
     * @param token the token
     * @return the username
     */
    @Override
    public String getUsernameFromToken(final String token) {
        Validate.notBlank(token, "Token cannot be blank");

        var key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload().getSubject();
    }

    /**
     * Retrieves the jwt token from the request cookie or request header if present and valid.
     *
     * @param request    the httpRequest
     * @param fromCookie if jwt should be retrieved from the cookies.
     * @return the jwt token
     */
    @Override
    public String getJwtToken(final HttpServletRequest request, final boolean fromCookie) {
        if (fromCookie) {
            return getJwtFromCookie(request);
        }

        return getJwtFromRequest(request);
    }

    /**
     * Validates the Jwt token passed to it.
     *
     * @param token the token
     * @return if valid or not
     */
    @Override
    public boolean isValidJwtToken(final String token) {
        var key = Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
        try {
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException e) {
            LOG.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    /**
     * Retrieves the jwt token from the request header if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    private String getJwtFromRequest(final HttpServletRequest request) {
        var headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.isNotBlank(headerAuth)
                && headerAuth.startsWith(SecurityConstants.BEARER_PREFIX)) {
            return headerAuth.split(StringUtils.SPACE)[NUMBER_OF_DAYS_TO_EXPIRE];
        }
        return null;
    }

    /**
     * Retrieves the jwt token from the request cookie if present and valid.
     *
     * @param request the httpRequest
     * @return the jwt token
     */
    private String getJwtFromCookie(final HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (Objects.nonNull(cookies)) {
            for (Cookie cookie : cookies) {
                if (TokenType.ACCESS.getName().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}

