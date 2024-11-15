package org.mpdev.scala.aoc2015
package solutions.day21

case class Player(var name: String, var hitPoints: Int, var damageStrength: Int, var armourStrength: Int) {

    def this(weapon: Weapon, armour: Armour, rings: List[Ring], name: String = "", hitPoints: Int = 0) =
        this(name, hitPoints, weapon.damage + rings.map( _.damage ).sum, armour.armour + rings.map( _.armour ).sum)
        cost = weapon.cost + armour.cost + rings.map(_.cost).sum

    var cost: Int = 0

    def defend(player2: Player): Unit =
        val damageSuffered = player2.damageStrength - armourStrength
        hitPoints -= (if (damageSuffered < 0) 1 else damageSuffered )
}

object Player {
    def apply(weapon: Weapon, armour: Armour, rings: Set[Ring], name: String = "", hitPoints: Int = 0): Player =
        val player = Player(name, hitPoints, weapon.damage + rings.map( _.damage ).sum, armour.armour + rings.map( _.armour ).sum)
        player.cost = weapon.cost + armour.cost + rings.map(_.cost).sum
        player
}
