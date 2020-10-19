package victor.training.spring.aspects;

import java.io.File;
import java.lang.reflect.Method;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching
@SpringBootApplication
@Slf4j
// [RO] "Viata e complexa si are multe aspecte" - Cel mai iubit dintre pamanteni. Spring e viata. :)
public class ProxyApp implements CommandLineRunner {
   public static void main(String[] args) {
      SpringApplication.run(ProxyApp.class, args);
   }

   // [1] Implement decorator with Spring
   // [2] InterfaceProxy.proxy (no Spring)
   // [3] ClassProxy.proxy (no Spring)
   // [4] Spring Cache support [opt: redis]
   // [5] Spring aspect, @Facade, @Logged
   // [6] Tips: self proxy, debugging, final
   // [7] OPT: Manual proxying using BeanPostProcessor

   // Holy Domain Logic.
   // Very precious things that I want to keep agnostic to technical details
   @Autowired
   private ExpensiveOps ops;

   public void run(String... args) {
      log.debug("\n");
      log.debug("---- CPU Intensive ~ memoization?");
      log.debug("10000169 is prime ? ");
      log.debug("Got: " + ops.isPrime(10000169) + "\n");
      log.debug("10000169 is prime ? ");
      log.debug("Got: " + ops.isPrime(10000169) + "\n");

      log.debug("---- I/O Intensive ~ \"There are only two things hard in programming...\"");
      log.debug("Folder . MD5: ");
      log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
      log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");

      // AICI detectez o modificare de fisier Files NIO
      ops.invalidateCache(new File("."));
      log.debug("Folder . MD5: ");
      log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
   }
}

@Aspect
@Component
class LoggingInterceptor {
   private static final Logger log = LoggerFactory.getLogger(LoggingInterceptor.class);

   @Around("execution(* victor..*.*(..))")
   public Object logIt(ProceedingJoinPoint point) throws Throwable {
      long t0 = System.currentTimeMillis();
      log.info("Calling method {} with params {}", point.getSignature().getName(), point.getArgs());
      Object result = point.proceed();
      long t1 = System.currentTimeMillis();
      log.info("Took {} ms", t1 - t0);
      return result;
   }

}


