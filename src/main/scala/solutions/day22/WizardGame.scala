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

    private def startEffect(spell: Spell, player1: Player, player2: Player, effects: ArrayBuffer[Effect]): Unit =
        spell.atStart(player1, player2)
        log.debug(s"started $spell")

    private def applyEffects(player1: Player, player2: Player, effects: ArrayBuffer[Effect]): Unit =
        val expiredEffects: ArrayBuffer[Effect] = ArrayBuffer()
        for (effect <- effects) do
            if (effect.timer <= 0)
                effect.spell.atEnd(player1, player2)
                expiredEffects += effect
                log.debug(s"ended $effect")
            else
                effect.spell.whileActive(player1, player2)
                effect.timer -= 1
                log.debug(s"applied $effect")
        effects --= expiredEffects

    def playRound(player1: Player, spell: Spell, player2: Player, effects: ArrayBuffer[Effect]): Int =
        // player 1 casts spell
        log.debug(s"going into round $player1, $player2")
        log.debug("**Player Turn**")
        startEffect(spell, player1, player2, effects)
        applyEffects(player1, player2, effects)
        player1.cash -= spell.cost
        if (spell.duration > 0)
            effects += Effect(spell.duration, spell)
            log.debug(s"scheduled for next rounds ${Effect(spell.duration, spell)}")
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

    //
    // calculate possible next states in the game
    //

    private def isValidEffect(spell: Spell, effects: ArrayBuffer[Effect]): Boolean =
        if (effects.count( _.spell == spell) > 0)
            val activeEffect = effects.filter( _.spell == spell ).head
            activeEffect.timer == 1
        else
            true

    private def getNextSpells(state: GameState): Set[Spell] =
        Spell.values
            .filter( _.cost <= state.player1.cash )
            .filter( s => isValidEffect(s, state.effects) )
            .toSet

    private def getNextStates(state: GameState): Set[(GameState, Int)] =
        val nextStates: ArrayBuffer[(GameState, Int)] = ArrayBuffer()
        val spells: Set[Spell] = getNextSpells(state)
        for (spell <- getNextSpells(state)) do
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
