package org.mpdev.scala.aoc2015
package solutions.day01

import framework.PuzzleSolver

class ElevatorButtons extends PuzzleSolver {

    override def solvePart1(input: List[String]): Any =
        input.transform.sum

    override def solvePart2(input: List[String]): Any =
        input.transform.scanLeft(0)(_ + _).indexOf(-1)

    private val inputMapper = Map('(' -> 1, ')' -> -1)

    extension (l: List[String])
        private def transform: List[Int] = l.head.toList.map[Int](inputMapper(_))
}
