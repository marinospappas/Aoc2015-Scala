package org.mpdev.scala.aoc2015
package day05

import framework.{AocMain, InputReader}
import solutions.day05.StringsInspection
import utils.containsSeqTwice

import org.scalatest.prop.TableDrivenPropertyChecks.*
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*
import org.scalatest.prop.Tables.Table

class TestDay05 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = StringsInspection()

    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 2
    }

    private val part2Params = Table(
        ("input", "expected"),
        ("qjhvhtzxzqqjkmpb", true),
        ("xxyxx", true),
        ("uurcxstgmygtbstg", false),
        ("ieodomkazucvgmuy", false)
    )

    it should "solve part2 correctly" in {
        forAll(part2Params) { (input: String, expected: Boolean) =>
            input.containsSeqTwice(2, -1) && input.containsSeqTwice(1, 1) shouldBe expected
        }
    }
}
