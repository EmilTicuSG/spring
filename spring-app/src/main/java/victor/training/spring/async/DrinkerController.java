package victor.training.spring.async;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.async.drinks.Beer;
import victor.training.spring.async.drinks.DillyDilly;
import victor.training.spring.async.drinks.Vodka;
import zipkin2.Call;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;

import static java.lang.System.currentTimeMillis;
import static java.util.concurrent.CompletableFuture.supplyAsync;

@Slf4j
@RestController
public class DrinkerController {
   @Autowired
   private Barman barman;

   // TODO [1] inject and submit work to a ThreadPoolTaskExecutor
   // TODO [2] mark pour* methods as @Async
   // TODO [3] Build a non-blocking web endpoint
   @GetMapping("api/drink")
   public DillyDilly drink() throws Exception {
      log.debug("Submitting my order");
      long t0 = currentTimeMillis();

      CompletableFuture<Beer> futureBeer = supplyAsync(() -> barman.pourBeer());
      CompletableFuture<Vodka> futureVodka = supplyAsync(() -> barman.pourVodka());
      Beer beer = futureBeer.get(); // 1 sec
      Vodka vodka = futureVodka.get(); // 0 sec

      long t1 = currentTimeMillis();
      log.debug("Got my drinks in {} millis", t1-t0);
      return new DillyDilly(beer, vodka);
   }
}
