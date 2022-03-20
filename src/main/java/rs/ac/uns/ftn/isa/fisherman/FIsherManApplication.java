package rs.ac.uns.ftn.isa.fisherman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class FIsherManApplication {

	public static void main(String[] args) {
		SpringApplication.run(FIsherManApplication.class, args);
	}

}
