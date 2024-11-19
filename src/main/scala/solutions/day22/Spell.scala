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
enum Spell(val cost: Int, val immediateEffect: Boolean, val duration: Int, val damage: Int, val armour: Int, val repair: Int, val cash: Int) {
    case MAGIC_MISSILE extends Spell(53,  true, 0, 4, 0, 0, 0)
    case DRAIN         extends Spell(73,  true, 0, 2, 0, 2, 0)
    case SHIELD        extends Spell(113, true, 6, 0, 7, 0, 0)
    case POISON        extends Spell(173, false, 6, 3, 0, 0, 0)
    case RECHARGE      extends Spell(229, false, 5, 0, 0, 0, 101)
}

case class Effect(spell: Spell, timer: Int)

object Effect {
    def newEffect(e: Effect): Effect = Effect(e.spell, e.timer)
}