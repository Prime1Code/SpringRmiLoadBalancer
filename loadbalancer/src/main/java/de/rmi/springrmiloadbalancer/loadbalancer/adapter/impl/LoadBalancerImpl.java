package de.rmi.springrmiloadbalancer.loadbalancer.adapter.impl;

import de.rmi.springrmiloadbalancer.loadbalancer.adapter.LoadBalancer;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * {@link LoadBalancer} implementation. The following load balancing strategies are currently implemented: <br>
 *     <ul>
 *         <li>round-robin</li>
 *     </ul>
 * Currently the load balancer only supports stateless remote calls
 */
public class LoadBalancerImpl implements LoadBalancer {

    private ArrayList<HashMap<String,String>> availableServers = new ArrayList<>();
    private int currentServer = 0;

    /**
     * Gets the next available server configuration (url, port)
     * @return HashMap with configuration (url, port) for next server
     */
    @Override
    public HashMap<String, String> getAvailableServer() {
        if (currentServer >= availableServers.size()) {
            currentServer = 0;
        }
        return availableServers.get(currentServer++); // round-robin
    }

    /**
     * Registers a new server configuration (url, port) on the load balancer
     * @param server HashMap with server configuration (url, port)
     * @return REGISTER_OK = registration successful <br> REGISTER_ERROR = registration error
     */
    @Override
    public int registerServer(HashMap<String, String> server) {
        System.out.println("server = " + server);
        if (server != null &&
                server.containsKey(URL) &&
                server.containsKey(PORT)) {
            if (availableServers.add(server)) {
                return REGISTER_OK;
            }
        }
        return REGISTER_ERROR;
    }
}
