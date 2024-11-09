package org.mpdev.scala.aoc2015
package day18

import framework.InputReader
import solutions.day18.LightsAnimation

import solutions.day18.LightState.ON
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay18 extends AnyFlatSpec {

    private val input = InputReader.read(18, "src/test/resources/input")
    private val solver = LightsAnimation(input)

    it should "read input and set the grid" in {
        solver.grid.printIt()
        (
            solver.grid.getDimensions, solver.grid.countOf(ON), solver.grid.getDataPoints.size
        ) shouldBe (
            (6, 6), 15, 15
        )
    }

    it should "solve part1 correctly" in {
        solver.solvePart1(input) shouldBe 101
    }

    it should "solve part2 correctly" in {
        solver.solvePart2(input) shouldBe 48
    }
}
