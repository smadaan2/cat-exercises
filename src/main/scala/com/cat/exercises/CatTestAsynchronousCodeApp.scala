package com.cat.exercises

import cats._
import cats.implicits._

object CatTestAsynchronousCodeApp extends App {

  def testTotalUptime()= {
    val hosts = Map("host1" -> 10, "host2" -> 6)
    val client = new TestUptimeClient(hosts)
    val service = new UptimeService(client)
    val actual = service.getTotalUptime(hosts.keys.toList)
    val expected = hosts.values.sum
    assert(actual == expected)
  }
  println(s"Testing assertion::: ${testTotalUptime}")
}

trait UptimeClient[F[_]] {
  def getUptime(hostname: String): F[Int]
}

class TestUptimeClient(hosts: Map[String, Int]) extends UptimeClient[Id] {
  def getUptime(hostname: String): Int = hosts.getOrElse(hostname, 0)
}

class UptimeService[F[_]](client: UptimeClient[F])(implicit m: Monad[F]) {
  def getTotalUptime(hostnames: List[String]): F[Int] = hostnames.traverse(client.getUptime).map(_.sum)
}


