package de.rmi.springrmiloadbalancer.server;

import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import de.rmi.springrmiloadbalancer.server.adapter.HelloWorld;
import de.rmi.springrmiloadbalancer.server.app.ServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;

/**
 * Basic RMI server.
 * Registers himself on the {@link LoadBalancer} and implements a {@link HelloWorld} dummy service.
 */
@SpringBootApplication
@Slf4j
public class RmiServer {

	@Autowired
	LoadBalancer loadBalancer;

	@Autowired
	ServerConfig config;

	public static void main(String[] args) {
		SpringApplication.run(RmiServer.class, args);
	}

	/**
	 * After the {@link ApplicationReadyEvent} is fired, this method gets invoked and
	 * this server gets registered on the {@link LoadBalancer} (with ip and port)
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void registerServerOnLoadBalancer() {
		HashMap<String, String> serverConfig = new HashMap<>();
		try {
			final String url = InetAddress.getLocalHost().getHostAddress();
			serverConfig.put("URL", url);
			if (config.isRandomPort()) {
				serverConfig.put("PORT", Integer.toString(ServerConfig.RANDOM_SERVER_PORT));
			}
			else {
				serverConfig.put("PORT", Integer.toString(config.getServerPort()));
			}
			loadBalancer.registerServer(serverConfig);
		} catch (UnknownHostException e) {
			log.debug(e.getMessage(), e);
		}
	}
}
