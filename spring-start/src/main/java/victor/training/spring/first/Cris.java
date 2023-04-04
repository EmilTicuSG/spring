package victor.training.spring.first;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import victor.training.spring.another.MyEvent;

import javax.annotation.PostConstruct;

@RequiredArgsConstructor
public class Cris implements CommandLineRunner {
  @Value("${mail.sender}")
  private final String prop;

  //  @PostConstruct
  //  @EventListener(ApplicationStartingEvent.class)
  //  @EventListener(ApplicationContextInitializedEvent.class)
  //  @EventListener(ContextRefreshedEvent.class)
  @EventListener(ApplicationStartedEvent.class) // when the app is fully configured
  public void logicMethod() {
    System.out.println("Cris logic: " + prop);
  }

  //  @EventListener
  //  public void onAppStart(ApplicationStartingEvent event) {
  //    event.getSpringApplication().add
  //  }
  @EventListener
  public void onMyEvent(MyEvent event) {
    System.out.println("Received event: " + event.getData());
  }

  @Override
  public void run(String... args) throws Exception {
    System.out.println("Workers/batches:  " + args);
  }
}


@Component
class SomeBeanNoOneLoves {

  @PostConstruct
  public void method() {
    System.out.printf(" I still live !");
  }
}