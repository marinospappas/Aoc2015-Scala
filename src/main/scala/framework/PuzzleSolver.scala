package org.mpdev.scala.aoc2015
package framework

trait PuzzleSolver(val input: List[String]) {

    def part1: (Any, Long) = {
        val start = System.currentTimeMillis()
        (solvePart1, System.currentTimeMillis() - start)
    }

    def part2: (Any, Long) = {
        val start = System.currentTimeMillis()
        (solvePart2, System.currentTimeMillis() - start)
    }

    def solvePart1: Any

    def solvePart2: Any

}
