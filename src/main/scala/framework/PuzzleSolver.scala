package org.mpdev.scala.aoc2015
package framework

trait PuzzleSolver {

    def part1(input: List[String]): (Any, Long) = {
        val start = System.currentTimeMillis()
        (solvePart1(input), System.currentTimeMillis() - start)
    }

    def part2(input: List[String]): (Any, Long) = {
        val start = System.currentTimeMillis()
        (solvePart2(input), System.currentTimeMillis() - start)
    }

    def solvePart1(input: List[String]): Any

    def solvePart2(input: List[String]): Any

}
