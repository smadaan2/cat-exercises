package com.cat.exercises
import cats.data.EitherT._
import cats.data.OptionT._
import cats.data.{EitherT, OptionT}
import cats.implicits._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import cats.syntax.applicative._
import cats.instances._

object CatMonadTransformerApp extends App {

  type ListOption[A] = OptionT[List, A]

  //We can create instances of ListOption using the OptionT constructor,or more conveniently using pure:

  val result1: ListOption[Int] = OptionT(List(Option(10)))

  //val result2: ListOption[Int] = 32.pure[ListOption]

  type ErrorOr[A] = Either[String, A]

  type ErrorOrOption[A] = OptionT[ErrorOr, A]

  //val a: ErrorOrOption[Int] = 10.pure[ErrorOrOption]

  //For example, let’s create a Future of an Either of Option. Once again we build this from the inside out with an OptionT of an EitherT of Future

//  type FutureEither[A] = EitherT[Future, String, A]
//  type FutureEitherOption[A] = OptionT[FutureEither, A]
//
//  val futureEitherOr: FutureEitherOption[Int] =
//    for {
//      a <- 10.pure[FutureEitherOption]
//      b <- 32.pure[FutureEitherOption]
//    } yield a + b
//
//
//  // Extracting the untransformed monad stack. Each call to value unpacks a single monad transformer. We may need more than one call to completely unpack a large stack.
//  futureEitherOr.value.value


  //Many monads in Cats are defined using the corresponding transformer and the Id monad.

}
