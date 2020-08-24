package com.kensai.grpc.max

import com.kensai.grpc.proto.max.{MaxRequest, MaxResponse}
import com.kensai.grpc.proto.max.MaxServiceGrpc.MaxService
import io.grpc.Status
import io.grpc.stub.StreamObserver

class MaxServiceImpl extends MaxService {

  override def computeMax(responseObserver: StreamObserver[MaxResponse]): StreamObserver[MaxRequest] = {
    var currentMax = Long.MinValue

    new StreamObserver[MaxRequest]() {
      override def onNext(request: MaxRequest): Unit =
        if (request.value > currentMax) {
          currentMax = request.value
          responseObserver.onNext(MaxResponse(currentMax))
        }

      override def onError(t: Throwable): Unit =
        responseObserver.onError(Status.INTERNAL.withCause(t).asRuntimeException())

      override def onCompleted(): Unit =
        responseObserver.onCompleted()
    }
  }
}
