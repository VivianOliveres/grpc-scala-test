![Scala CI](https://github.com/VivianOliveres/grpc-scala-test/workflows/Scala%20CI/badge.svg?branch=master)

# gRPC Scala Test

This project aims to implement basic scenarios with gRpc.

Its based on :
* [Scala](https://scala-lang.org/)
* [scalaPB](https://scalapb.github.io/)
* [Gradle](https://gradle.org/)
* [gRPC](https://grpc.io/)

## Greeting scenario

In this unary use case, client send one message (first_name and last_name) and then server 
respond with one greeting message (Hello ${first_name} ${last_name}).

## Prime number decomposition scenario

In this streaming server use case, client send one message (containing a long value) and 
then server respond with all prime numbers that compose this value.

For instance, if client send 15, then server should respond 3 and then 5 (because 3x5=15 
and 3 and 5 are prime numbers).

## Average computing scenario

In this streaming client use case, client send multiple messages (containing a long value) and 
then server respond with the average of all numbers received.

For instance, if client send 1, 2 and 3, then server should respond 2.0.

## Max computing scenario

In this bi directional streaming use case, client send multiple messages (containing a long value) and 
then server respond only when this value is the max that it received.

For instance, if client send 1, 3 and 2, then server should respond 1 and 3.
