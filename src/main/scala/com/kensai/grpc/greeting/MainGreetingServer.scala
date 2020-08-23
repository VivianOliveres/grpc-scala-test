package com.kensai.grpc.greeting

import com.kensai.grpc.greeting.proto.greeting.{GreetingRequest, Person}

object MainGreetingServer {

  def main(args: Array[String]): Unit = {
    println("lol")

    println(GreetingRequest().withPerson(Person().withFirstName("Vivian").withLastName("Oliveres")))
  }
}
