package com.kensai.grpc.greeting

import com.kensai.grpc._
import com.kensai.grpc.proto.greeting._
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.{ExecutionContext, Future}

object MainGreetingServer {

  def main(args: Array[String]): Unit = {
    println("MainGreetingServer")

    val server: Server = ServerBuilder.forPort(Port)
      .addService(GreetingServiceGrpc.bindService(new GreetingServiceImpl, ExecutionContext.global))
      .build
      .start

    println("MainGreetingServer - server started")

    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      server.shutdown()
      System.err.println("*** server shut down")
    }

    server.awaitTermination()
  }
}
