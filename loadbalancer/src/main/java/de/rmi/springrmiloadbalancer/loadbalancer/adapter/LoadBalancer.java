package de.rmi.springrmiloadbalancer.loadbalancer.adapter;

import java.util.HashMap;

/**
 * Load balancer interface. The following load balancing strategies are currently implemented: <br>
 *     <ul>
 *         <li>round-robin</li>
 *     </ul>
 * Currently the load balancer only supports stateless remote calls
 */
public interface LoadBalancer {

    public static final String URL = "URL";
    public static final String PORT = "PORT";
    public static final int REGISTER_OK = 0;
    public static final int REGISTER_ERROR = -1;

    /**
     * Gets the next available server configuration (url, port)
     * @return HashMap with configuration (url, port) for next server
     */
    public HashMap<String, String> getAvailableServer();

    /**
     * Registers a new server configuration (url, port) on the load balancer
     * @param server HashMap with server configuration (url, port)
     * @return REGISTER_OK = registration successful <br> REGISTER_ERROR = registration error
     */
    public int registerServer (HashMap<String, String> server);
}
