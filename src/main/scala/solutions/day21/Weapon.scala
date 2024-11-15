package org.mpdev.scala.aoc2015
package solutions.day21

enum Weapon(val cost: Int, val damage: Int, val armour: Int) {
    case DAGGER extends Weapon(8, 4, 0)
    case SHORT_SWORD  extends Weapon(10, 5, 0)
    case WAR_HAMMER extends Weapon(25, 6, 0)
    case LONG_SWORD extends Weapon(40, 7, 0)
    case GREAT_AXE extends Weapon(74, 8, 0)
}