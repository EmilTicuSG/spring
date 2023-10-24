package victor.training.spring.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
@Slf4j
@RequiredArgsConstructor
public class ClientCredentials { // CLient=Applicatie
  private final OAuth2RestTemplate restWithClientCredentials;

  // https://medium.com/@bcarunmail/securing-rest-api-using-keycloak-and-spring-oauth2-6ddf3a1efcc2
  @GetMapping("/api/client-credentials")
  public String clientCredentials() {
    return restWithClientCredentials.getForObject("http://localhost:8082", String.class);
  }

  @Scheduled(fixedRate = 2 * 1000)
  //@Async // makes the scheduled task re-entrant !! CAREFUL !!
  // +1 fun: think load balanced app on multiple pods.
  // if you want a distributed scheduled(to fire ONCE even if your app runs on 10 macines),
  //  use Shedlock or Quartz that coordinate via the DB, also storing the status, errors, run-time
  public void scheduled() {
    String value = restWithClientCredentials.getForObject("http://localhost:8082", String.class);
    log.info("@Scheduled got value: " + value);
  }


//  @Configuration
  public static class ConfigureOAuth2RestTemplate {
    @Bean
    public OAuth2RestTemplate restWithClientCredentials(OAuth2ProtectedResourceDetails details) {
      log.info("Trying to get the Access Token with Client Credential ...");
      OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
      //Prepare by getting the AccessToken once
      OAuth2AccessToken token = oAuth2RestTemplate.getAccessToken();
      System.out.println(token);
      log.info("SUCCESS");
      return oAuth2RestTemplate;
    }
  }

}


