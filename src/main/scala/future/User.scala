package org.mpdev.scala.aoc2015
package future

import org.slf4j.{Logger, LoggerFactory}

import java.util.concurrent.LinkedTransferQueue
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.*
import scala.concurrent.duration.*

class TestFuture {

    private val log: Logger = LoggerFactory.getLogger("Program")

    def run1(queue: LinkedTransferQueue[Long]): Future[Int] = Future {
        log.info(s">>> Started Future 1")
        val l = queue.poll(3, SECONDS)
        log.info(s"Future 1 received $l")
        Thread.sleep(3000)
        log.info("Completed Future 1")
        0
    }

    def run2(queue: LinkedTransferQueue[Long]): Future[Int] = Future {
        log.info(s">>> Started Future 2")
        Thread.sleep(2000)
        val l = 12345
        queue.add(l)
        log.info(s"Future 2 sent $l")
        Thread.sleep(4000)
        log.info("Completed Future 2")
        0
    }

}

object Main {

    private val log: Logger = LoggerFactory.getLogger("Program")

    def main(args: Array[String]): Unit = {
        val f = TestFuture()
        val q = LinkedTransferQueue[Long]()
        f.run1(q)
        f.run2(q)
        log.info("program waiting")
        Thread.sleep(8000)
        log.info("Program completed")
    }
}