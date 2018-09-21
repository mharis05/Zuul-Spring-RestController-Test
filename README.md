# Spring-RestController-Test

## Introduction:
A short project that validates response from a Rest Controller listening to Netflix Zuul Proxy

## Pre-Requesites:
A netflix-zuul proxy server running with reverse proxying on port 5000 (configurable - see below). This proxy should filter requests to "/api/image" endpoint and converts value of "image" property to Base64 before forwarding it to web server.

## Explanation:
This project contains a REST Controller that exposes ah HTTP POST endpoint "api/image" and listens on port 5000
This port can be changed if required in `application.properties` property: `server.port`

### Tests:
There are two tests:
1. Verify value of "image" property after passing proxy is base 64 encoded
2. Verify the encoded value is in fact as provided in the initial payload

## How to run Tests:
Note: There is no need to run the tomcat web server manually in order to run tests as the tests are annotated by `SpringBootTest` with configured `webEnvironment` due to which, Apache Tomcat server spins up before any test is executed and closes afterwards.

### Steps: 
0. Make sure Zuul proxy is up and running 

#### IDE:
1. Run desired @Test method in `SpringBootWebApplicationTests` class (/src/test/java/com/zuulDemo/SpringBootWebApplicationTests.java) if you need to execute a particular test (Tests are independent)
2. Run `SpringBootWebApplicationTests` class to execute all tests

#### Maven:
1. Run command: `mvn test` or `mvn clean test`
