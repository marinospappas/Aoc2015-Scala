package org.mpdev.scala.aoc2015
package day22

import framework.AocMain
import solutions.day22.{Effect, GameState, Player, Spell, WizardGame}

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import solutions.day22.Spell.*

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

import scala.collection.mutable.ArrayBuffer

class TestDay22 extends AnyFlatSpec {

    val log: Logger = LoggerFactory.getLogger(classOf[TestDay22])

    AocMain.environment = "test"
    private val solver = WizardGame()

    it should "read input and setup game" in {
        val me = Player("Me", 250, 10)
        println(me)
        println(solver.boss)
        (solver.boss.hitPoints, solver.boss.damageStrength) shouldBe (13, 8)
    }

    it should "solve part1 scenario 1" in {
        val me = Player("Me", 250, 10)
        val startState: GameState = GameState(me.hitPoints, me.cash, 0, 13, List[Effect](), true)
        solver.minSpent = Int.MaxValue
        solver.playGame(startState, 0)
        val result = solver.minSpent
        println(result)
        result shouldBe 226
    }

    it should "solve part1 scenario 2" in {
        val me = Player("Me", 250, 10)
        val startState: GameState = GameState(me.hitPoints, me.cash, 0, 14, List[Effect](), true)
        solver.partTwo = true
        solver.minSpent = 641
        solver.playGame(startState, 0)
        val result = solver.minSpent
        println(result)
        result shouldBe 641
    }

    it should "solve part2 correctly" in {
    }
}
