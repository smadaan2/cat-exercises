package com.cat.exercises
import cats._
import cats.implicits._
import cats.data._

object CatStateMonadApp extends App {

  val a = State[Int, String] { state =>
    (state, s"The state is $state")
  }

  //In other words, an instance of State is a func on that does two things:
  // transforms an inputstate to an output state;
  // computes a result.

  //We can “run” our monad by supplying an initial state

  val (state, result) = a.run(10).value

  // Get the state, ignore the result:
  val state1 = a.runS(10).value

  // Get the result, ignore the state:

  val result1 = a.runA(10).value

  //Composing and Transforming State

  val step1 = State[Int, String] { num =>
    val ans = num + 1
    (ans, s"Result of step1: $ans")
  }

  val step2 = State[Int, String] { num =>
    val ans = num * 2
    (ans, s"Result of step2: $ans")
  }

  val both = for {
    a <- step1
    b <- step2
  } yield (a, b)


  val (state2, result2) = both.run(20).value

  //get extracts the state as the result;
  val getDemo = State.get[Int]
  getDemo.run(10).value

  //set updates the state and returns unit as the result;
  val setDemo = State.set[Int](30)
  setDemo.run(10).value

  //pure ignores the state and returns a supplied result;
  val pureDemo = State.pure[Int, String]("Result")
  pureDemo.run(10).value

  //inspect extracts the state via a transforma on func on;
  val inspectDemo = State.inspect[Int, String](_ + "!")
  inspectDemo.run(10).value

  //modify updates the state using an update func on.
  val modifyDemo = State.modify[Int](_ + 1)









}
