package org.mpdev.scala.aoc2015
package solutions.day01

import framework.PuzzleSolver

class ElevatorButtons extends PuzzleSolver {

    override def solvePart1(input: List[String]): Any =
        input.transform.map(b => b.floor).sum

    override def solvePart2(input: List[String]): Any =
        input.transform.map(b => b.floor).scanLeft(0)(_ + _).indexOf(-1)

    extension (l: List[String])
        private def transform: List[Button] = l.head.toList.map[Button](c => Button.values.find(b => b.value.equals(c)).get)
}

enum Button(val value: Char, val floor: Int):
    case Up extends Button('(', 1)
    case Down extends Button(')', -1)
