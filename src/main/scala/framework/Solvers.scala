package org.mpdev.scala.aoc2015
package framework

import solutions.day01.ElevatorButtons
import solutions.day02.PresentWrapping
import solutions.day04.Mad5HashProcessor
import solutions.day12.JsonProcessor
import solutions.day18.LightsAnimation
import solutions.day09.ShortestAndLongestRoute
import solutions.day05.StringsInspection

val solvers = Map(
    1 -> ElevatorButtons(), 2 -> PresentWrapping(), 4 -> Mad5HashProcessor(), 5 -> StringsInspection(),
    9 -> ShortestAndLongestRoute(), 12 -> JsonProcessor(),
    18 -> LightsAnimation()
)
