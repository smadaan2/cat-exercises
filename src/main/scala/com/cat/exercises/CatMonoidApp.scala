package com.cat.exercises
import cats._
import cats.implicits._

object CatMonoidApp extends App {

  val m = Monoid[String].combine("Hi ", "there") //or
  Monoid.apply[String].combine("Hi ", "there")

  println(s"Combine Function of String monoid $m")

  Monoid[String].empty

  val a = Option(22)
  val b = Option(20)
  val n = Monoid[Option[Int]].combine(a, b)

  println(s"Combine Function of Option monoid $n")


}
