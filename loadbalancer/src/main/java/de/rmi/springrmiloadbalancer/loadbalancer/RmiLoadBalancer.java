package de.rmi.springrmiloadbalancer.loadbalancer;

import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * {@link LoadBalancer} application
 */
@SpringBootApplication
public class RmiLoadBalancer {
	public static void main(String[] args) {
		SpringApplication.run(RmiLoadBalancer.class, args);
	}
}
