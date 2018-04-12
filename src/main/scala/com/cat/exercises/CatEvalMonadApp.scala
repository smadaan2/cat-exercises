package com.cat.exercises
import cats._
import cats.implicits._

object CatEvalMonadApp {
  //Eval.now captures a value right now. Its seman cs are similar to a val—eager and memoized
  //Eval.later captures a lazy, memoized computation, similar to a lazy val.
  //Eval.always captures a lazy computation, similar to a def

  val now = Eval.now(math.random + 1000)
  val later = Eval.later(math.random + 2000)
  val always = Eval.always(math.random + 3000)

  //We can extract the result of an Eval using its value method

  println(s"now.value::: ${now.value}")
  println(s"later.value::: ${later.value}")
  println(s"always.value::: ${always.value}")

  val x = Eval.now {
    println("Computing X")
    math.random
  }

  x.value // first access
  x.value // second access

  val y = Eval.always {
    println("Computing Y")
    math.random
  }

  y.value // first access
  y.value // second access

  //Eval has a memoize method that allows us to memoize a chain of computa-  ons. The result of the chain up to the call to memoize is cached, whereas calcula ons a er the call retain their original seman cs:
  val saying = Eval.
    always { println("Step 1"); "The cat" }.
    map { str => println("Step 2"); s"$str sat on" }.
    memoize.
    map { str => println("Step 3"); s"$str the mat" }


  saying.value // first access
  saying.value // second access

  // Eval.defer: map and flatMap methods are tram-polined. This means we can nest calls to map and flatMap arbitrarily without consuming stack frames. We call this property “stack safety”.
//  def factorial(n: BigInt): BigInt = if(n == 1) n else n * factorial(n - 1)
//
//  factorial(50000)  // java.lang.StackOverflowError

  def factorial1(n: BigInt): Eval[BigInt] =
    if(n == 1) {
      Eval.now(n)
    } else {
      factorial1(n - 1).map(_ * n)
    }

  factorial1(50000).value  // java.lang.StackOverflowError

  def factorial(n: BigInt): Eval[BigInt] =
    if(n == 1) {
      Eval.now(n)
    } else {
      Eval.defer(factorial(n - 1).map(_ * n))
    }

  factorial(50000).value


}
