package org.mpdev.scala.aoc2015
package solutions.day04

import framework.PuzzleSolver
import utils.Md5

import scala.util.boundary
import scala.util.boundary.break

class Mad5HashProcessor extends PuzzleSolver {

    private val byte0 = 0.toByte

    override def solvePart1(input: List[String]): Any =
        boundary:
            val key = input.head
            Range(0, Int.MaxValue).foreach { i =>
                val md5Chcksum = Md5.checksum(s"$key$i")
                if (md5Chcksum(0) == byte0 && md5Chcksum(1) == byte0 && (md5Chcksum(2) & 0xf0) == byte0)
                    break(i)
            }

    override def solvePart2(input: List[String]): Any =
        boundary:
            val key = input.head
            Range(0, Int.MaxValue).foreach { i =>
                val md5Chcksum = Md5.checksum(s"$key$i")
                if (md5Chcksum(0) == byte0 && md5Chcksum(1) == byte0 && md5Chcksum(2) == byte0)
                    break(i)
            }
}
