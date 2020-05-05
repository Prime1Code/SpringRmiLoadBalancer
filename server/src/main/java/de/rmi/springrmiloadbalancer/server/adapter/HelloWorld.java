package de.rmi.springrmiloadbalancer.server.adapter;

/**
 * HelloWorld service interface
 */
public interface HelloWorld {

    /**
     * Prints "Hello World from Server"
     * @return String
     */
    public String helloWorldFromServer ();

    /**
     * Sums up to parameters a and b and returns their sum
     * @param a
     * @param b
     * @return int
     */
    public int sum (final int a, final int b);
}
