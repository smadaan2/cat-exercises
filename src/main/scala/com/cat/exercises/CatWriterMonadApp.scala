package com.cat.exercises
import cats._
import cats.implicits._
import cats.data._

// for tell or // for writer

object CatWriterMonadApp extends App{

  val writer = Writer(Vector("It was the best of times", "it was the worst of times"), 1859)

  type Logged[A] = Writer[Vector[String], A]

  //Cats provides a way of crea ng Writers specifying only the log or the result. If we only have a result we can use the standard pure syntax.
  // To do this we must have a Monoid[W] in scope so Cats knows how to produce an empty log
  123.pure[Logged]

  //If we have a log and no result we can create a Writer[Unit] using the tell syntax

  Vector("msg1", "msg2", "msg3").tell

  //If we have both a result and a log, we can either use Writer.apply or we can use the writer syntax

  val a = Writer(Vector("msg1", "msg2", "msg3"), 123) //or
  val b = 123.writer(Vector("msg1", "msg2", "msg3"))

  //We can extract the result and log from a Writer using the value and written methods
  val aResult: Int =  a.value
  val aLog: Vector[String] = a.written

  //We can extract both values at the same time using the run method:

  val (log, result) = b.run


  //The log in a Writer is preserved when we map or flatMap over it
  val writer1 = for {
    a <- 10.pure[Logged]
    _ <- Vector("a", "b", "c").tell
    b <- 32.writer(Vector("x", "y", "z"))
  } yield a + b

  writer1.run

  //we can transform the log in a Writer with the mapWritten method
  val writer2 = writer1.mapWritten(_.map(_.toUpperCase))

  writer2.run

  //We can transform both log and result simultaneously using bimap or mapBoth.
  // bimap takes two func on parameters, one for the log and one for the result. mapBoth takes a single func on that accepts two parameters:

  val writer3 = writer1.bimap(
    log => log.map(_.toUpperCase),
    res => res * 100
  )

  writer3.run

  val writer4 = writer1.mapBoth { (log, res) =>
    val log2 = log.map(_ + "!")
    val res2 = res * 1000
    (log2, res2)
  }

  writer4.run

  //Finally, we can clear the log with the reset method and swap log and result with the swap method:

  val writer5 = writer1.reset
  writer5.run

  val writer6 = writer1.swap
  writer6.run



}
