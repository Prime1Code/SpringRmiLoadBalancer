package de.rmi.springrmiloadbalancer.server.app;

import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import de.rmi.springrmiloadbalancer.server.adapter.HelloWorld;
import de.rmi.springrmiloadbalancer.server.adapter.impl.HelloWorldImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.remoting.rmi.RmiServiceExporter;

import java.util.Random;

/**
 * Basic server configuration with a {@link HelloWorld} dummy service and
 * a predefined {@link LoadBalancer} remote bean. Server port and {@link LoadBalancer}
 * configuration (ip, port) can be customized in the application.properties file.
 */
@Configuration
public class ServerConfig {

    @Value("${server.port:1100}")
    private int serverPort;
    public static final int RANDOM_SERVER_PORT = new Random().nextInt(2000);

    @Value("${load.balancer.ip:localhost}")
    private String LOAD_BALANCER_IP;

    @Value("${load.balancer.port:1099}")
    private int LOAD_BALANCER_PORT;

    @Value("${use.random.port:false}")
    private boolean useRandomPort;

    /**
     * Implementation of HelloWorld service interface
     * @return HelloWorld implementation
     */
    @Bean
    public HelloWorld helloWorld () {
        return new HelloWorldImpl();
    }

    /**
     * {@link HelloWorld} service proxy <br>
     * Important: RANDOM_SERVER_PORT has to be swapped with SERVER_PORT
     * @return {@link HelloWorld} proxy bean
     */
    @Bean
    public RmiServiceExporter helloWorldExporter () {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(HelloWorld.class);
        exporter.setService(helloWorld());
        exporter.setServiceName(HelloWorld.class.getSimpleName());
        if (useRandomPort) {
            exporter.setRegistryPort(RANDOM_SERVER_PORT);
            exporter.setServicePort(RANDOM_SERVER_PORT);
        }
        else {
            exporter.setRegistryPort(serverPort);
            exporter.setServicePort(serverPort);
        }
        return exporter;
    }

    /**
     * Predefined {@link LoadBalancer} proxy
     * @return {@link LoadBalancer} proxy bean
     */
    @Bean
    public RmiProxyFactoryBean loadBalancerClient () {
        RmiProxyFactoryBean factory = new RmiProxyFactoryBean();
        final String serviceUrl = new StringBuilder("rmi://")
                .append(LOAD_BALANCER_IP)
                .append(":")
                .append(LOAD_BALANCER_PORT)
                .append("/")
                .append(LoadBalancer.class.getSimpleName())
                .toString();
        factory.setServiceUrl(serviceUrl);
        factory.setServiceInterface(LoadBalancer.class);
        return factory;
    }

    /**
     * Is server using a random port?
     * @return useRandomPort
     */
    public boolean isRandomPort() {
        return useRandomPort;
    }

    /**
     * Returns the server port
     * @return serverPort
     */
    public int getServerPort() {
        return serverPort;
    }
}
