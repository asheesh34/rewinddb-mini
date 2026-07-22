package com.rewinddb.security;

import com.rewinddb.auth.entity.AppUser;
import com.rewinddb.auth.repository.AppUserRepository;
import com.rewinddb.common.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Resolves the currently authenticated {@link AppUser} from the security context.
 * Centralized here so every service that needs "who is making this request" (for
 * ownership checks, audit trails, etc.) shares one implementation instead of each
 * duplicating the SecurityContextHolder lookup.
 */
@Component
@RequiredArgsConstructor
public class CurrentUserProvider {

    private final AppUserRepository appUserRepository;

    public AppUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null
                || !authentication.isAuthenticated()
                || authentication instanceof AnonymousAuthenticationToken) {
            throw new UnauthorizedException("Authentication is required");
        }

        return appUserRepository.findByEmail(authentication.getName())
                .orElseThrow(() -> new UnauthorizedException("Authenticated user was not found"));
    }
}
