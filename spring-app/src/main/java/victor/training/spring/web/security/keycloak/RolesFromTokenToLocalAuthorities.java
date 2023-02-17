package victor.training.spring.web.security.keycloak;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import victor.training.spring.web.entity.UserRole;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@Slf4j
public class RolesFromTokenToLocalAuthorities implements GrantedAuthoritiesMapper {
    @Override
    public Collection<? extends GrantedAuthority> mapAuthorities(Collection<? extends GrantedAuthority> authorities) {
        log.debug("Input authorities: " + authorities);
        List<UserRole> matchingRoles = authorities.stream()
                .map(a -> UserRole.valueOfOpt(a.getAuthority()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(toList());
        if (matchingRoles.size() != 1) {
            KeyCloakUtils.printTheTokens();
            throw new IllegalArgumentException("No single role found in token that matches known roles " + Arrays.toString(UserRole.values()));
        }
        List<SimpleGrantedAuthority> resolveAuthorities = matchingRoles.stream()
                .flatMap(userRole -> userRole.getAuthorities().stream())
                .map(SimpleGrantedAuthority::new)
                .collect(toList());
        log.debug("Output authorities (roles:{}) = {}", matchingRoles, resolveAuthorities);
        return resolveAuthorities;
    }

}
