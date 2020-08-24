package com.kensai.grpc.greeting

import com.kensai.grpc.proto.greeting.{GreetingRequest, GreetingServiceGrpc, Person}
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

object MainGreetingClient {

  def main(args: Array[String]): Unit = {
    println("MainGreetingClient")

    val channel = ManagedChannelBuilder.forAddress(Host, Port)
      .usePlaintext // To replace with SSL
      .build

    askGreeting(channel)
    askAnonymousGreeting(channel)
  }

  def askGreeting(channel: ManagedChannel): Unit = {
    println(s"Sending GreetingRequest(Vivian, Oliveres)")
    val request = GreetingRequest(Some(Person("Vivian", "Oliveres")))

    val blockingStub = GreetingServiceGrpc.blockingStub(channel)
    val response = blockingStub.greet(request)

    println(s"Response received: $response")
  }

  /**
   * Should throw a StatusRuntimeException
   */
  def askAnonymousGreeting(channel: ManagedChannel): Unit = {
    println(s"Sending GreetingRequest(None)")
    val request = GreetingRequest(None)

    val blockingStub = GreetingServiceGrpc.blockingStub(channel)
    val response = blockingStub.greet(request)

    println(s"Response received: $response")
  }

}
