package com.cat.exercises
import cats._
import cats.implicits._

object CatFuntorApp extends App {

  //Creating instances of Functor
  val list1 = List(1, 2, 3)
  val list2 = Functor[List].map(list1)(_ * 2)
  val option1 = Option(123)
  val option2 = Functor[Option].map(option1)(_.toString)

  println(s"Option instance of Functor::: $option2")

  //Lifting Example

  val func = (x: Int) => x + 1
  val liftedFunc = Functor[Option].lift(func)
  println(s"Functor lifting example::::${liftedFunc(Option(1))}")

  //Scala’s Function1 type doesn’t have a map method

  val func1 = (a: Int) => a + 1
  val func2 = (a: Int) => a * 2
  val func3 = (a: Int) => a + "!"
  //val func4 = func1.map(func2).map(func3)

  //
}
