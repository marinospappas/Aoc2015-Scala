package org.mpdev.scala.aoc2015
package solutions.day01

import framework.{InputReader, PuzzleSolver}

class ElevatorButtons(var testData: List[String] = List()) extends PuzzleSolver {

    private val inputMapper = Map('(' -> 1, ')' -> -1)
    private val inputData: List[Int] = (if (testData.nonEmpty) testData else InputReader.read(1)).transform

    override def solvePart1: Any =
        inputData.sum

    override def solvePart2: Any =
        inputData.scanLeft(0)(_ + _).indexOf(-1)

    extension (l: List[String])
        private def transform: List[Int] = l.head.toList.map[Int](inputMapper(_))
}
