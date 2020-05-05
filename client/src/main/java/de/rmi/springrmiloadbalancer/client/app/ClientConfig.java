package de.rmi.springrmiloadbalancer.client.app;

import de.rmi.springrmiloadbalancer.common.RmiLoadBalancerProxyFactoryBean;
import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import de.rmi.springrmiloadbalancer.server.adapter.HelloWorld;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;

/**
 * Basic client configuration. The {@link HelloWorld}-Service is load balanced ({@link RmiLoadBalancerProxyFactoryBean})
 * {@link LoadBalancer} configuration (ip, port) can be customized in the application.properties file.
 */
@Configuration
public class ClientConfig {

    @Value("${load.balancer.ip:localhost}")
    private String LOAD_BALANCER_IP;

    @Value("${load.balancer.port:1099}")
    private int LOAD_BALANCER_PORT;

    /**
     * Custom {@link RmiProxyFactoryBean} for load balanced remote calls.
     * @return {@link RmiLoadBalancerProxyFactoryBean} proxy bean
     */
    @Bean
    public RmiLoadBalancerProxyFactoryBean helloWorldClient () {
        RmiLoadBalancerProxyFactoryBean factory = new RmiLoadBalancerProxyFactoryBean();
        factory.setServiceInterface(HelloWorld.class);
        return factory;
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
}
