package de.rmi.springrmiloadbalancer.loadbalancer.app;

import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import de.rmi.springrmiloadbalancer.loadbalancer.adapter.impl.LoadBalancerImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiServiceExporter;

/**
 * Load balancer configuration. The service manager is used by the server for registration.
 * The server port can be customized in the application.properties file.
 */
@Configuration
public class LoadBalancerConfig {

    @Value("${server.port:1099}")
    private int PORT_LOADBALANCER;

    /**
     * {@link LoadBalancerImpl} implementation
     * @return {@link LoadBalancerImpl} implementation
     */
    @Bean
    public LoadBalancer loadBalancer () {
        return new LoadBalancerImpl();
    }

    /**
     * Exports the {@link LoadBalancer} for registering servers
     * @return {@link LoadBalancer} proxy bean
     */
    @Bean
    public RmiServiceExporter loadBalancerExporter () {
        RmiServiceExporter exporter = new RmiServiceExporter();
        exporter.setServiceInterface(LoadBalancer.class);
        exporter.setService(loadBalancer());
        exporter.setServiceName(LoadBalancer.class.getSimpleName());
        exporter.setRegistryPort(PORT_LOADBALANCER);
        exporter.setServicePort(PORT_LOADBALANCER);
        //exporter.setRegistryHost("0.0.0.0");
        return exporter;
    }
}
