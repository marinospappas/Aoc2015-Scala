package org.mpdev.scala.aoc2015
package solutions.day16

import framework.{AoCException, InputReader, PuzzleSolver}

class IdentificationOfItems extends PuzzleSolver {

    val entries: List[Entry] = InputReader.read(16).map( s => sue"$s" )

    def solvePart1: Any = entries.filter( _.matchPart1 ).head.id

    def solvePart2: Any = entries.filter( _.matchPart2 ).head.id

    extension (sc: StringContext)
        // Sue 1: goldfish: 9, cars: 0, samoyeds: 9
        private def sue(args: String*): Entry = {
            sc.s(args *) match
                case s"Sue $id: $a1: $n1, $a2: $n2, $a3: $n3" => Entry(id.toInt, List(
                    (Attribute.valueOf(a1.toUpperCase), n1.toInt),
                    (Attribute.valueOf(a2.toUpperCase), n2.toInt),
                    (Attribute.valueOf(a3.toUpperCase), n3.toInt))
                )
                case x => throw AoCException(s"bad input $x")
        }
}

case class Entry(id: Int, attributes: List[(Attribute, Int)]) {
    def matchPart1: Boolean = attributes.forall( atr => atr._1.count == atr._2 )
    def matchPart2: Boolean = attributes.forall( atr => atr._1.check(atr._2))
}

enum Attribute(val count: Int, val check: Int => Boolean) {
    case CHILDREN    extends Attribute(3, n => n == 3)
    case CATS        extends Attribute(7, n => n > 7)
    case SAMOYEDS    extends Attribute(2, n => n == 2)
    case POMERANIANS extends Attribute(3, n => n < 3)
    case AKITAS      extends Attribute(0, n => n == 0)
    case VIZSLAS     extends Attribute(0, n => n == 0)
    case GOLDFISH    extends Attribute(5, n => n < 5)
    case TREES       extends Attribute(3, n => n > 3)
    case CARS        extends Attribute(2, n => n == 2)
    case PERFUMES    extends Attribute(1, n => n == 1)
}
