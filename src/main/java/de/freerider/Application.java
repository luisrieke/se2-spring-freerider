package de.freerider;

import de.freerider.model.Customer;
import de.freerider.repository.CrudRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	@Autowired
	private CrudRepository<Customer, String> customerRepository;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

}
