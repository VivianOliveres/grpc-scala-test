package com.kensai.grpc.max

import com.kensai.grpc.Port
import com.kensai.grpc.proto.max.MaxServiceGrpc
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.ExecutionContext

object MainMaxServer {

  def main(args: Array[String]): Unit = {
    println("MainMaxServer")

    val server: Server = ServerBuilder.forPort(Port)
      .addService(MaxServiceGrpc.bindService(new MaxServiceImpl, ExecutionContext.global))
      .build
      .start

    println("MainMaxServer - server started")

    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      server.shutdown()
      System.err.println("*** server shut down")
    }

    server.awaitTermination()
  }
}
