package de.rmi.springrmiloadbalancer.common;

import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;
import de.rmi.springrmiloadbalancer.server.adapter.HelloWorld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.rmi.RmiProxyFactoryBean;
import org.springframework.util.Assert;

import java.util.HashMap;

/**
 * Custom RMI load balancer proxy factor bean. Extends the {@link RmiProxyFactoryBean} with an update method. This update
 * methods receives a new server configuration from the {@link LoadBalancer} every time the {@code getObject()} gets called.
 * The {@code afterPropertiesSet()}-method overrides the super implementation to avoid a missing serviceUrl exception.
 * It also sets the {@code lookupStubOnStartup} property to false in order to reduce remote exceptions on application start.
 */
@Slf4j
public class RmiLoadBalancerProxyFactoryBean extends RmiProxyFactoryBean {

    @Autowired
    private LoadBalancer loadBalancer;

    /**
     * Actual service implementation (e.g. {@link HelloWorld})
     */
    private Object loadBalancerServiceProxy;

    /**
     * Gets a new server from the load balancer every time the {@code loadBalancerServiceProxy} gets returned.
     */
    private void update () {
        HashMap<String, String> availableServer = loadBalancer.getAvailableServer();
        if (log.isDebugEnabled()) {
            log.debug("availableServer = {}", availableServer);
        }
        if (availableServer != null && !availableServer.isEmpty()) {
            final String newServiceUrl = new StringBuilder("rmi://")
                    .append(availableServer.get(LoadBalancer.URL))
                    .append(":")
                    .append(availableServer.get(LoadBalancer.PORT))
                    .append("/")
                    .append(getServiceInterface().getSimpleName())
                    .toString();
            if (log.isDebugEnabled()) {
                log.debug("newServiceUrl = {}", newServiceUrl);
            }
            setServiceUrl(newServiceUrl);
        }
    }

    /**
     * Overrides the super implementation to avoid a missing serviceUrl exception. It also
     * sets the {@code lookupStubOnStartup} property to false in order to reduce remote exceptions on application start.
     */
    @Override
    public void afterPropertiesSet() { // avoids missing serviceUrl exception
        setLookupStubOnStartup(false);
        prepare();
        Class<?> ifc = getServiceInterface();
        Assert.notNull(ifc, "Property 'serviceInterface' is required");
        loadBalancerServiceProxy = new ProxyFactory(ifc, this).getProxy(getBeanClassLoader());
    }

    /**
     * Returns the {@code loadBalancerServiceProxy}-object. It is the actual service instance (e.g. {@link HelloWorld})
     * Before returning the {@code loadBalancerServiceProxy}-object the {@code update()} method is called to get a new
     * server for the next method invocation on the {@code loadBalancerServiceProxy}-object
     * @return {@code loadBalancerServiceProxy}-object
     */
    @Override
    public Object getObject() {
        update();
        return loadBalancerServiceProxy;
    }
}
