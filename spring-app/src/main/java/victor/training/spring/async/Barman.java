package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.Vodka;
import victor.training.spring.varie.ThreadUtils;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
public class Barman {
  @Async("executor") // ruleaz-o pe beanul numit "executor"
  public CompletableFuture<Beer> pourBeer() {
    log.debug("Pouring Beer (SOAP CALL)...");
    ThreadUtils.sleepq(1000);
    return CompletableFuture.completedFuture(new Beer());
  }

  @Async("executor") // ruleaz-o pe beanul numit "executor"
  public CompletableFuture<Vodka> pourVodka() {
    log.debug("Pouring Vodka (REST CALL)...");
    ThreadUtils.sleepq(1000);
    return CompletableFuture.completedFuture(new Vodka());
  }

  @Async
  public void injura(String uratura) {
     log.info("Acum primesc mesaj");
    if (uratura != null)
      throw new RuntimeException("Iti fac buzunar");
  }
}
