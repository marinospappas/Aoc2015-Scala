package org.mpdev.scala.aoc2015
package solutions.day08

import framework.{InputReader, PuzzleSolver}

class StringLengthCalculator(var testData: List[String] = List()) extends PuzzleSolver {

    val inputData: List[String] = InputReader.read(8)
    val compiledStrings: List[String] = inputData
        .map( s => s.substring(1, s.length - 1) )
        .map( _.replace("\\\\", "\\") )
        .map( _.replace("\\\"", "\"") )
        .map( _.replaceAll("""\\x[0-9a-f]{2}""", "_") )
    val encodedStrings: List[String] = inputData
        .map( _.replace("\\", "\\\\") )
        .map( _.replace("\"", "\\\"") )
        .map( "\"" + _ + "\"" )

    override def solvePart1: Any =
        inputData.map( _.length).sum - compiledStrings.map( _.length ).sum

    override def solvePart2: Any =
        encodedStrings.map(_.length).sum - inputData.map(_.length).sum
}
