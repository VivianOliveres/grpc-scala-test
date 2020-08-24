package com.kensai.grpc.prime

import com.kensai.grpc.proto.prime.{PrimeDecompositionRequest, PrimeDecompositionResponse}
import com.kensai.grpc.proto.prime.PrimeDecompositionServiceGrpc.PrimeDecompositionService
import io.grpc.Status
import io.grpc.stub.StreamObserver

class PrimeDecompositionServiceImpl extends PrimeDecompositionService {

  override def decompose(request: PrimeDecompositionRequest, responseObserver: StreamObserver[PrimeDecompositionResponse]): Unit = {
    var rest = request.value

    if (rest <= 0) {
      responseObserver.onError(Status.INVALID_ARGUMENT.withDescription(s"Invalid value [$rest]").asRuntimeException())
    }

    if (rest != 1) {
      // First test number 2
      while (rest % 2 == 0) {
        rest = rest / 2
        responseObserver.onNext(PrimeDecompositionResponse(2))
      }

      var i = 3
      while (i <= rest) {
        if (rest % i == 0) {
          rest = rest / i
          responseObserver.onNext(PrimeDecompositionResponse(i))
        }
        i = i + 2 // +2 because even numbers should not be considered
      }
    }

    responseObserver.onCompleted()
  }
}
