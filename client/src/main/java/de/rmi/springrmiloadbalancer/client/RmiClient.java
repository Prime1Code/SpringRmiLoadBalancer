package de.rmi.springrmiloadbalancer.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Basic RMI client application
 */
@SpringBootApplication
public class RmiClient {

	public static void main(String[] args) {
		SpringApplication.run(RmiClient.class, args);
	}

}
