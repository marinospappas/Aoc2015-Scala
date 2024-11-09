package org.mpdev.scala.aoc2015
package solutions.day02

import framework.{InputReader, PuzzleSolver}

class PresentWrapping extends PuzzleSolver(InputReader.read(2)) {

    private val inputData: List[Parcel] = input.transform

    override def solvePart1: Any =
        inputData.map(p => p.area() + p.extra()).sum

    override def solvePart2: Any =
        inputData.map(_.ribbon()).sum

    extension (l: List[String])
        private def transform: List[Parcel] = l.map[Parcel](line => {
            val d = line.split('x').map[Int](s => s.toInt)
            Parcel(d(0), d(1), d(2))
        })
}
