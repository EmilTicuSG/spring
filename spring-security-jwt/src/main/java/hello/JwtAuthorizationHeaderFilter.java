package hello;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedCredentialsNotFoundException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.bind.DatatypeConverter;

@Slf4j
public class JwtAuthorizationHeaderFilter extends AbstractPreAuthenticatedProcessingFilter {

//	public static final String JWT_HEADER_NAME = "x-mycomp-jwt-1";

	@Value("${jwt.secret}")
	private String backendSecret;

	public JwtAuthorizationHeaderFilter(AuthenticationManager authenticationManager) {
		setAuthenticationManager(authenticationManager);
	}

	@Override
	protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {

//		new HttpRequest(request);
		String authenticationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		System.out.println("Header: " + authenticationHeader);
		String jwtHeader = authenticationHeader.substring("Bearer ".length());

		if (jwtHeader == null) {
			return null;
		}

		String encodedJwt = jwtHeader;

		try {
			Claims claims = Jwts.parser()
					.setSigningKey(DatatypeConverter.parseBase64Binary(backendSecret))
					.parseClaimsJws(encodedJwt)
					.getBody();

			String countryId = (String) claims.get("countryId");
			log.info("Attempting login with userid={} and country={}", claims.getSubject(), countryId);
			return new UsernameContextPrincipal(claims.getSubject(), AuthnContext.LOW);
		} catch (UnsupportedJwtException jwtException) {
			throw new PreAuthenticatedCredentialsNotFoundException("Invalid JWT Token", jwtException);
		}
	}

	private AuthnContext getAuthnContext(Claims claims) {
		String contextStr = claims.get("AuthnContext4", String.class);
		if (contextStr == null) {
			return null;
		}
		return AuthnContext.valueOf(contextStr);
	}

	@Override
	protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
		return "N/A";
	}
}