package org.mpdev.scala.aoc2015
package solutions.day23

import framework.{InputReader, PuzzleSolver}
import utils.aocvm.{AocVm, InstructionSet, OpCode}
import utils.aocvm.ParamReadWrite.{R, W}
import utils.aocvm.OpResultCode.*

import solutions.day23.Computer.initialiseOpCodes

class Computer extends PuzzleSolver {

    initialiseOpCodes()
    private val aocVm: AocVm = AocVm(InputReader.read(23).transform())
    val id: Int = aocVm.newProgram(InputReader.read(23).transform())

    override def solvePart1: Any =
        aocVm.sendInputToProgram(0)
        aocVm.runProgram()
        aocVm.waitForProgram()
        aocVm.getFinalOutputFromProgram.last

    override def solvePart2: Any =
        aocVm.sendInputToProgram(1)
        aocVm.runProgram()
        aocVm.waitForProgram()
        aocVm.getFinalOutputFromProgram.last

    extension(l: List[String])
        private def transform(): List[String] =
            val programList: List[String] = ("in a" :: l) :+ "out b"
            programList.map( _.replace(", ", " ") )
}

object Computer {
    def initialiseOpCodes(): Unit =
        InstructionSet.opCodesList("hlf") = OpCode("hlf", 2, List(R, W),
            { a => (SET_MEMORY, List(a(1), a.head.asInstanceOf[Long] / 2L)) }
        )
        InstructionSet.opCodesList("tpl") = OpCode("tpl", 2, List(R, W),
            { a => (SET_MEMORY, List(a(1), a.head.asInstanceOf[Long] * 3L)) }
        )
        InstructionSet.opCodesList("jie") = OpCode("jie", 2, List(R, R),
            { a => if (a.head.asInstanceOf[Long] % 2L == 0L) (INCR_PC, List(a(1))) else (NONE, List()) }
        )
        InstructionSet.opCodesList("jio") = OpCode("jio", 2, List(R, R),
            { a => if (a.head.asInstanceOf[Long] == 1L) (INCR_PC, List(a(1))) else (NONE, List()) }
        )
}
