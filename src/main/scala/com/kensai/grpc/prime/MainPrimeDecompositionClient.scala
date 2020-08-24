package com.kensai.grpc.prime

import com.kensai.grpc.proto.prime.PrimeDecompositionServiceGrpc.PrimeDecompositionServiceBlockingStub
import com.kensai.grpc.proto.prime.{PrimeDecompositionRequest, PrimeDecompositionServiceGrpc}
import com.kensai.grpc.{Host, Port}
import io.grpc.{ManagedChannelBuilder, StatusRuntimeException}

object MainPrimeDecompositionClient {

  def main(args: Array[String]): Unit = {
    println("MainPrimeDecompositionClient")

    val channel = ManagedChannelBuilder.forAddress(Host, Port)
      .usePlaintext // To replace with SSL
      .build

    val blockingStub = PrimeDecompositionServiceGrpc.blockingStub(channel)

    // Expect to print 3 and 5
    ask(blockingStub, 15)

    // Expect to throw IllegalArgumentException
//    try {
//      ask(blockingStub, -1)
//    } catch {
//      case e: StatusRuntimeException => e.printStackTrace()
//    }
  }

  private def ask(blockingStub: PrimeDecompositionServiceBlockingStub, value: Int) = {
    println(s"MainPrimeDecompositionClient - send request($value)")
    val request = PrimeDecompositionRequest(value)
    val responses = blockingStub.decompose(request).
      toSeq
      .map(_.primeNumber)

    println(s"MainPrimeDecompositionClient - received responses: $responses")
  }
}
