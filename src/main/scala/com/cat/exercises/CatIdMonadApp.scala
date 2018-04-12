package com.cat.exercises
import cats._
import cats.implicits._

// for flatMap

object CatIdMonadApp extends App {

  val a = Monad[Id].pure(3)
  val b = Monad[Id].flatMap(a)(_ + 1)

  val m = for {
    x <- a
    y <- b
  } yield x + y

  println(s"Composing Id Monad::: $m")

}
