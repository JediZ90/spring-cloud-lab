package lab.cloud.bookstore.web.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import static lab.cloud.bookstore.web.security.SecurityAuthorities.BOOK_STORE_ID_PREFIX;

import java.io.Serializable;
import java.util.Collection;
import java.util.Optional;

public class BookStorePermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(Authentication authentication, Object targetDomainObject, Object permission) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Optional<Boolean> matched = authorities.stream()
                        .filter(authority -> authority.getAuthority().startsWith(BOOK_STORE_ID_PREFIX))
                        .map(authority -> {
                            String serviceInstanceId = authority.getAuthority()
                                            .substring(BOOK_STORE_ID_PREFIX.length());
                            return serviceInstanceId.equals(targetDomainObject);
                        }).findFirst();

        return matched.orElse(true);
    }

    @Override
    public boolean hasPermission(Authentication authentication, Serializable targetId, String targetType,
                    Object permission) {
        return true;
    }
}