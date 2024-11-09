package org.mpdev.scala.aoc2015
package framework

import solutions.day01.ElevatorButtons
import solutions.day02.PresentWrapping
import solutions.day04.Mad5HashProcessor
import solutions.day12.JsonProcessor
import solutions.day18.LightsAnimation

val solvers = Map(
    1 -> ElevatorButtons(), 2 -> PresentWrapping(), 4 -> Mad5HashProcessor(),
    12 -> JsonProcessor(),
    18 -> LightsAnimation()
)
