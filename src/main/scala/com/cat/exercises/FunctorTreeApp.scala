package com.cat.exercises
import cats._
import cats.implicits._

object FunctorTreeApp extends App{

  sealed trait Tree[+A] {
    def map[B](f: A=> B ): Tree[B] = this match {
      case Branch(t1,t2)  => Branch(t1.map(f),t2.map(f))
      case Leaf(a) => Leaf(f(a))
    }
  }
  final case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]
  final case class Leaf[A](value: A) extends Tree[A]

  object Tree {
    def branch[A](left: Tree[A], right: Tree[A]): Tree[A] =
      Branch(left, right)
    def leaf[A](value: A): Tree[A] =
      Leaf(value)
  }

  Branch(Leaf(10), Leaf(20)).map(_ * 2)  //

  val a: List[Tree[String]] = ???

  a.map(tree => tree.map(_.toInt))

  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {def map[A, B](tree: Tree[A])(func: A => B): Tree[B] =tree.map(func)}














}
