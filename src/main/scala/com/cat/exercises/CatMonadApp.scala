package com.cat.exercises
import cats._
import cats.implicits._

// for Monad
object CatMonadApp extends App {

  val opt1: Option[Int] = Monad[Option].pure(3)
  val opt2: Option[Int] = Monad[Option].flatMap(opt1)(a => Some(a + 2))
  val opt3: Option[Int] = Monad[Option].map(opt2)(a => 100 * a)

  val m: Option[Int] = for {
    a <- opt1
    b <- opt2
    c <- opt3
  } yield a+b+c

  println(s"Composition of Monad::: $m")

}
