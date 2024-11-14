package org.mpdev.scala.aoc2015
package utils.aocvm

import scala.collection.mutable.ArrayBuffer

trait CustomOpCode {
    def execute(instructionList: ArrayBuffer[(OpCode, List[Any])], pc: Int, params: List[Any]): Unit
}
