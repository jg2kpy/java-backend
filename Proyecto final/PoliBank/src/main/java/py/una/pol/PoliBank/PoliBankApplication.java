package py.una.pol.PoliBank;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PoliBankApplication {

	public static void main(String[] args) {
		SpringApplication.run(PoliBankApplication.class, args);
	}

}
