package org.mpdev.scala.aoc2015
package day16

import framework.AocMain
import solutions.day16.IdentificationOfItems
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay16 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = IdentificationOfItems()

    it should "read entries correctly" in {
        solver.entries.foreach( println )
        solver.entries.size shouldBe 500
    }

    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 40
    }

    it should "solve part2 correctly" in {
        solver.solvePart2 shouldBe 241
    }
}
