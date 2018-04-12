package com.cat.exercises
import cats._
import cats.implicits._
import cats.data._

object CatReaderMonadApp extends App {

  case class Cat(name: String, favoriteFood: String)

  //We can create a Reader[A, B] from a func on A => B using the Reader.apply constructor
  val catName: Reader[Cat, String] = Reader(cat => cat.name)

  // extract the func on again using the Reader's run method
  catName.run(Cat("Garfield", "lasagne"))

  //The map method simply extends the computa on in the Reader by passing its result through a func on:
  val greetKitty: Reader[Cat, String] = catName.map(name => s"Hello ${name}")

  //The flatMap method is more interes ng. It allows us to combine readers that depend on the same input type.
  val feedKitty: Reader[Cat, String] = Reader(cat => s"Have a nice bowl of ${cat.favoriteFood}")

  val greetAndFeed: Reader[Cat, String] =
  for {
    greet <- greetKitty
    feed  <- feedKitty
  } yield s"$greet. $feed."

  greetAndFeed(Cat("Garfield", "lasagne"))


  //Use Case: The classic use of Readers is to build programs that accept a configura on as a parameter.
  //Our configuration will consist of two databases: a list of valid users and a list of their passwords.

  case class Db(usernames: Map[Int, String], passwords: Map[String, String])

  type DbReader[A] = Reader[Db,A]

  def findUsername(userId: Int): DbReader[Option[String]] = ???

  def checkPassword(username: String, password: String): DbReader[Boolean] = ???

  def checkLogin(userId: Int, password: String): DbReader[Boolean] = ???

  val users = Map(
    1 -> "dade",
    2 -> "kate",
    3 -> "margo"
  )

  val passwords = Map(
    "dade"  -> "zerocool",
    "kate"  -> "acidburn",
    "margo" -> "secret")

  val db = Db(users, passwords)

  checkLogin(1, "zerocool").run(db)


}
