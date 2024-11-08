package org.mpdev.scala.aoc2015
package solutions.day01

import framework.PuzzleSolver

import scala.util.boundary
import scala.util.boundary.break

class ElevatorButtons extends PuzzleSolver {

    override def solvePart1(input: List[String]): Any =
        input.transform.map(b => b.floor).sum

    override def solvePart2(input: List[String]): Any =
        boundary:
            val data = input.head.toList.map[Button](c => Button.values.find(b => b.value.equals(c)).get): List[Button]
            var curFloor = 0
            for (i <- data.indices) do {
                curFloor = curFloor + data(i).floor
                if (curFloor == -1) {
                    curFloor = i + 1
                    break(i + 1)
                }
            }

    extension (l: List[String])
        private def transform: List[Button] = l.head.toList.map[Button](c => Button.values.find(b => b.value.equals(c)).get)
}

enum Button(val value: Char, val floor: Int):
    case Up extends Button('(', 1)
    case Down extends Button(')', -1)
