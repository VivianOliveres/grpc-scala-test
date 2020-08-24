package com.kensai.grpc.average

import com.kensai.grpc.proto.average.{AverageRequest, AverageResponse}
import com.kensai.grpc.proto.average.AverageServiceGrpc.AverageService
import io.grpc.Status
import io.grpc.stub.StreamObserver

class AverageServiceImpl extends AverageService {

  override def computeAverage(responseObserver: StreamObserver[AverageResponse]): StreamObserver[AverageRequest] = {
    var sum = 0.0
    var length = 0

    new StreamObserver[AverageRequest] {
      override def onNext(request: AverageRequest): Unit = {
        sum += request.value
        length += 1
      }

      override def onError(t: Throwable): Unit =
        Status.INTERNAL.withCause(t).asRuntimeException()

      override def onCompleted(): Unit = {
        val result = sum / length
        responseObserver.onNext(AverageResponse(result))
        responseObserver.onCompleted()
      }
    }
  }
}
