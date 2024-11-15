package org.mpdev.scala.aoc2015
package solutions.day21

enum Ring(val cost: Int, val damage: Int, val armour: Int) {
    case DAMAGE_1 extends Ring(25, 1, 0)
    case DAMAGE_2 extends Ring(50, 2, 0)
    case DAMAGE_3 extends Ring(100, 3, 0)
    case DEFENSE_1 extends Ring(20, 0, 1)
    case DEFENSE_2 extends Ring(40, 0, 2)
    case DEFENSE_3 extends Ring(80, 0, 3)
}