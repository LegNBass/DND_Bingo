/*
 * Copyright (c) 2021. Don't steal this.
 */

package dev.armentrout.dnd_bingo

import io.finch._
import org.scalatest.FunSuite

class MainTest extends FunSuite {
  test("healthcheck") {
    assert(Main.healthCheck(Input.get("/")).awaitValueUnsafe().contains("OK"))
  }

  test("helloWorld") {
    assert(Main.helloWorld(Input.get("/hello")).awaitValueUnsafe().contains(Main.Message("World")))
  }

  test("hello") {
    assert(Main.hello(Input.get("/hello/foo")).awaitValueUnsafe().contains(Main.Payload(Main.Message("foo"))))
  }
}