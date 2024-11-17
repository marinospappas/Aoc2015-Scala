package org.mpdev.scala.aoc2015
package solutions.day22

// Magic Missile costs 53 mana. It instantly does 4 damage.
// Drain costs 73 mana. It instantly does 2 damage and heals you for 2 hit points.
// Shield costs 113 mana. It starts an effect that lasts for 6 turns.
//      While it is active, your armor is increased by 7.
// Poison costs 173 mana. It starts an effect that lasts for 6 turns.
//      At the start of each turn while it is active, it deals the boss 3 damage.
// Recharge costs 229 mana. It starts an effect that lasts for 5 turns.
//      At the start of each turn while it is active, it gives you 101 new mana.
enum Spell(val cost: Int,
           var duration: Int,
           val applyImmediately: Boolean,
           val effect: (Player, Player, Int) => Unit = (p1, p2, t) => ()) {
    case MAGIC_MISSILE extends Spell(53,  1, true, (p1, p2, t) => p2.hitPoints -= 4)
    case DRAIN         extends Spell(73,  1, true, (p1, p2, t) => { p2.hitPoints -= 2; p1.hitPoints += 2 })
    case SHIELD        extends Spell(113, 6, true, (p1, p2, t) => { if (t == 6) p1.armourStrength += 7 else if (t == 0) p1.armourStrength -= 7 })
    case POISON        extends Spell(173, 6, false, (p1, p2, t) => p2.hitPoints -= 3)
    case RECHARGE      extends Spell(229, 5, false, (p1, p2, t) => p1.cash += 101)
}

case class Effect(spell: Spell, var timer: Int)

object Effect {
    def newEffect(e: Effect): Effect = Effect(e.spell, e.timer)
}