package org.mpdev.scala.aoc2015
package day09

import framework.InputReader
import framework.AocMain

import solutions.day09.ShortestAndLongestRoute
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay09 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = ShortestAndLongestRoute()

    it should "read input and build graph" in {
        solver.graph.printIt()
        (
            solver.graph.getNodes.toSet, solver.graph.nodes.size, solver.graph.nodes.values.map( _.size )
        ) shouldBe (
            Set("Start", "London", "Belfast", "Dublin"), 4, List(3, 3, 3, 3)
        )
    }
    
    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 605
    }

    it should "solve part2 correctly" in {
        solver.solvePart2 shouldBe 982
    }
}
