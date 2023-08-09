package victor.training.spring.first;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

@Component
@ConfigurationProperties(prefix = "welcome")
@Validated
public class WelcomeInfo {
  private static final Logger log = org.slf4j.LoggerFactory.getLogger(WelcomeInfo.class);
  @NotNull
  private Integer gate; // todo validate not null

  // TODO 4a validate size >= 4
  @Size(min = 4)
  @NotNull
  private String welcomeMessage;
  @Size(min=1)
  private List<URL> supportUrls; // TODO 4b validate list contains at least 1 element
  private Map<Locale, String> localContactPhone;
  private HelpInfo help;

  public static class HelpInfo {
    private Integer appId;
    private File file; // TODO 4c validate exists on disk

    public Integer getAppId() {
      return appId;
    }

    public File getFile() {
      return file;
    }

    public void setAppId(Integer appId) {
      this.appId = appId;
    }

    public void setFile(File file) {
      this.file = file;
    }

    public String toString() {
      return "WelcomeInfo.HelpInfo(appId=" + getAppId() + ", file=" + getFile() + ")";
    }
  }

  @PostConstruct
  public void checkFileExists() {
//    if (!help.file.isFile()) {
//      throw new IllegalArgumentException("Not a file");
//    }
    Validator v;
  }
  
  public Integer getGate() {
    return gate;
  }

  public String getWelcomeMessage() {
    return welcomeMessage;
  }

  public List<URL> getSupportUrls() {
    return supportUrls;
  }

  public Map<Locale, String> getLocalContactPhone() {
    return localContactPhone;
  }

  public HelpInfo getHelp() {
    return help;
  }

  public void setGate(Integer gate) {
    this.gate = gate;
  }

  public void setWelcomeMessage(String welcomeMessage) {
    this.welcomeMessage = welcomeMessage;
  }

  public void setSupportUrls(List<URL> supportUrls) {
    this.supportUrls = supportUrls;
  }

  public void setLocalContactPhone(Map<Locale, String> localContactPhone) {
    this.localContactPhone = localContactPhone;
  }

  public void setHelp(HelpInfo help) {
    this.help = help;
  }


  public String toString() {
    return "WelcomeInfo(gate=" + getGate() + ", welcomeMessage=" + getWelcomeMessage() + ", supportUrls=" + getSupportUrls() + ", localContactPhone=" + getLocalContactPhone() + ", help=" + getHelp() + ")";
  }



  @PostConstruct
  public void printMyselfAtStartup() throws JsonProcessingException {
    String jsonToString = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(this);
    log.info("WelcomeInfo: \n" + jsonToString);
  }
}
// to test the points below, watch the log for 'WelcomeInfo:' output
//   or create a new component in which to inject WelcomeInfo and use a property
// TODO 1 inject welcome.welcomeMessage property in 'welcomeMessage' field
//   Hint: @Value("${
//   Question: what happens if the property is NOT defined ?
//     TODO 1* provide a default value by @Value("${...:defaultvalue}")
// TODO 2 inject welcome.gate in the 'gate' field'
//   Question: what happens if the value of the property is not an INT ?
// TODO 3 inject all properties at once using @ConfigurationProperties(prefix=...)
//   Ref: https://www.google.com/search?q=configuration+properties+baledung
// TODO 4 validate (see above)
//   - using if at startup
//   - using javax.validation annotations + @Validated on the class
//   Trick: provide a default to a property by assigning the field = "defaultvalue"
// TODO 5[PRO] make this class immutable: lombok.@Value + @ConstructorBinding + @ConfigurationPropertiesScan on a @Configuration
//  Hint: https://stackoverflow.com/questions/26137932/immutable-configurationproperties