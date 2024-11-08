package org.mpdev.scala.aoc2015
package day02

import solutions.day02.PresentWrapping

import framework.InputReader
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay02 extends AnyFlatSpec {

    private val solver = PresentWrapping()
    private val input = InputReader.read(2, "src/test/resources/input")

    it should "solve part1 correctly" in {
        solver.solvePart1(input) shouldBe 101
    }

    it should "solve part2 correctly" in {
        solver.solvePart2(input) shouldBe 48
    }
}
