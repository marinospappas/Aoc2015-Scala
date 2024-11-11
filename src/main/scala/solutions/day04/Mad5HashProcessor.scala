package org.mpdev.scala.aoc2015
package solutions.day04

import framework.{InputReader, PuzzleSolver}
import utils.Md5

import scala.util.boundary
import scala.util.boundary.break

class Mad5HashProcessor extends PuzzleSolver {

    private val byte0 = 0.toByte
    private val key = InputReader.read(4).head
    
    override def solvePart1: Any =
        boundary:
            
            Range(0, Int.MaxValue).foreach { i =>
                val md5Chcksum = Md5.checksum(s"$key$i")
                if (md5Chcksum(0) == byte0 && md5Chcksum(1) == byte0 && (md5Chcksum(2) & 0xf0) == byte0)
                    break(i)
            }

    override def solvePart2: Any =
        boundary:
            Range(0, Int.MaxValue).foreach { i =>
                val md5Chcksum = Md5.checksum(s"$key$i")
                if (md5Chcksum(0) == byte0 && md5Chcksum(1) == byte0 && md5Chcksum(2) == byte0)
                    break(i)
            }
}
