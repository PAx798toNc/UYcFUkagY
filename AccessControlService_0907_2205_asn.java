// 代码生成时间: 2025-09-07 22:05:42
import org.springframework.stereotype.Service;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AccessControlService {

    /**
     * Checks if the current user has the required permission to access a resource.
     * Throws an exception if access is denied.
     *
     * @param permission Required permission string.
     * @throws ResponseStatusException If the user does not have the required permission.
     */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public void checkUserPermission(String permission) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.getAuthorities().contains(permission)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access Denied: No Permission");
        }
    }

    /**
     * Example method with access control.
     * Only users with the 'ROLE_ADMIN' authority can access this method.
     *
     * @return A string indicating successful access.
     */
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String accessAdminResource() {
        return "Access granted to admin resource";
    }

    /**
     * Example method with access control.
     * Only users with the 'ROLE_USER' authority can access this method.
     *
     * @return A string indicating successful access.
     */
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String accessUserResource() {
        return "Access granted to user resource";
    }

    /**
     * Generic method to handle access denied scenarios.
     *
     * @param e The AccessDeniedException.
     * @return A response indicating access was denied.
     */
    public String handleAccessDenied(AccessDeniedException e) {
        return "Access denied: " + e.getMessage();
    }
}
