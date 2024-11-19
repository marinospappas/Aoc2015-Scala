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

    def applySpell(spell: Spell, p: Player, b: Player, timer: Int): (Player, Player) =
        val player1 = Player(p.name, p.cash + spell.cash, p.hitPoints + spell.repair, p.damageStrength, p.armourStrength + spell.armour)
        val boss = Player(b.name, 0, b.hitPoints - spell.damage, b.damageStrength)
        (player1, boss)

    def applyEffects(player1: Player, player2: Player, effects: List[Effect]): (Player, Player, List[Effect]) =
        val activeEffects: ArrayBuffer[Effect] = ArrayBuffer()
        var (p1, p2) = (Player(player1.name, player1.cash, player1.hitPoints, player1.damageStrength), player2)
        for (effect <- effects) do
            val res = applySpell(effect.spell, p1, p2, effect.timer)
            p1 = res._1
            p2 = res._2
            log.debug(s"applied ${effect.spell}")
            if (effect.timer <= 1)
                log.debug(s"ended ${effect.spell}")
            else
                activeEffects += Effect(effect.spell, effect.timer - 1)
        log.debug(s"active effects $activeEffects")
        (p1, p2, activeEffects.toList)

    def playerRound(player: Player, spell: Spell, boss: Player, effects: List[Effect]): (Int, Player, Player, List[Effect]) =
        log.debug("** Player turn")
        log.debug(s"player $player")
        log.debug(s"boss $boss")
        var (p1, b, newEffects) = applyEffects(player, boss, effects)
        if (b.gameOver)
            return (1, p1, b, newEffects)
        if (spell.cost > player.cash || effects.count( _.spell == spell ) > 0)
            return (-1, p1, b, newEffects)
        p1 = Player(p1.name, p1.cash - spell.cost, p1.hitPoints, p1.damageStrength, p1.armourStrength)
        log.debug(s"player casts $spell")
        if (spell.immediateEffect)
            val res = applySpell(spell, p1, b, 1)
            p1 = res._1
            b = res._2
            log.debug(s"immediate effect $spell")
        if (spell.duration > 1)
            newEffects = (List() ::: newEffects) :+ Effect(spell, spell.duration - /*(if (spell.immediateEffect) 1 else*/ 0) //)
        (0, p1, b, newEffects)

    def bossRound(player: Player, boss: Player, effects: List[Effect]): (Int, Player, Player, List[Effect]) =
        log.debug("** Boss turn")
        log.debug(s"player $player")
        log.debug(s"boss $boss")
        var (p1, p2, newEffects) = applyEffects(player, boss, effects)
        if (p2.gameOver)
            return (1, p1, p2, newEffects)
        log.debug(s"boss does ${p2.damageStrength - p1.armourStrength} damage")
        p1 = p1.defend(p2)
        if (p1.gameOver)
            return (-1, p1, p2, newEffects)
        (0, p1, p2, newEffects)


    def playRound(player: Player, spell: Spell, boss: Player, effects: List[Effect]): (Int, Player, Player, List[Effect]) =
        val result = playerRound(player, spell, boss, effects)
        if (result._1 != 0)
            result
        else
            bossRound(result._2, result._3, result._4)

    //
    // calculate possible next states in the game
    //

    private def getNextStates(state: GameState): Set[(GameState, Int)] =
        val nextStates: ArrayBuffer[(GameState, Int)] = ArrayBuffer()
        applyEffects(state.player1, state.player2, state.effects)
        for (spell <- Spell.values) do
            val result = playRound(state.player1, spell, state.player2, state.effects)
            if (result._1 >= 0)
                nextStates += ((GameState.newState(GameState(result._2, spell, result._3, result._4)), spell.cost))
        log.debug(s"**** next states from $state: ")
        for (next <- nextStates) do
            log.debug(s"****      $next")
        nextStates.toSet

    //
    // recursive version
    //

    var minSpent: Int = Int.MaxValue
    var partTwo: Boolean = false

    def playGame(bossHitPoints: Int, myHitPoints: Int, myCash: Int, activeEffects: List[Effect], playerTurn: Boolean, spent: Int): Any =
        val bossDamageStrenth = boss.damageStrength
        var myArmour = 0
        var bossHp = bossHitPoints
        var myHp = myHitPoints
        var myCsh = myCash

        if (partTwo && playerTurn)
            myHp -= 1
            if (myHitPoints <= 0)
                return false

        val newActiveEffects: ArrayBuffer[Effect] = ArrayBuffer()
        for (effect <- activeEffects) do
            if (effect.timer >= 0)
                bossHp -= effect.spell.damage
                myHp += effect.spell.repair
                myArmour += effect.spell.armour
                myCsh += effect.spell.cash

            val newEffect = Effect(effect.spell, effect.timer - 1)
            if (newEffect.timer > 0)
                newActiveEffects += newEffect

        if (bossHp <= 0)
            if (spent < minSpent)
                minSpent = spent
                return true

        if (spent >= minSpent)
            return false

        if (playerTurn) {
            for (spell <- Spell.values) do

                val spellAlreadyActive = newActiveEffects.map( _.spell ).contains(spell)
                if (spell.cost <= myCsh && !spellAlreadyActive)
                    playGame(bossHp, myHp, myCsh - spell.cost, (List[Effect]() ::: newActiveEffects.toList) :+ Effect(spell, spell.duration),
                        false, spent + spell.cost)
        } else {
            myHp += myArmour - (if (myArmour - bossDamageStrenth < 0) bossDamageStrenth else 1)
            if (myHp > 0)
                playGame(bossHp, myHp, myCsh, newActiveEffects.toList, true, spent)
        }

    //
    // solve part 1 and 2
    //

    def findMinCostForWin(p1: Player, p2: Player): MinCostPath[GameState] =
        val start: GameState = GameState(p1, null, p2, List[Effect]())
        djikstra.minPath(start, id => id.player2.hitPoints <= 0)

    override def solvePart1: Any =
        //val minPath = findMinCostForWin(me, boss)
        //log.debug("minimum cost path: ")
        //minPath.printPath()
        //minPath.minCost
        playGame(boss.hitPoints, me.hitPoints, me.cash, List[Effect](), true, 0)
        minSpent

    override def solvePart2: Any =
        partTwo = true
        minSpent = Int.MaxValue
        playGame(boss.hitPoints, me.hitPoints, me.cash, List[Effect](), true, 0)
        minSpent
    
    extension (l: List[String])
        // Hit Points: 71
        // Damage: 10
        private def transform: List[Int] = l.map[Int](line => { val a = line.split(": "); a(1).toInt } )
}
