package org.mpdev.scala.aoc2015
package day20

import framework.AocMain
import solutions.day20.PacketsDistribution

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay20 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = PacketsDistribution()

    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 4
    }

    it should "solve part2 correctly" in {
        solver.solvePart2 shouldBe 4
    }
}
