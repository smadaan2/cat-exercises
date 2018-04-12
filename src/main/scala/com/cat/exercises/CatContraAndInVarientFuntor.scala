package com.cat.exercises
import cats._
import cats.implicits._

/***
  * Contravariant and Invariant used for converting Monoid if one of default context is given
  * In below case we are able to generate Monoid[Symbol] by using Monoid[String]
  */
object CatContraAndInVarientFuntor extends App {

  implicit val symbolMonoid: Monoid[Symbol] = Monoid[String].imap(Symbol.apply)(_.name)

  case class Test(a: Int)

  implicit val testMonoid: Monoid[Test] = Monoid[Int].imap(Test.apply)(_.a)

}
