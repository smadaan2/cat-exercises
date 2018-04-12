package com.cat.exercises
import cats._
import cats.implicits._

object CatSemiGroupalApp extends App {

  //*******Creating Instances

  val ins1: Option[(Int, String)] = Semigroupal[Option].product(Some(123), Some("abc"))

  println(s"Composing two options::: $ins1")

  val ins2: Option[(Nothing, String)] = Semigroupal[Option].product(None, Some("abc"))

  val ins3: Option[(Int, Nothing)] = Semigroupal[Option].product(Some(123), None)

 //The companion object for Semigroupal defines a set of methods on top of product.

  val ins4: Option[(Int, Int, Int)] = Semigroupal.tuple3(Option(1), Option(2), Option(3))

  val ins5: Option[(Int, Int, Int)] = Semigroupal.tuple3(Option(1), Option(2), Option.empty[Int])

  val ins6: Option[Int] = Semigroupal.map3(Option(1), Option(2), Option(3))(_ + _ + _)

  val ins7: Option[Int] = Semigroupal.map2(Option(1), Option.empty[Int])(_ + _)

  (Option(123), Option("abc")).tupled

  //Cats’ apply syntax provides a method called mapN that accepts an implicit Functor and a function of the correct arity to combine the values

  case class Cat(name: String, born: Int, color: String)

  val catInstance: Option[Cat] = (Option("Garfield"), Option(1978), Option("Orange & black")).mapN(Cat.apply)

  println(s"Creating Cat instance::::$catInstance")

  val add: (Int, Int) => Int = (a, b) => a + b

  //(Option("cats"), Option(true)).mapN(add) //error: type mismatch;

  //***********Apply syntax also has contramapN and imapN methods that accept Contravariant and Invariant functors.
  //In below case we are able to generate Monoid[Dog] by using Monoid[String], Monoid[Int] and Monoid[List[String]]

  case class Dog(name: String, yearOfBirth: Int, favoriteFoods: List[String])

  val tupleToDog: (String, Int, List[String]) => Dog = Dog.apply _

  val dogToTuple: Dog => (String, Int, List[String]) = dog => (dog.name, dog.yearOfBirth, dog.favoriteFoods)

  implicit val dogMonoid: Monoid[Dog] = (
    Monoid[String],
    Monoid[Int],
    Monoid[List[String]]
  ).imapN(tupleToDog)(dogToTuple)

  //Our Monoid allows us to create “empty” Cats, and add Cats together using the syntax from Chapter 2:
    import cats.syntax.semigroup._ // for |+|
    val garfield = Dog("Garfield", 1978, List("Lasagne"))
    val heathcliff = Dog("Heathcliff", 1988, List("Junk Food"))
    val dogComposeUsingMonoid: Dog = garfield |+| heathcliff

    println(s"Composition of Dog using Monoid:: ${dogComposeUsingMonoid.toString}")
}
