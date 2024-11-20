package org.mpdev.scala.aoc2015
package solutions.day22

import framework.{InputReader, PuzzleSolver}
import utils.{Djikstra, Graph, MinCostPath}

import scala.collection.mutable.ArrayBuffer
import scala.util.boundary
import scala.util.boundary.break

class WizardGame extends PuzzleSolver {

    private val wizData = InputReader.read(22).transform
    val boss: Player = Player("Boss", hitPoints = wizData.head, damageStrength = wizData(1))
    val me: Player = Player("Me", 500, 50)
    val graph: Graph[GameState] = Graph(customGetConnected = id => getNextStates(id))
    val djikstra: Djikstra[GameState] = Djikstra(graph)
    val startState: GameState = GameState(me.hitPoints, me.cash, 0, boss.hitPoints, List[Effect](), true)

    //
    // calculate possible next states in the game (for Djikstra algorithm)
    //

    private def getNextStates(gameState: GameState): Set[(GameState, Int)] =
        log.debug(s"*** identifying next states from: $gameState")
        var state: GameState = GameStateBuilder(gameState).build()
        if (partTwo && state.playerTurn)
            state = GameStateBuilder(state).withPlayerHitPoints(state.playerHitPoints - 1).build()
            if (state.playerHitPoints <= 0)
                log.debug(s"***     no next states found")
                return Set()

        state = processActiveSpells(state)
        if (state.bossHitPoints <= 0)
            log.debug(s"***     no next states found")
            return Set()

        if (state.playerTurn) {
            val nextStates: ArrayBuffer[(GameState, Int)] = ArrayBuffer()
            for (spell <- Spell.values) do {
                val spellAlreadyActive = state.activeEffects.map(_.spell).contains(spell)
                if (spell.cost <= state.playerCash && !spellAlreadyActive)
                    nextStates += ((GameStateBuilder(state)
                        .withPlayerCash(state.playerCash - spell.cost)
                        .withActiveSpells((List[Effect]() ::: state.activeEffects) :+ Effect(spell, spell.duration))
                        .withPlayerTurn(false).build(), spell.cost))
            }
            for (st <- nextStates)
                log.debug(s"***     $st")
            nextStates.toSet
        } else {
            val playerHitPoints = state.playerHitPoints +
                state.playerArmour - (if (state.playerArmour - boss.damageStrength < 0) boss.damageStrength else 1)
            if (playerHitPoints > 0)
                val newState = Set((GameStateBuilder(state).withPlayerHitPoints(playerHitPoints).withPlayerTurn(true).build(), 0))
                log.debug(s"***     $newState")
                newState
            else
                log.debug(s"***     no next states found")
                Set()
        }

    private def processActiveSpells(state: GameState): GameState =
        val newActiveEffects: ArrayBuffer[Effect] = ArrayBuffer()
        var playerArmour = 0
        var bossHitPoints = state.bossHitPoints
        var playerHitPoints = state.playerHitPoints
        var playerCash = state.playerCash
        for (effect <- state.activeEffects) do {
            if (effect.timer >= 0) {
                bossHitPoints -= effect.spell.damage
                playerHitPoints += effect.spell.repair
                playerArmour += effect.spell.armour
                playerCash += effect.spell.cash
            }
            val newEffect = Effect(effect.spell, effect.timer - 1)
            if (newEffect.timer > 0)
                newActiveEffects += newEffect
        }
        GameState(playerHitPoints, playerCash, playerArmour, bossHitPoints, newActiveEffects.toList, state.playerTurn)

    //
    // recursive version
    //

    private var minSpent: Int = Int.MaxValue
    private var partTwo: Boolean = false

    def playGame(gameState: GameState, spent: Int): Unit =
        var state: GameState = GameStateBuilder(gameState).build()

        if (partTwo && state.playerTurn)
            state = GameStateBuilder(state).withPlayerHitPoints(state.playerHitPoints - 1).build()
            if (state.playerHitPoints <= 0)
                return

        state = processActiveSpells(state)
        if (state.bossHitPoints <= 0) {
            if (spent < minSpent)
                minSpent = spent
            return
        }
        if (spent >= minSpent)
            return

        if (state.playerTurn) {
            for (spell <- Spell.values) do {
                val spellAlreadyActive = state.activeEffects.map(_.spell).contains(spell)
                if (spell.cost <= state.playerCash && !spellAlreadyActive)
                    playGame(GameStateBuilder(state)
                        .withPlayerCash(state.playerCash - spell.cost)
                        .withActiveSpells((List[Effect]() ::: state.activeEffects) :+ Effect(spell, spell.duration))
                        .withPlayerTurn(false).build(),
                        spent + spell.cost)
            }
        } else {
            val playerHitPoints = state.playerHitPoints +
                state.playerArmour - (if (state.playerArmour - boss.damageStrength < 0) boss.damageStrength else 1)
            if (playerHitPoints > 0)
                playGame(GameStateBuilder(state).withPlayerHitPoints(playerHitPoints).withPlayerTurn(true).build(), spent)
        }

    //
    // solve part 1 and 2
    //

    override def solvePart1: Any =
        //val minPath = djikstra.minPath(startState, id => id.bossHitPoints <= 0)
        //minPath.printPath()
        //minPath.minCost
        playGame(startState, 0)
        minSpent

    override def solvePart2: Any =
        partTwo = true
        minSpent = Int.MaxValue
        solvePart1

    extension (l: List[String])
        // Hit Points: 71
        // Damage: 10
        private def transform: List[Int] = l.map[Int](line => { val a = line.split(": "); a(1).toInt } )
}
