package de.rmi.springrmiloadbalancer.client.service;

import de.rmi.springrmiloadbalancer.common.RmiLoadBalancerProxyFactoryBean;
import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import de.rmi.springrmiloadbalancer.server.adapter.HelloWorld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.stereotype.Component;

/**
 * Basic {@link HelloWorld}-service. Uses the {@link RmiLoadBalancerProxyFactoryBean}
 * to load balance various remote calls
 */
@Component
@Slf4j
public class HelloWorldService {

    /**
     * Custom {@link RmiProxyFactoryBean} for load balancing remote calls
     */
    @Autowired
    RmiLoadBalancerProxyFactoryBean helloWorldClient;

    /**
     * After the {@link ApplicationReadyEvent} is fired, this method gets invoked and
     * two remote methods on the {@link HelloWorld} class get called.
     * The {@link LoadBalancer} gets called on {@code helloWorldClient.getObject()}.
     * This method returns an instance of the interface that it received from the server
     * that was reported by the {@link LoadBalancer}.
     */
    @EventListener(ApplicationReadyEvent.class)
    public void invokeMethods () {
        HelloWorld helloWorld = (HelloWorld) helloWorldClient.getObject();
        log.info(helloWorld.helloWorldFromServer());
        log.info("{}", helloWorld.sum(3, 26));
    }
}
