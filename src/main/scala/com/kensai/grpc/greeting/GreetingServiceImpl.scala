package com.kensai.grpc.greeting

import com.kensai.grpc.proto.greeting._

import scala.concurrent.Future

class GreetingServiceImpl extends GreetingServiceGrpc.GreetingService {

  override def greet(request: GreetingRequest): Future[GreetingResponse] = {
    val maybeResponse = request.person
      .map(person => s"Hello ${person.firstName} ${person.lastName}")
      .map(GreetingResponse(_))
      .orElse(None)
    maybeResponse.map(Future.successful).getOrElse(Future.failed(new IllegalArgumentException("No person to greet")))
  }
}
