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
    private val djikstra: Djikstra[GameState] = Djikstra(graph)

    //
    // play one round of the wizard game
    //

    def applyEffects(player1: Player, player2: Player, effects: ArrayBuffer[Effect]): Unit =
        val expiredEffects: ArrayBuffer[Effect] = ArrayBuffer()
        for (effect <- effects) do
            effect.timer -= 1
            effect.spell.effect(player1, player2, effect.timer)
            log.debug(s"applied $effect")
            if (effect.timer <= 0)
                expiredEffects += effect
                log.debug(s"ended $effect")
        effects --= expiredEffects
        
    def playerRound(player: Player, spell: Spell, boss: Player, effects: ArrayBuffer[Effect]): Int =
        log.debug("** Player turn")
        log.debug(s"player $player")
        log.debug(s"boss $boss")
        applyEffects(player, boss, effects)
        if (boss.hitPoints <= 0)
            return 1
        if (spell.cost > player.cash || effects.count( _.spell == spell ) > 0)
            return -1
        player.cash -= spell.cost
        log.info(s"player casts $spell")
        if (spell.applyImmediately)
            spell.effect(player, boss, spell.duration)
            log.info(s"immediate effect $spell")
        if (spell.duration > 1)    
            effects += Effect(spell, spell.duration)
        0
            
    def bossRound(player: Player, boss: Player, effects: ArrayBuffer[Effect]): Int =
        log.debug("** Boss turn")
        log.debug(s"player $player")
        log.debug(s"boss $boss")
        applyEffects(player, boss, effects)
        if (boss.hitPoints <= 0)
            return 1
        log.info(s"boss does ${boss.damageStrength - player.armourStrength} damage")    
        player.defend(boss)    
        if (player.hitPoints <= 0)
            return -1
        0

    def playRound(player: Player, spell: Spell, boss: Player, effects: ArrayBuffer[Effect]): Int =
        val result = playerRound(player, spell, boss, effects)
        if (result != 0)
            result
        else
            bossRound(player, boss, effects)
        
    //
    // calculate possible next states in the game
    //

    private def getNextStates(state: GameState): Set[(GameState, Int)] =
        val nextStates: ArrayBuffer[(GameState, Int)] = ArrayBuffer()
        applyEffects(state.player1, state.player2, state.effects)
        for (spell <- Spell.values) do
            val p1 = Player.newPlayer(state.player1)
            val p2 = Player.newPlayer(state.player2)
            val effects = state.effects.map( Effect.newEffect ).to(ArrayBuffer)
            val result = playRound(p1, spell, p2, effects)
            if (result >= 0)
                nextStates += ((GameState.newState(GameState(p1, spell, p2, effects)), spell.cost))
        log.debug(s"**** next states from $state: ")
        for (next <- nextStates) do
            log.debug(s"****      $next")
        nextStates.toSet

    //
    // solve part 1 and 2
    //

    def findMinCostForWin(p1: Player, p2: Player): MinCostPath[GameState] =
        val start: GameState = GameState(p1, null, p2, ArrayBuffer[Effect]())
        djikstra.minPath(start, id => id.player2.hitPoints <= 0)

    override def solvePart1: Any =
        val minPath = findMinCostForWin(me, boss)
        log.debug("minimum cost path: ")
        minPath.printPath()
        minPath.minCost

    override def solvePart2: Any =
        var result = -1
        result

    extension (l: List[String])
        // Hit Points: 71
        // Damage: 10
        private def transform: List[Int] = l.map[Int](line => { val a = line.split(": "); a(1).toInt } )
}
