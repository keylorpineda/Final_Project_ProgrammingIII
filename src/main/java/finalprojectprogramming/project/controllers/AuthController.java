package finalprojectprogramming.project.controllers;

import java.io.BufferedReader;
import java.time.Instant;
import java.util.Date;
import java.io.IOException;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import finalprojectprogramming.project.dtos.AuthRequestDTO;
import finalprojectprogramming.project.dtos.AuthResponseDTO;
import finalprojectprogramming.project.models.User;
import finalprojectprogramming.project.security.AppUserDetails;
import finalprojectprogramming.project.security.jwt.JwtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final Validator validator;
    private final ObjectMapper objectMapper;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService, Validator validator,
            ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.validator = validator;
        this.objectMapper = objectMapper;
    }

    @PostMapping(value = "/login", consumes = { MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_FORM_URLENCODED_VALUE, MediaType.ALL_VALUE })
    public ResponseEntity<AuthResponseDTO> login(HttpServletRequest httpRequest) {
        AuthRequestDTO request = resolveCredentials(httpRequest);
        validateRequest(request);
        return authenticate(request);
    }

    private AuthRequestDTO resolveCredentials(HttpServletRequest httpRequest) {
        try {
            String contentType = httpRequest.getContentType();
            LOGGER.debug("Content-Type header: {}", contentType);

            if (contentType != null && contentType.contains(MediaType.APPLICATION_JSON_VALUE)) {
                String rawBody = readBody(httpRequest);
                LOGGER.debug("Raw login request body: {}", rawBody);
                return parseJson(rawBody, httpRequest);
            }

            String email = httpRequest.getParameter("email");
            String password = httpRequest.getParameter("password");
            if (email != null || password != null) {
                LOGGER.debug("Resolved credentials from parameters - email provided: {}, password provided: {}",
                        email != null && !email.isBlank(), password != null && !password.isBlank());
                return AuthRequestDTO.builder().email(email).password(password).build();
            }

            String rawBody = readBody(httpRequest);
            if (rawBody != null && !rawBody.isBlank()) {
                LOGGER.debug("Attempting to parse login request body without explicit content type: {}", rawBody);
                return parseJson(rawBody, httpRequest);
            }

            return null;
        } catch (IOException ex) {
            throw new HttpMessageNotReadableException("Unable to read request payload", ex,
                    new ServletServerHttpRequest(httpRequest));
        }
    }

    private String readBody(HttpServletRequest request) throws IOException {
        StringBuilder body = new StringBuilder();
        try (BufferedReader reader = request.getReader()) {
            String line;
            while ((line = reader.readLine()) != null) {
                body.append(line);
            }
        }
        return body.toString();
    }

    private AuthRequestDTO parseJson(String rawBody, HttpServletRequest request) {
        if (rawBody == null || rawBody.isBlank()) {
            return null;
        }

        try {
            AuthRequestDTO authRequest = objectMapper.readValue(rawBody, AuthRequestDTO.class);
            LOGGER.debug("Parsed login request - email provided: {}, password provided: {}",
                    authRequest.getEmail() != null && !authRequest.getEmail().isBlank(),
                    authRequest.getPassword() != null && !authRequest.getPassword().isBlank());
            return authRequest;
        } catch (JsonProcessingException ex) {
            throw new HttpMessageNotReadableException("Malformed request payload", ex,
                    new ServletServerHttpRequest(request));
        }
    }

    private void validateRequest(AuthRequestDTO request) {
        if (request == null) {
            throw new ConstraintViolationException("Missing credentials payload", Set.of());
        }

        Set<ConstraintViolation<AuthRequestDTO>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException("Validation failed", violations);
        }
    }

    private ResponseEntity<AuthResponseDTO> authenticate(AuthRequestDTO request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String authenticatedEmail = authentication.getName();
        String token = jwtService.generateToken(authenticatedEmail);
        Date expirationDate = jwtService.extractExpiration(token);
        Instant expiresAt = expirationDate != null ? expirationDate.toInstant() : null;

        Long userId = null;
        String role = null;
        Object principal = authentication.getPrincipal();
        if (principal instanceof AppUserDetails details) {
            User user = details.getDomainUser();
            if (user != null) {
                userId = user.getId();
                role = user.getRole() != null ? user.getRole().name() : null;
            }
        } else if (principal instanceof User user) {
            userId = user.getId();
            role = user.getRole() != null ? user.getRole().name() : null;
        } else {
            LOGGER.debug("Authentication principal is not an instance of AppUserDetails or User: {}", principal);
        }

        AuthResponseDTO response = AuthResponseDTO.builder()
                .token(token)
                .tokenType("Bearer")
                .expiresAt(expiresAt)
                .userId(userId)
                .role(role)
                .build();

        return ResponseEntity.ok(response);
    }
}