package org.mpdev.scala.aoc2015
package day07

import framework.{AocMain, InputReader}
import solutions.day02.PresentWrapping

import solutions.day07.DigitalCircuit
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay07 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = DigitalCircuit()

    it should "read input and set circuit up" in {
        for (node <- solver.gatesMap.values)
            println(node)
        solver.gatesMap.size shouldBe 9
    }
    
    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 507
    }
}
