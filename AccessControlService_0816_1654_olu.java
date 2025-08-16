// 代码生成时间: 2025-08-16 16:54:28
import org.springframework.stereotype.Component;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Component
public class AccessControlService {

    private static final String ROLE_ADMIN = "ROLE_ADMIN";
    private static final String ROLE_USER = "ROLE_USER";

    /**
     * Check if the current user has admin privileges.
     * 
     * @return true if the user has admin role, false otherwise.
     */
    @PreAuthorize("Youself")
    public boolean isAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return false;
        }
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userDetails.getAuthorities().stream().
                anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals(ROLE_ADMIN));
    }

    /**
     * Enforce access control for admin users.
     * Throws an exception if the user is not admin.
     */
    @PreAuthorize(\ROLE_ADMIN)
    public void enforceAdminAccess() {
        if (!isAdmin()) {
            throw new AccessDeniedException("Access Denied: User does not have admin privileges.");
        }
    }

    /**
     * Custom exception to handle access denied scenarios.
     */
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public static class AccessDeniedException extends RuntimeException {
        public AccessDeniedException(String message) {
            super(message);
        }
    }
}
