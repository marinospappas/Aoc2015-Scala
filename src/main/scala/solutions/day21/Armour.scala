package org.mpdev.scala.aoc2015
package solutions.day21

enum Armour(val cost: Int, val damage: Int, val armour: Int) {
    case NONE extends Armour(0, 0, 0)
    case LEATHER extends Armour(13, 0, 1)
    case CHAIN_MAIL extends Armour(31, 0, 2)
    case SPLINT_MAIL extends Armour(53, 0, 3)
    case BANDED_MAIL extends Armour(75, 0, 4)
    case PLATE_MAIL extends Armour(102, 0, 5)
}
