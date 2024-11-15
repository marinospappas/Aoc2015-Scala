package org.mpdev.scala.aoc2015
package solutions.day21

import framework.{InputReader, PuzzleSolver}

import scala.collection.mutable.ArrayBuffer
import scala.util.boundary
import scala.util.boundary.break

class FightGame extends PuzzleSolver {

    private val bossData = InputReader.read(21).transform
    val boss: Player = Player("Boss", bossData.head, bossData(1), bossData(2))

    def playRound(player1: Player, player2: Player): Int =
        // player 1 attacks
        player2.defend(player1)
        if (player2.hitPoints <= 0)
            return 1    // player 1 wins
        // player 2 attacks (if still standing)
        player1.defend(player2)
        if (player1.hitPoints <= 0) -1   // player 2 wins
        else 0   // fight is stil on

    def playGame(player1: Player, player2: Player): Int =
        var result = 0
        while result == 0 do
            result = playRound(player1, player2)
        // 1 = player 1 wins, 2 = player 2 wins
        if (result == 1) 1 else 2

    def playersCombinations(): List[Player] =
        val combis: ArrayBuffer[Player] = ArrayBuffer()
        for (weapon <- Weapon.values)
            for (armour <- Armour.values)
                for (rings <- ringCombinations())
                    combis += Player.apply(weapon, armour, rings, "Me", 100)
        combis.sortBy( p => p.cost).toList

    def ringCombinations(): List[Set[Ring]] =
        val combis: ArrayBuffer[Set[Ring]] = ArrayBuffer()
        combis += Set[Ring]()
        for (ring <- Ring.values)
            combis += Set(ring)
        for (rings <- Ring.values.combinations(2))
            combis += rings.toSet
        combis.toList

    override def solvePart1: Any =
        var result = -1
        boundary:
            for (player <- playersCombinations())
                if (playGame(player, Player("Boss", boss.hitPoints, boss.damageStrength, boss.armourStrength)) == 1)
                    result = player.cost
                    break()
        result

    override def solvePart2: Any =
        var result = -1
        boundary:
            for (player <- playersCombinations().reverse)
                if (playGame(player, Player("Boss", boss.hitPoints, boss.damageStrength, boss.armourStrength)) == 2)
                    result = player.cost
                    break()
        result

    extension (l: List[String])
        // Hit Points: 109
        // Damage: 8
        // Armor: 2
        private def transform: List[Int] = l.map[Int](line => { val a = line.split(": "); a(1).toInt } )
}
