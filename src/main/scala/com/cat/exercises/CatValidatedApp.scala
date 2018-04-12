package com.cat.exercises

import cats._
import cats.implicits._
import cats.data._

object CatValidatedApp extends App {

  /**By now we are familiar with the fail-fast error handling behaviour of Either.
    * Furthermore, because Either is a monad, we know that the seman cs of product are the same as those for flatMap.
    * In fact, it is impossible for us to design a monadic data type that implements error accumula ng seman cs without breaking the consistency of these two methods.
    * Fortunately, Cats provides a data type called Validated that has an instance of Semigroupal but no instance of Monad.
    * The implementation of product is therefore free to accumulate errors
    */

  type AllErrorsOr[A] = Validated[List[String], A]

  Semigroupal[AllErrorsOr].product(
    Validated.invalid(List("Error 1")),
    Validated.invalid(List("Error 2"))
  )

  //*******Creating Instances of Validated

  val v: Validated[Nothing, Int] = Validated.Valid(123)
  val i: Validated[List[String], Nothing] = Validated.Invalid(List("Badness"))

  println(s"Instance of Validated::::$v")
  println(s"Instance of Validated::::$i")

  val v1 = Validated.valid[List[String], Int](123)
  val i1 = Validated.invalid[List[String], Int](List("Badness"))

  123.valid[List[String]]
  List("Badness").invalid[Int]

  //As a fourth op on we can use pure and raiseError // for raiseError

  type ErrorsOr[A] = Validated[List[String], A]

  123.pure[ErrorsOr]
  List("Badness").raiseError[ErrorsOr, Int]

  //Finally, there are helper methods to create instances of Validated from different sources.

  val a = Validated.catchOnly[NumberFormatException]("foo".toInt)
  val b = Validated.catchNonFatal(sys.error("Badness"))
  val c = Validated.fromTry(scala.util.Try("foo".toInt))
  val d = Validated.fromEither[String, Int](Left("Badness"))
  val e = Validated.fromOption[String, Int](None, "Badness")

  //*******Combining Instances of Validated

  import cats.syntax.apply._

  type AllErrorsOr1[A] = Validated[Vector[String], A]

  val m = (
    Vector("1").invalid[Int],
    Vector("2").invalid[Int]
  ).tupled

  val n = (
    NonEmptyVector.of("Error 1").invalid[Int],
    NonEmptyVector.of("Error 2").invalid[Int]
  ).tupled

}
