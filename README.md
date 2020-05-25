![CI](https://github.com/Prime1Code/SpringRmiLoadBalancer/workflows/CI/badge.svg)

# Load balancing RMI calls with Spring

This project is an approach to load balancing RMI calls with Spring. The standard implementation of Spring Remoting has been adapted for this.

## How does it work?

There are 3 entities needed for this to work:
* Client
* Server
* Load balancer

And 3 steps for the load balancing to work:
1. The client asks the loadbalancer for a server. 
2. The load balancer returns an available server according to the load balancing-strategy. 
3. The client then invokes the methods on this server.

## Example
This project includes a small demo-project with a basic setup for the client, server and load balancer. Every new server automatically registers himself on the load balancer and is ready to process incoming RMI calls. The client invokes two methods on the server:
1. Hello world - returns "Hello world from server"
2. sum (a, b) - returns the sum of a and b

You can see which server is called from the client when the log level is set to "debug". 

### How to run?
```
java -jar loadBalancer-exec.jar --server.port=<port>
```
```
java -jar server-exec.jar --use.random.port<true|false> --server.port=<port> --load.balancer.port=<port> --load.balancer.ip=<ip>
``` 
```
java -jar client-exec.jar --load.balancer.port=<port> --load.balancer.ip=<ip>
``` 
Output
```
HelloWorldService   : Hello world from Server
HelloWorldService   : 29
```
