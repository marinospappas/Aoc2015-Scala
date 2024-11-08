package org.mpdev.scala.aoc2015
package framework

private def usage(): Unit =
    System.err.println("usage: AoCMain #day")
    System.exit(1)

@main
def aoc2024(args: String*): Unit =
    if (args.isEmpty)
        usage()
    val day = args(0).toInt
    val solver = solvers(day)
    val input = readInput(day)
    val solution1 = solver.part1(input)
    val solution2 = solver.part2(input)
    println("\nAoC 2015 Solution day " + day + " part 1: " + solution1(0) + "   in " + solution1(1) + " msecs")
    println("AoC 2015 Solution day " + day + " part 2: " + solution2(0) + "   in " + solution2(1) + " msecs")
