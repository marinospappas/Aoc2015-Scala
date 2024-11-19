package org.mpdev.scala.aoc2015
package solutions.day22

case class Player(name: String = "", cash: Int = 0, hitPoints: Int = 0, damageStrength: Int = 0, armourStrength: Int = 0) {
    def defend(player2: Player): Player =
        val damageSuffered = player2.damageStrength - armourStrength
        Player(name, cash, hitPoints - ( if (damageSuffered < 0) 1 else damageSuffered ), damageStrength, armourStrength)
        
    def gameOver: Boolean = hitPoints <= 0
}

object Player {
    def newPlayer(p: Player): Player = Player(p.name, p.cash, p.hitPoints, p.damageStrength)
}
