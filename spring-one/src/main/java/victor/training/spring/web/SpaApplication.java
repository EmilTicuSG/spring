package victor.training.spring.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableCaching
@EnableSwagger2
@EnableMBeanExport
@EnableScheduling
@SpringBootApplication
public class SpaApplication {

	public static void main(String[] args) {
		new SpringApplicationBuilder(SpaApplication.class)
				.profiles("spa") // re-enables WEB nature (disabled in application.properties for the other apps)
				.run(args);
	}

	@Bean
	public MethodValidationPostProcessor postProcessor1() {
		return new MethodValidationPostProcessor();
	}
}
//
//@Service
//class MyService {
//	private boolean cr323;
//
//}
