package org.mpdev.scala.aoc2015
package solutions.day02

import framework.PuzzleSolver

class PresentWrapping extends PuzzleSolver{

    override def solvePart1(input: List[String]): Any =
        input.transform.map(p => p.area() + p.extra()).sum

    override def solvePart2(input: List[String]): Any =
        input.transform.map(_.ribbon()).sum

    extension (l: List[String])
        private def transform: List[Parcel] = l.map[Parcel](line => {
            val d = line.split('x').map[Int](s => s.toInt)
            Parcel(d(0), d(1), d(2))
        })
}
