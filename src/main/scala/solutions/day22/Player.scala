package org.mpdev.scala.aoc2015
package solutions.day22

case class Player(name: String, var cash: Int = 0, var hitPoints: Int = 0, var damageStrength: Int = 0, var armourStrength: Int = 0) {
    def defend(player2: Player): Unit =
        val damageSuffered = player2.damageStrength - armourStrength
        hitPoints -= (if (damageSuffered < 0) 1 else damageSuffered )
}

object Player {
    def newPlayer(p: Player): Player = Player(p.name, p.cash, p.hitPoints, p.damageStrength, p.armourStrength)
}
