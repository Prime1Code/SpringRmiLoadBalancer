package de.rmi.springrmiloadbalancer.server.adapter.impl;

import de.rmi.springrmiloadbalancer.server.adapter.HelloWorld;

/**
 * Implementation of {@link HelloWorld} service interface.
 */
public class HelloWorldImpl implements HelloWorld {

    /**
     * Returns a dummy string to a client
     * @return "Hello World from Server" to a client
     */
    @Override
    public String helloWorldFromServer() {
        return "Hello World from Server";
    }

    /**
     * Sums up to parameters a and b and returns their sum
     * @param a
     * @param b
     * @return int
     */
    @Override
    public int sum(int a, int b) {
        return a+b;
    }
}
