package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.props.WelcomeInfo;
import victor.training.spring.web.controller.dto.CurrentUserDto;
import victor.training.spring.web.security.preauth_jwt.JwtPrincipal;

import java.util.concurrent.CompletableFuture;

@Slf4j
@RequiredArgsConstructor
@RestController
public class TechnicalController {
    private final AnotherClass anotherClass;

//    @Secured("ADMIN")
//    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('drop.all')")

    @PostMapping("drop")
    public String dropDB() {
        return "RIP";
    }

    @GetMapping("api/user/current")
    public CurrentUserDto getCurrentUsername() throws Exception {

        log.info("Return current user");
        CurrentUserDto dto = new CurrentUserDto();
        dto.username = other.howTheHack().get();
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        JwtPrincipal jwtUser = (JwtPrincipal) principal;
        dto.role = jwtUser.getRole().name();
        //        dto.phone = ??

        // dto.username = anotherClass.asyncMethod().get();

        // A) role-based security
        //		dto.role = extractOneRole(authentication.getAuthorities());

        // B) authority-based security
        //		dto.authorities = authentication.getAuthorities().stream()
        //				.map(GrantedAuthority::getAuthority).collect(Collectors.toList());

        //<editor-fold desc="KeyCloak">
        //		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        //		dto.username = authentication.getName();
        //		dto.role = authentication.getAuthorities().iterator().next().getAuthority();
        //		dto.authorities = stripRolePrefix(authentication.getAuthorities());
        //    // Optional:
        //		KeycloakPrincipal<KeycloakSecurityContext> keycloakToken =(KeycloakPrincipal<KeycloakSecurityContext>) authentication.getPrincipal();
        //		dto.fullName = keycloakToken.getKeycloakSecurityContext().getIdToken().getName();
        //		log.info("Other details about user from ID Token: " + keycloakToken.getKeycloakSecurityContext().getIdToken().getOtherClaims());
        //</editor-fold>
        return dto;
    }

    @Autowired
    private Other other;

    @Component
    static class Other {
        @Async("taskExecutor") // called locally is NOT doing any async
        public CompletableFuture<String> howTheHack() {
            log.info("WHere am i");
            String uname = SecurityContextHolder.getContext().getAuthentication().getName();
            return CompletableFuture.completedFuture(uname);
        }
    }

    //	public static String extractOneRole(Collection<? extends GrantedAuthority> authorities) {
    //		// For Spring Security (eg. hasRole) a role is an authority starting with "ROLE_"
    //		List<String> roles = authorities.stream()
    //				.map(GrantedAuthority::getAuthority)
    //				.filter(authority -> authority.startsWith("ROLE_"))
    //				.map(authority -> authority.substring("ROLE_".length()))
    //				.collect(Collectors.toList());
    //		if (roles.size() == 2) {
    //			log.debug("Even though Spring allows a user to have multiple roles, we wont :)");
    //			return "N/A";
    //		}
    //		if (roles.size() == 0) {
    //			return null;
    //		}
    //		return roles.get(0);
    //	}


    	@Bean // enable propagation of SecurityContextHolder over @Async
    	public DelegatingSecurityContextAsyncTaskExecutor taskExecutor(ThreadPoolTaskExecutor barPool) {
    		// https://www.baeldung.com/spring-security-async-principal-propagation
    		return new DelegatingSecurityContextAsyncTaskExecutor(barPool);
    	}

    @Autowired
    private WelcomeInfo welcomeInfo;

    // TODO [SEC] allow unsecured access
    @GetMapping("unsecured/welcome")
    public WelcomeInfo showWelcomeInfo() {
        return welcomeInfo;
    }

    // TODO [SEC] URL-pattern restriction: admin/**
    @GetMapping("admin/launch")
    public String restart() {
        return "What does this red button do?     ... [Missile Launched]";
    }

}

@Slf4j
@Service
class AnotherClass {
//    @Async
//    public CompletableFuture<String> asyncMethod() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.info("Current authentication = {}", authentication);
//        return CompletableFuture.completedFuture(authentication.getName());
//    }
}
