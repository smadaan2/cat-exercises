package com.cat.exercises

import cats.{Monoid, _}
import cats.implicits._
import cats.instances.list._
import scala.concurrent.{Future, _}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global


object CatMapReduceCaseResult extends App {
  import FoldMapAppWithMonoid._
  val processors = Runtime.getRuntime.availableProcessors()
  def parallelFoldMap[A, B: Monoid](v: List[A])(fn: A => B): Future[B] = {
    val partition = math.ceil(v.size / processors).toInt
    Future.sequence {
      v.grouped(partition).toList.map { batch =>
        Future(foldMap(batch.toVector)(fn))
      }
    }.map(list =>foldMap(list.toVector)(identity))
  }
  val result: Int = Await.result(parallelFoldMap((1 to 100).toList)(_ + 1), 1.second)

  println(s"MapReduce Result:::$result")
}
