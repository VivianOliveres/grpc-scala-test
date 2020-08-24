package com.kensai.grpc.average

import com.kensai.grpc.Port
import com.kensai.grpc.proto.average.AverageServiceGrpc
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.ExecutionContext

object MainAverageServer {

  def main(args: Array[String]): Unit = {
    println("MainAverageServer")

    val server: Server = ServerBuilder.forPort(Port)
      .addService(AverageServiceGrpc.bindService(new AverageServiceImpl, ExecutionContext.global))
      .build
      .start

    println("MainAverageServer - server started")

    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      server.shutdown()
      System.err.println("*** server shut down")
    }

    server.awaitTermination()
  }
}
