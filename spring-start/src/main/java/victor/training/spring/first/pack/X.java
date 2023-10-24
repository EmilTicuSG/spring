package victor.training.spring.first.pack;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import victor.training.spring.first.MailService;
import victor.training.spring.first.Y;

import javax.annotation.PostConstruct;


//@Controller // = HTML generated on server with .jsp .jsf VAADIN Velocity Freemarker
//@RestController // REST API (JSON in out)

//@Service // = business logic

//@Repository // Data Access layer DB

//@Adapter

//@Configuration // @Bean
@Component // = everything else
//@RequiredArgsConstructor // makes javac generate the constructor in the .class file
@Slf4j
//@Lazy
public class X {
  @Autowired

  private Y y;
  @Autowired
  private MailService mailService;

//  @Value("${some.inexisting.prop}")
//  private String crashAtRuntime;

  @PostConstruct
  public void init() {
    System.out.println("What was I injected: " + mailService);
  }

  @Autowired // at startup
  public void init(Y y, Y y2) {
    // only when you want to DO stuff with those dependencies, not just store them as fields
    System.out.println("Y: " + y);
  }

  public int logic() {
    return 1;
  }
}
//