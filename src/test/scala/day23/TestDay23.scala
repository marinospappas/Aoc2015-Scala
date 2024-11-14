package org.mpdev.scala.aoc2015
package day23

import framework.AocMain
import solutions.day23.Computer

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay23 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = Computer()

    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 2
    }

    it should "solve part2 correctly" in {
        solver.solvePart2 shouldBe 7
    }
}
