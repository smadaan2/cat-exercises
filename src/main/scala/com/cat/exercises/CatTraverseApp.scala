package com.cat.exercises
import cats._
import cats.implicits._

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

object CatTraverseApp extends App {

  //Cats’ Traverse type class generalises these patterns to work with any type of Applicative: Future, Option, Validated, and so on.
  val hostnames = List("alpha.example.com",
    "beta.example.com",
    "gamma.demo.com"
  )

  def getUptime(hostname: String): Future[Int] =
    Future(hostname.length * 60)

  /////////
  val allUptimes: Future[List[Int]] =
    Future.traverse(hostnames)(getUptime)

  Await.result(allUptimes, 1.second)
  ////////

  //Traversing with Applicatives // for mapN

  def newCombine(accum: Future[List[Int]],
                 host: String): Future[List[Int]] =
    (accum, getUptime(host)).mapN(_ :+ _)
}
