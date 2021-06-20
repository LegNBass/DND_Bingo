/*
 * Copyright (c) 2021. Don't steal this.
 */

package dev.armentrout.dnd_bingo

import cats.effect.IO
import com.twitter.finagle.{Http, Service}
import com.twitter.finagle.http.{Request, Response}
import com.twitter.util.Await
import io.finch._
import io.finch.catsEffect._
import io.finch.circe._
import io.circe.generic.auto._

object Main extends App {

  case class Message(hello: String)

  case class Payload(data: Message)

  def healthCheck: Endpoint[IO, String] = get(pathEmpty) {
    Ok("OK")
  }

  def helloWorld: Endpoint[IO, Message] = get("hello") {
    Ok(Message("World"))
  }

  def hello: Endpoint[IO, Payload] = get("hello" :: path[String]) { s: String =>
    Ok(Payload(Message(s)))
  }

  def service: Service[Request, Response] = Bootstrap
    .serve[Text.Plain](healthCheck)
    .serve[Application.Json](helloWorld :+: hello)
    .toService

  Await.ready(Http.server.serve(":8081", service))
}