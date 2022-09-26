package victor.training.spring.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import victor.training.spring.di.subp.Z;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

// [1] Injection: field, constructor, method; debate; mockito
// [1] PostConstruct
// [2] Qualifier
// [3] Primary
// [4] Profile
// [5] getBean
// [6] inject List<BeanI>

@SpringBootApplication
public class FirstApplication implements CommandLineRunner {
	public static void main(String[] args) {
		SpringApplication.run(FirstApplication.class);
	}

	@Autowired
	private X x;

	@Override
	public void run(String... args) throws Exception {
		System.out.println("At startup ");
		System.out.println(x.prod());
	}
}

@Service
record X(
		Y y,
		Z z
) {

	public int prod() {
		return 1 + y.prod();
	}
}

@Component
@Retention(RetentionPolicy.RUNTIME)
@interface Facade {

}

//@Service // /**/Business Logic (domain rules)
//@Repository // DB access <
//	//  import the repo into the configuration.
//@Controller // not used anymore - the old way of generating webpages: jsp, jsf, vaadin, gwt, ....velocity
//@RestController // REST APIs
//@Component // garbage (unclear, technical)
//
//@Configuration // contains @Bean definitions, not application code
@Facade
class Y {
	private final Z z;

	// constructor injection (no @Autowired needed since Spring 4.3)
	public Y(Z z) {
		this.z = z;
	}

	public int prod() {
		return 1 + z.prod();
	}
}
