package victor.training.spring.sub;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import victor.training.spring.first.MailService;
import victor.training.spring.first.X;

@Service
@RequiredArgsConstructor
public class Y {
//  private String currentUsername; // NICIODATA pe singleton nu tii date specifice UNUI SINGUR request/mesaj


//  @Qualifier("mailServiceImpl") // springule vreau acea isntanta cu numele asta
  private final MailService mailServiceImpl; // polymorphic injection
  // numele punctului de injectie (camp/param) decide numele beanului Spring


  @Value("${db.password}")
  private final String dbPassword;


  // dar e design smell
  @Lazy
  private final X x;

  //  @Value("${welcome.welcomeMessage}") // inject this from the configuration files
  private final String message = "HALO";

  // (recommended) constructor injection => 😏 replace with @RequiredArgsConstructor
//  public Y(@Qualifier("mailServiceImpl") MailService mailService,
//           @Value("${mail.sender}") String mailSender) {
//    this.mailService = mailService;
//    this.mailSender = mailSender;
//  }

  public int logic() {
    mailServiceImpl.sendEmail("I like 4 topics : " + message);
    System.out.println("Prop citit db pass= " + dbPassword);
    return 1;
  }
}