package org.mpdev.scala.aoc2015
package day12

import framework.InputReader
import solutions.day12.JsonProcessor

import org.scalatest.prop.TableDrivenPropertyChecks._
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers._
import org.scalatest.prop.Tables.Table

class TestDay12 extends AnyFlatSpec {

    private val solver = JsonProcessor()
    private val input = InputReader.read(12, "src/test/resources/input", "json")

    it should "read json string correctly" in {
        solver.inputValue(input).isInstanceOf[ujson.Arr] shouldBe true
    }

    private val redParams = Table(
        ("json", "expected"),
        (ujson.read("""{"c":"violet", "a":"red", "b":161}"""), true),
        (ujson.read("""{"c":"violet", "a":"white", "b":161}"""), false),
        (ujson.read("""{"c":["violet","red"], "b":161}"""), false),
        (ujson.read("""[87, "red", -2, "yellow"]"""), false)
    )

    it should "recognise 'red' objects" in {
        forAll (redParams) { (json: ujson.Value, expected: Boolean) =>
            solver.isRedObject(json) shouldBe expected
        }
    }

    it should "solve part1 correctly" in {
        solver.solvePart1(input) shouldBe 3531
    }

    it should "solve part2 correctly" in {
        solver.solvePart2(input) shouldBe 3074
    }
}
