package com.kensai.grpc.average

import java.util.concurrent.CountDownLatch

import com.kensai.grpc.proto.average.{AverageRequest, AverageResponse, AverageServiceGrpc}
import com.kensai.grpc.proto.average.AverageServiceGrpc.{AverageServiceBlockingStub, AverageServiceStub}
import com.kensai.grpc.{Host, Port}
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver

object MainAverageClient {

  def main(args: Array[String]): Unit = {
    println("MainAverageClient")

    val channel = ManagedChannelBuilder.forAddress(Host, Port)
      .usePlaintext // To replace with SSL
      .build

    val blockingStub = AverageServiceGrpc.stub(channel)

    val result = send(blockingStub, List(1, 2, 3))
    println(s"MainAverageClient - result is $result")
    if (result != 2.0) throw new RuntimeException(s"Result[$result] should be equal to 2.0")

  }

  def send(stub: AverageServiceStub, values: List[Int]): Double = {
    val latch = new CountDownLatch(1);

    var response: Double = 0.0

    val responseObserver = stub.computeAverage(new StreamObserver[AverageResponse]() {
      override def onNext(value: AverageResponse): Unit =
        response = value.average

      override def onError(t: Throwable): Unit = {}

      override def onCompleted(): Unit = latch.countDown()
    })

    // Send requests
    values
      .map(AverageRequest(_))
      .foreach(responseObserver.onNext)

    responseObserver.onCompleted()

    // Wait server response
    try {
      latch.await()
    } catch {
      case e: Exception => e.printStackTrace()
    }

    response
  }
}
