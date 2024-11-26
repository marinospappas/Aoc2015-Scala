package org.mpdev.scala.aoc2015
package solutions.day17

import framework.{InputReader, PuzzleSolver}
import utils.combinationsWithDuplicates

import scala.collection.mutable.ArrayBuffer

class Containerisation extends PuzzleSolver {

    val inputData: List[Int] = InputReader.read(17).map( _.toInt )
    val solutions: ArrayBuffer[List[Int]] = ArrayBuffer()
    private val groupTotal = 150

    def solvePart1: Any = {
        var combis: Iterator[List[Int]] = Iterator()
        for ( n <- 2 to inputData.size )
            if ({ combis = inputData.combinationsWithDuplicates(n).filter( _.sum == groupTotal )
                combis.nonEmpty })
                solutions ++= combis
        solutions.size
    }


    def solvePart2: Any = {
        val minSizeGroup = solutions.minBy( _.size ).size
        solutions.count( _.size == minSizeGroup )
    }

}

