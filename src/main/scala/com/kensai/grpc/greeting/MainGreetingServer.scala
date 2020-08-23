package com.kensai.grpc.greeting

import com.kensai.grpc.proto.greeting.{GreetingRequest, Person}

object MainGreetingServer {

  def main(args: Array[String]): Unit = {
    println("main")

    println(GreetingRequest().withPerson(Person().withFirstName("Vivian").withLastName("Oliveres")))
  }
}
