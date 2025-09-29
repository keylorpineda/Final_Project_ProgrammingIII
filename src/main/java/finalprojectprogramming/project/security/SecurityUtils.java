package finalprojectprogramming.project.security;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;

import finalprojectprogramming.project.models.enums.UserRole;

public final class SecurityUtils {

    private SecurityUtils() {
    }

    public static void requireAny(UserRole... roles) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AuthenticationCredentialsNotFoundException("Authentication is required to access this resource.");
        }
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        if (authorities == null || authorities.isEmpty()) {
            throw new AccessDeniedException("Access is denied.");
        }
        boolean hasMatch = authorities.stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(authority -> Arrays.stream(roles)
                        .anyMatch(role -> authority.equals(role.name()) || authority.equals("ROLE_" + role.name())));
        if (!hasMatch) {
            throw new AccessDeniedException("Access is denied.");
        }
    }
}