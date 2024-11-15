package org.mpdev.scala.aoc2015
package day22

import framework.AocMain
import solutions.day22.{Player, WizardGame}

import solutions.day22.Spell.{MAGIC_MISSILE, POISON}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers.*

class TestDay22 extends AnyFlatSpec {

    AocMain.environment = "test"
    private val solver = WizardGame()

    it should "read input and setup game" in {
        val me = Player("Me", 250, 10)
        println(me)
        println(solver.boss)
        (solver.boss.hitPoints, solver.boss.damageStrength) shouldBe (13, 8)
    }

    it should "play the game correctly" in {
        val me = Player("Me", 250, 10)
        val spells = List(POISON, MAGIC_MISSILE)
        println(me)
        println(solver.boss)
        var result = 0
        for (i <- 0 to 1)
            result = solver.playRound(me, spells(i), solver.boss)
            println(s"Round >>> $i")
            println(me)
            println(solver.boss)
            println(solver.effects)
            println(s"result $result")
        (result, me.hitPoints, solver.boss.hitPoints) shouldBe (1, 2, 0)
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
