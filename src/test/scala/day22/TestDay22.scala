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

    it should "play the game correctly 1st scenario" in {
        var me = Player("Me", 250, 10)
        var boss = solver.boss
        val spells = List(POISON, MAGIC_MISSILE)
        var effects: List[Effect] = List()
        log.info(s"Start: $me - $boss")
        var winner = 0
        for (i <- 0 to 1)
            log.info(s"Round >>> $i")
    //        val result = solver.playRound(me, spells(i), boss, effects)
      //7      me = result._2
         //   boss = result._3
         //   effects = result._4
         //   winner = result._1
        log.info(s"End: $me - $boss")
        (winner, me.hitPoints, me.armourStrength, me.cash, boss.hitPoints) shouldBe (1, 2, 0, 24, 0)
    }

    it should "play the game correctly - 2nd scenario" in {
        var me = Player("Me", 250, 10)
        var boss = Player("Boss", 0, 14, 8)
        var effects: List[Effect] = List()
        val spells = List(RECHARGE, SHIELD, DRAIN, POISON, MAGIC_MISSILE)
        log.info(s"Start: $me - $boss")
        var winner = 0
        for (i <- 0 to 4)
            log.info(s"\nRound >>> $i")
    //        val result = solver.playRound(me, spells(i), boss, effects)
      //      me = result._2
        //    boss = result._3
          //  effects = result._4
            //winner = result._1
        log.info(s"End: $me - $boss")
        (winner, me.hitPoints, me.armourStrength, me.cash, boss.hitPoints) shouldBe(1, 1, 0, 114, -1)
    }

    it should "solve part1 scenario 1" in {
        val me = Player("Me", 250, 10)
        val startState: GameState = GameState(me.hitPoints, me.cash, 0, solver.boss.hitPoints, List[Effect](), true)
        val result = solver.djikstra.minPath(startState, id => id.bossHitPoints <= 0)
        println(result.minCost)
        result.printPath()
    }

    it should "solve part1 scenario 2" in {
        val me = Player("Me", 250, 10)
        val boss = Player("Boss", 0, 14, 8)
        //val minPath = solver.findMinCostForWin(me, boss)
        //log.debug("minimum cost path: ")
        //minPath.printPath()
        //val minPath = solver.playGame(me, boss, List[Effect](), true, 0)
        //log.debug(s"minimum cost : ${solver.minSpent}")
        //(minPath.path.size, minPath.minCost) shouldBe(6, 641)
    }

    it should "solve part2 correctly" in {
    }
}
