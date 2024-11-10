package org.mpdev.scala.aoc2015
package day09

import framework.InputReader

import solutions.day09.ShortestAndLongestRoute
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay09 extends AnyFlatSpec {

    private val solver = ShortestAndLongestRoute()

    it should "manipulate list" in {
        val l: List[String] = List("1", "2", "3")
        println(l)
        println(l :+ "6")
        println(l)
        
        println(l.filter( _ != "2"))
    }
    
    
    it should "solve part1 correctly" in {
        solver.solvePart1 shouldBe 101
    }

    it should "solve part2 correctly" in {
        solver.solvePart2 shouldBe 48
    }
}
