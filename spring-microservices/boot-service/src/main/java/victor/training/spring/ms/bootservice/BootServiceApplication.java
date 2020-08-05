package victor.training.spring.ms.bootservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Arrays;
import java.util.stream.Stream;

@EnableBinding(Sink.class)
@EnableDiscoveryClient
@SpringBootApplication
public class BootServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BootServiceApplication.class, args);
	}

	@Autowired
	private ReservationRepo reservationRepo;

//	@Transactional
	@Override
	public void run(String... args) throws Exception {
		Stream.of("Anca","Mihai","Sorin", "Costel")
			.map(Reservation::new)
			.forEach(reservationRepo::save);
	}
}


