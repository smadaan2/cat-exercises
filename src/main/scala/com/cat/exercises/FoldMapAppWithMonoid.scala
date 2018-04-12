package com.cat.exercises

import cats._
import cats.implicits._

object FoldMapAppWithMonoid extends App{
  def foldMap[A,B: Monoid](v: Vector[A])(fn: A=>B): B = {
    v.foldLeft(Monoid[B].empty)((x,y) => Monoid[B].combine(x,fn(y)))
  }

  val result = foldMap(Vector(1, 2, 3))(identity)

  println(s"result::$result")
}
