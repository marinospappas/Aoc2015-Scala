package org.mpdev.scala.aoc2015
package day08

import framework.AocMain

import solutions.day08.StringLengthCalculator
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay08 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = StringLengthCalculator()

    it should "read input correctly" in {
        solver.inputData.foreach( println(_) )
    }

    it should "solve part1 correctly" in {
        println(solver.compiledStrings)
        solver.solvePart1 shouldBe 12
    }

    it should "solve part2 correctly" in {
        println(solver.encodedStrings)
        solver.solvePart2 shouldBe 19
    }
}
