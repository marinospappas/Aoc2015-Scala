package org.mpdev.scala.aoc2015
package day21

import framework.AocMain
import solutions.day21.{FightGame, Player}

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay21 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = FightGame()

    it should "read input and setup game" in {
        val me = Player("Me", 8, 5, 5)
        println(me)
        println(solver.boss)
        (solver.boss.hitPoints, solver.boss.damageStrength, solver.boss.armourStrength) shouldBe (12, 7, 2)
    }

    it should "play the game correctly" in {
        val me = Player("Me", 8, 5, 5)
        println(me)
        println(solver.boss)
        var result = 0
        for (i <- 1 to 4)
            result = solver.playRound(me, solver.boss)
            println(s"Round >>> $i")
            println(me)
            println(solver.boss)
            println(s"result $result")
        (result, me.hitPoints, solver.boss.hitPoints) shouldBe (1, 2, 0)
    }

    it should "calculate ring combinations" in {
        val ringCombis = solver.ringCombinations()
        println(ringCombis)
    }

    it should "calculate setup combinations" in {
        val playerSetupCombis = solver.playersCombinations()
        playerSetupCombis.foreach( player => println(s"$player, cost = ${player.cost}"))
        playerSetupCombis.foreach( player => solver.playGame(player, solver.boss) )
    }


    it should "solve part1 correctly" in {
        val me = Player("Me", 8, 5, 5)
        val winner = solver.playGame(me, solver.boss)
        println(s"winner: $winner")
        println(me)
        println(solver.boss)
        winner shouldBe 1
    }

    it should "solve part2 correctly" in {

    }
}
