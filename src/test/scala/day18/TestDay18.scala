package org.mpdev.scala.aoc2015
package day18

import framework.InputReader
import solutions.day18.{LightState, LightsAnimation}
import solutions.day18.LightState.ON

import utils.GridBuilder
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
            (6, 6), 15, 36
        )
    }

    it should "animate the lights" in {
        val expectedOutput = List(
                "......",
                "......",
                "..##..",
                "..##..",
                "......",
                "......"
        )
        val expectedGid = GridBuilder[LightState]().withMapper(LightState.mapper).fromVisualGrid(expectedOutput).build()
        println("Initial")
        var currentGrid = solver.grid
        currentGrid.printIt()
        for (count <- 1 to 4) do
            println(count)
            currentGrid = solver.animate(currentGrid)
            currentGrid.printIt()
        currentGrid.getDataPoints shouldBe expectedGid.getDataPoints
    }

    it should "solve part1 correctly" in {
        solver.repeatAnimation = 4
        solver.debug = true
        solver.solvePart1(input) shouldBe 4
    }

    it should "solve part2 correctly" in {
        solver.repeatAnimation = 5
        solver.debug = true
        solver.solvePart2(input) shouldBe 17
    }
}
