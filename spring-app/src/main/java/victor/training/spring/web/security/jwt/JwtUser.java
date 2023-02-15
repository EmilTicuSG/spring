package victor.training.spring.web.security.jwt;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Arrays.asList;

@Data
public class JwtUser implements UserDetails { // this object will later be available to any code needing it from the
    // SecurityContextHolder
    private final String username;
    private final String role;
    private String country; // dynamic, based on login request

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return asList(() -> role);
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
