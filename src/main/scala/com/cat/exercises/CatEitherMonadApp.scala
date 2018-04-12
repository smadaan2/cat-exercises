package com.cat.exercises
import cats._
import cats.implicits._

object CatEitherMonadApp extends App{

  val a = 3.asRight[String]
  val b = 4.asRight[String]

  for {
    x <- a
    y <- b
  } yield x*x + y*y


  //These “smart constructors” or "extension methods have advantages over Left.apply and Right.apply because they return results of type Either instead of Left and Right.

//  def countPositive(nums: List[Int]) = nums.foldLeft(Right(0)) { (accumulator, num) =>
//      if(num > 0) {
//        accumulator.map(_ + 1)
//      } else {
//        Left("Negative. Stopping!")
//      } }         // giving compile time error bcoz 1) the compiler infers the type of the accumulator as Right instead of Either; 2) we didn’t specify type parameters for Right.apply so the compiler infers the le  parameter as Nothing.


  def countPositive(nums: List[Int]) = nums.foldLeft(0.asRight[String]) { (accumulator, num) =>
    if(num > 0) {
      accumulator.map(_ + 1)
    } else {
      Left("Negative. Stopping!")
    } }

  //catchOnly and catchNonFatal methods::: are great for capturing Exceptions as instances of Either:

  Either.catchOnly[NumberFormatException]("foo".toInt)
  Either.catchNonFatal(sys.error("Badness"))

  //There are also methods for creating an Either from other data types:

  Either.fromTry(scala.util.Try("foo".toInt))
  Either.fromOption[String, Int](None, "Badness")


  //orElse and getOrElse:::::: to extract values from the right side or return a default

  "Error".asLeft[Int].getOrElse(0)
  "Error".asLeft[Int].orElse(2.asRight[String])

  // The ensure method allows us to check whether the right-hand value satisfies a predicate:

  (-1).asRight[String].ensure("Must be non-negative!")(_ > 0)

  //recover and recoverWith methods::::: provide similar error handling to their namesakes on Future

  "error".asLeft[Int].recover {
    case str: String => -1
  }

  "error".asLeft[Int].recoverWith {
    case str: String => Right(-1)
  }

  // leftMap and bimap methods:::: to complement map:

  "foo".asLeft[Int].leftMap(_.reverse)

  6.asRight[String].bimap(_.reverse, _ * 7)
  "bar".asLeft[Int].bimap(_.reverse, _ * 7)

  //swap method:::: lets us exchange le  for right:

  123.asRight[String].swap



}
