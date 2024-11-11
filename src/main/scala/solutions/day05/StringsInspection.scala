package org.mpdev.scala.aoc2015
package solutions.day05

import framework.{InputReader, PuzzleSolver}
import utils.isVowel
import utils.containsSeqTwice
import utils.containsAny

class StringsInspection extends PuzzleSolver {

    private val inputData = InputReader.read(5)
    
    override def solvePart1: Any =
        inputData.count( s => s.count( c => c.isVowel ) >= 3 && s.containsSeqTwice(1, 0) && !s.containsAny(StringsInspection.unwantedStrings) )
    
    override def solvePart2: Any = inputData.count( s => s.containsSeqTwice(2, -1) && s.containsSeqTwice(1, 1) )
    
    object StringsInspection {
        val unwantedStrings: Set[String] = Set("ab", "cd", "pq", "xy")
    }
}
