package com.kensai.grpc.prime

import com.kensai.grpc.Port
import com.kensai.grpc.proto.prime.PrimeDecompositionServiceGrpc
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.ExecutionContext

object MainPrimeDecompositionServer {

  def main(args: Array[String]): Unit = {
    println("MainPrimeDecompositionServer")

    val server: Server = ServerBuilder.forPort(Port)
      .addService(PrimeDecompositionServiceGrpc.bindService(new PrimeDecompositionServiceImpl, ExecutionContext.global))
      .build
      .start

    println("MainPrimeDecompositionServer - server started")

    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      server.shutdown()
      System.err.println("*** server shut down")
    }

    server.awaitTermination()
  }
}
