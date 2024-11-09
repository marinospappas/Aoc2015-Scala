package org.mpdev.scala.aoc2015
package solutions.day18

enum LightState(val value: Char) {
    case ON extends LightState('#')
    case OFF extends LightState('.')
}

object LightState {
    val mapper: Map[Char, LightState] = LightState.values.map( v => (v.value, v) ).toMap
}
