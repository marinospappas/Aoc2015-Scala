package org.mpdev.scala.aoc2015
package day22

import framework.AocMain
import solutions.day22.{Effect, Player, WizardGame}

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import solutions.day22.Spell.*
import solutions.day22.Spell

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

    it should "play the game correctly 1st scenario" in {
        val me = Player("Me", 250, 10)
        val spells = List(POISON, MAGIC_MISSILE)
        val effects: ArrayBuffer[Effect] = ArrayBuffer()
        log.info(s"Start: $me - ${solver.boss}")
        var result = 0
        for (i <- 0 to 1)
            log.info(s"Round >>> $i")
            result = solver.playRound(me, spells(i), solver.boss, effects)
        (result, me.hitPoints, me.armourStrength, solver.boss.hitPoints) shouldBe (1, 2, 0, 0)
    }

    it should "play the game correctly - 2nd scenario" in {
        val me = Player("Me", 250, 10)
        val boss = Player("Boss", 0, 14, 8)
        val effects: ArrayBuffer[Effect] = ArrayBuffer()
        val spells = List(RECHARGE, SHIELD, DRAIN, POISON, MAGIC_MISSILE)
        println(me)
        println(boss)
        var result = 0
        for (i <- 0 to 4)
            log.info(s"Round >>> $i")
            result = solver.playRound(me, spells(i), boss, effects)
        (result, me.hitPoints, me.armourStrength, boss.hitPoints) shouldBe(1, 1, 0, -1)
    }

    it should "solve part1 scenario 1" in {
        val me = Player("Me", 250, 10)
        val minPath = solver.findMinCostForWin(me, solver.boss)
        log.debug("minimum cost path: ")
        minPath.printPath()
        (minPath.path.size, minPath.minCost) shouldBe (3, 226)
    }

    it should "solve part1 scenario 2" in {
        val me = Player("Me", 250, 10)
        val boss = Player("Boss", 0, 14, 8)
        val minPath = solver.findMinCostForWin(me, boss)
        log.debug("minimum cost path: ")
        minPath.printPath()
        (minPath.path.size, minPath.minCost) shouldBe(6, 641)
    }

    it should "solve part2 correctly" in {
    }
}
