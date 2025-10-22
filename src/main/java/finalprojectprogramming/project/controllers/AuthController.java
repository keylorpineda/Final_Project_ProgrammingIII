package finalprojectprogramming.project.controllers;

import java.time.Instant;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import finalprojectprogramming.project.dtos.AuthRequestDTO;
import finalprojectprogramming.project.dtos.AuthResponseDTO;
import finalprojectprogramming.project.models.User;
import finalprojectprogramming.project.security.AppUserDetails;
import finalprojectprogramming.project.security.jwt.JwtService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthController(AuthenticationManager authenticationManager, JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@Valid @RequestBody AuthRequestDTO request) {
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