# gRPC Scala Test

This project aims to implement basic scenariis with gRpc.

Its based on :
* [Scala](https://scala-lang.org/)
* [scalaPB](https://scalapb.github.io/)
* [Gradle](https://gradle.org/)
* [gRPC](https://grpc.io/)
* [Google Protobuf Plugin](https://github.com/google/protobuf-gradle-plugin)

## Greeting Scenario

In this unary use case, client send one message (first_name and last_name) and then server 
respond with one greeting message (Hello ${first_name} ${last_name}).

