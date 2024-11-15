package org.mpdev.scala.aoc2015
package solutions.day22

enum Spell(val cost: Int, val effect: Effect) {
    case MAGIC_MISSILE extends Spell(53, Effect(0, atStart = (_, p2) => p2.hitPoints -= 4))
    case DRAIN extends Spell(73, Effect(0, atStart = (p1, p2) => { p2.hitPoints -= 2; p1.hitPoints += 2 }))
    case SHIELD extends Spell(113, Effect(6, atStart = (p1, _) => p1.armourStrength += 7, atEnd = (p1, _) => p1.armourStrength -= 7))
    case POISON extends Spell(173, Effect(6, whileActive = (_, p2) => p2.hitPoints -= 3))
    case RECHARGE extends Spell(173, Effect(5, whileActive = (p1, _) => p1.cash += 101)
    )
}

// Magic Missile costs 53 mana. It instantly does 4 damage.
// Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
// Shield costs 113 mana. It starts an effect that lasts for 6 turns.
//      While it is active, your armor is increased by 7.
// Poison costs 173 mana. It starts an effect that lasts for 6 turns.
//      At the start of each turn while it is active, it deals the boss 3 damage.
// Recharge costs 229 mana. It starts an effect that lasts for 5 turns.
//      At the start of each turn while it is active, it gives you 101 new mana.

class Effect(var timer: Int = 0,
             val atStart: (Player, Player) => Unit = (p1, p2) => (),
             val whileActive: (Player, Player) => Unit = (p1,p2) => (),
             val atEnd: (Player, Player) => Unit = (p1,p2) => ()
           )