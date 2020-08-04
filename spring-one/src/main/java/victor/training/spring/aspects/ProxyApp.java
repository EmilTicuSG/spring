package victor.training.spring.aspects;

import java.io.File;
import java.util.Arrays;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@EnableAspectJAutoProxy(exposeProxy = true)
@EnableCaching (order = 1)
@SpringBootApplication
@Slf4j
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
	private ExpensiveOps ops = new ExpensiveOps();



	public void run(String... args) {
		log.debug("Oare ce mi-a dat springul este un proxy sau implm reala? " + ops.getClass());
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
		log.debug("AICI detectez o modificare in folderul respectiv (NIO)");
		// AICI
		ops.killFolderCache(new File("."));

		log.debug("Folder . MD5: ");
		log.debug("Got: " + ops.hashAllFiles(new File(".")) + "\n");
	}
}

@Component
@Aspect
class LoggingInterceptor {
	@Order(10)
	@Around("execution(* victor.training.spring.aspects.ExpensiveOps.*(..))")
	public Object logCall(ProceedingJoinPoint pjp) throws Throwable {
		System.out.println("Calling " + pjp.getSignature().getName() + " ( " + Arrays.toString(pjp.getArgs()) + " ) ");
		Object result = pjp.proceed();
		return result;
	}
}