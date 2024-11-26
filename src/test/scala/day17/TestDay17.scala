package org.mpdev.scala.aoc2015
package day17

import framework.AocMain
import solutions.day17.Containerisation
import utils.combinationsWithDuplicates

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay17 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = Containerisation()

    it should "calculate combinations" in {
        val combis = solver.inputData.combinationsWithDuplicates(2)
        combis.foreach(println)
    }

    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 3531
    }

    it should "solve part2 correctly" in {
        solver.solvePart2 shouldBe 3074
    }
}
