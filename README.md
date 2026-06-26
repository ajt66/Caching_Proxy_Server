# Caching_Proxy_Server

A CLI tool that starts a caching server to cache responses from other servers.

---

# Usage

* Starts a proxy server that forwards requests to a origin server, caches the response and serves the request on a
separate uri.
* Returns the cached response for the same request on the second attempt.  
* Port can also be configured with the CLI command.
* Logs whether the cache was hit or missed for the Origin url.

---

## Tech Stack

* Backend
  * Java 21
  * [Maven](https://maven.apache.org/) - Dependency management
  * [Spring Boot](https://spring.io) - Framework
  * Spring WebFlux - Spring Boot dependency for reactive programming
  * [Caffeine Cache](https://github.com/ben-manes/caffeine) - High performance and lightweight Cache
  * [Pico CLI](https://picocli.info/) - CLI tool

---

# Pre-Requisites

* Java 21
* Maven 3.5.14 or higher
* Postman(Optional but recommended)
---

# Jar Assembly

Run the following command in your terminal to assemble the jar file for the application

```sh
./mvnw clean package
```
or
```sh
mvn clean package
```

# Starting the application

Run the following command after assembling the jar file in your terminal to start the proxy server

```sh
java -jar target/caching_server-0.0.1-SNAPSHOT.jar --port <port number> --origin <Origin Server url>
```

As an example, the port can be 3000 and the origin url can be dummyjson.com
```sh
java -jar target/caching_server-0.0.1-SNAPSHOT.jar --port 3000 --origin https://dummyjson.com
```

This will start the proxy server and forward requests to dummyjson.com

Then Postman or a terminal can be used to send requests to an api.

# Example Testing Workflow

## Through terminal

After starting the server with the command above, open the terminal and run the commands below to return a
list of all / specific product from dummyjson.com 

```sh
curl.exe -i http://localhost:3000/products
```

the proxy server will forward the request to dummyjson.com/products and retrieve the entire list of products

(Note: The response can sometimes take too long to process the entire list and returns an internal error instead.
only this error does not occur in postman)

or
```sh
curl.exe -i http://localhost:3000/products/1
```

the proxy server will forward the request to dummyjson.com/products/1 and retrieve the product with id 1

## Through Postman(Recommended)

After starting the server with the command above, open the app and run this command through get

```sh
localhost:3000/products
```

the proxy server will forward the request to dummyjson.com/products and retrieve the entire list of products

or
```sh
localhost:3000/products/1
```

the proxy server will forward the request to dummyjson.com/products/1 and retrieve the product with id 1

---
