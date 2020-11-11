package victor.training.spring.web.domain;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public enum UserProfile {
    USER("runSearch", "USER"),
    ADMIN("runSearch", "ADMIN",
            "deleteTraining"),
    POWER("runSearch", "ADMIN",
            "deleteTraining");
    public final Set<String> authorities;

    UserProfile(String... authorities) {
        this.authorities = new HashSet<>(Arrays.asList(authorities));
    }
}
