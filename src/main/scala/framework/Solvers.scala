package org.mpdev.scala.aoc2015
package framework

import solutions.day01.ElevatorButtons
import solutions.day02.PresentWrapping
import solutions.day04.Mad5HashProcessor
import solutions.day12.JsonProcessor
import solutions.day18.LightsAnimation
import solutions.day09.ShortestAndLongestRoute
import solutions.day05.StringsInspection
import solutions.day07.DigitalCircuit
import solutions.day23.Computer
import solutions.day21.FightGame
import solutions.day22.WizardGame
import solutions.day16.IdentificationOfItems
import solutions.day08.StringLengthCalculator
import solutions.day17.Containerisation
import solutions.day20.PacketsDistribution

val solvers = Map(
    1 -> ElevatorButtons(), 2 -> PresentWrapping(), 4 -> Mad5HashProcessor(), 5 -> StringsInspection(),
    7 -> DigitalCircuit(), 8 -> StringLengthCalculator(), 9 -> ShortestAndLongestRoute(),
    12 -> JsonProcessor(),
    16 -> IdentificationOfItems(), 17 -> Containerisation(), 18 -> LightsAnimation(), 20 -> PacketsDistribution(),
    21 -> FightGame(), 22 -> WizardGame(), 23 -> Computer()
)
