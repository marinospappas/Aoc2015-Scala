package org.mpdev.scala.aoc2015
package solutions.day22

import framework.{InputReader, PuzzleSolver}

import scala.collection.mutable.ArrayBuffer
import scala.util.boundary
import scala.util.boundary.break

class WizardGame extends PuzzleSolver {

    private val wizData = InputReader.read(22).transform
    val boss: Player = Player("Boss", hitPoints = wizData.head, damageStrength = wizData(1))
    val me: Player = Player("Me", 500, 50)
    val effects: ArrayBuffer[Effect] = ArrayBuffer()

    def startEffect(effect: Effect, player1: Player, player2: Player, effects: ArrayBuffer[Effect]): Unit =
        effect.atStart(player1, player2)
        log.debug(s"started $effect")

    def applyEffects(player1: Player, player2: Player, effects: ArrayBuffer[Effect]): Unit =
        val expiredEffects: ArrayBuffer[Effect] = ArrayBuffer()
        for (effect <- effects)
            if (effect.timer <= 0)
                effect.atEnd(player1, player2)
                expiredEffects += effect
                log.debug(s"ended $effect")
            else
                effect.whileActive(player1, player2)
                effect.timer -= 1
                log.debug(s"applied $effect")

        effects --= expiredEffects

    def playRound(player1: Player, spell: Spell, player2: Player, effects: ArrayBuffer[Effect]): Int =
        // player 1 casts spell
        log.debug(s"going into round $player1, $player2")
        log.debug("**Player Turn**")
        startEffect(spell.effect, player1, player2, effects)
        applyEffects(player1, player2, effects)
        player1.cash -= spell.cost
        if (spell.effect.timer > 0)
            effects += spell.effect
            log.debug(s"scheduled for next rounds ${spell.effect}")
        log.debug(s"after player1 spell $player1, $player2")
        if (player2.hitPoints <= 0)
            return 1    // player 1 wins
        // player 2 attacks (if still standing)
        log.debug("**Boss Turn**")
        applyEffects(player1, player2, effects)
        if (player2.hitPoints <= 0)
            return 1    // player 1 wins 
        player1.defend(player2)
        log.debug(s"after player2 attack $player1, $player2")
        if (player1.hitPoints <= 0) -1   // player 2 wins
        else 0   // fight is still on

    def playGame(player1: Player, player2: Player): Int =
        -1

    override def solvePart1: Any =
        var result = -1
        result

    override def solvePart2: Any =
        var result = -1
        result

    extension (l: List[String])
        // Hit Points: 71
        // Damage: 10
        private def transform: List[Int] = l.map[Int](line => { val a = line.split(": "); a(1).toInt } )
}
