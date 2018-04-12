package com.cat.exercises
import cats._
import cats.implicits._

object CatFoldableApp extends App{

  //***instances as usual using Foldable.apply and call their implementations of foldLeft directly.

  val ints = List(1, 2, 3)
  Foldable[List].foldLeft(ints, 0)(_ + _)

  def bigData = (1 to 100000).toStream
  //bigData.foldRight(0L)(_ + _)   // java.lang.StackOverflowError ...

  val eval: Eval[Long] = Foldable[Stream].
      foldRight(bigData, Eval.now(0L)) { (num, eval) =>
        eval.map(_ + num)
      }

  eval.value

  //Foldable provides us with a host of useful methods defined on top of foldLeft i.e find, exists, forall, toList, isEmpty, nonEmpty

  Foldable[Option].nonEmpty(Option(42))

  //In addition to these familiar methods, Cats provides two methods that make use of Monoids i.e combineAll, foldMap

  Foldable[List].combineAll(List(1, 2, 3))  //6
  Foldable[List].foldMap(List(1, 2, 3))(_.toString) //123

  val integers = List(Vector(1, 2, 3), Vector(4, 5, 6))
  val t: Int = (Foldable[List] compose Foldable[Vector]).combineAll(integers)

  println(s"Composing Foldable:::$t")

}
