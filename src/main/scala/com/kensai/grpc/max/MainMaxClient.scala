package com.kensai.grpc.max

import java.util.concurrent.CountDownLatch

import com.kensai.grpc.proto.max.{MaxRequest, MaxResponse, MaxServiceGrpc}
import com.kensai.grpc.proto.max.MaxServiceGrpc.MaxServiceStub
import com.kensai.grpc.{Host, Port}
import io.grpc.ManagedChannelBuilder
import io.grpc.stub.StreamObserver

object MainMaxClient {

  def main(args: Array[String]): Unit = {
    println("MainMaxClient")

    val channel = ManagedChannelBuilder.forAddress(Host, Port)
      .usePlaintext // To replace with SSL
      .build

    val blockingStub = MaxServiceGrpc.stub(channel)

    val result = send(blockingStub, List(1, 3, 2))
    println(s"MainMaxClient - result is $result")
    if (result != List(1, 3)) throw new RuntimeException(s"Result[$result] should be equal to List(1, 3)")
  }

  def send(stub: MaxServiceStub, values: List[Long]): List[Long] = {
    val latch = new CountDownLatch(1);

    var result: List[Long] = List()

    val responseObserver = stub.computeMax(new StreamObserver[MaxResponse]() {
      override def onNext(response: MaxResponse): Unit =
        result = result :+ response.max

      override def onError(t: Throwable): Unit =
        t.printStackTrace()

      override def onCompleted(): Unit =
        latch.countDown()
    })

    // Send requests
    values
      .map(MaxRequest(_))
      .foreach(responseObserver.onNext)

    responseObserver.onCompleted()

    // Wait server response
    try {
      latch.await()
    } catch {
      case e: Exception => e.printStackTrace()
    }

    result
  }
}
