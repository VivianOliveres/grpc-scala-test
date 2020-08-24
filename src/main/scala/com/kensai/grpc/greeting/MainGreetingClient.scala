package com.kensai.grpc.greeting

import com.kensai.grpc._
import com.kensai.grpc.proto.greeting.GreetingServiceGrpc.GreetingServiceBlockingStub
import com.kensai.grpc.proto.greeting.{GreetingRequest, GreetingServiceGrpc, Person}
import io.grpc.{ManagedChannel, ManagedChannelBuilder}

object MainGreetingClient {

  def main(args: Array[String]): Unit = {
    println("MainGreetingClient")

    val channel = ManagedChannelBuilder.forAddress(Host, Port)
      .usePlaintext // To replace with SSL
      .build

    val blockingStub = GreetingServiceGrpc.blockingStub(channel)

    askGreeting(blockingStub)
    askAnonymousGreeting(blockingStub)
  }

  private def askGreeting(blockingStub: GreetingServiceBlockingStub): Unit = {
    println(s"Sending GreetingRequest(Vivian, Oliveres)")
    val request = GreetingRequest(Some(Person("Vivian", "Oliveres")))

    val response = blockingStub.greet(request)
    println(s"Response received: $response")
  }

  /**
   * Should throw a StatusRuntimeException
   */
  private def askAnonymousGreeting(blockingStub: GreetingServiceBlockingStub): Unit = {
    println(s"Sending GreetingRequest(None)")
    val request = GreetingRequest(None)

    val response = blockingStub.greet(request)
    println(s"Response received: $response")
  }

}
