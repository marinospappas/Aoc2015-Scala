package org.mpdev.scala.aoc2015
package utils.aocvm

import utils.aocvm.ProgramState.{COMPLETED, READY, RUNNING, WAIT}
import utils.aocvm.OpResultCode.*

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer
import scala.util.boundary
import boundary.break

class Program(prog: List[String], ioChannel: List[Channel[Long]] = List[Channel[Long]]()) {

    private val log: Logger = LoggerFactory.getLogger("Program")
    var programState: ProgramState = READY
    var instanceName = ""

    private val sourcePgmOptions: Map[String, String] =
        if (prog.nonEmpty && prog.head.startsWith("#!"))
            prog.head.substring(2).split(",").map ( s => {
                val a = s.split("=")
                a(0) -> a(1)
            } ).toMap
        else
            Map()

    private val instructionList: ArrayBuffer[(OpCode, List[Any])] =
        prog.filterNot ( line => line.startsWith("#") || line.isEmpty )
            .map ( _.substring(if (sourcePgmOptions("indent") == null) 0 else sourcePgmOptions("indent").toInt ) )
            .map ( _.split(" ") )
            .map ( x => (InstructionSet.opCodeFomString(x(0)), x.toList.slice(1, x.length).map ( v => v.toIntOrString )) )
            .to(ArrayBuffer)

    private val registers = mutable.Map[String, Long]().withDefaultValue(0)

    //suspend
    def run(initReg: Map[String, Long] = Map(), maxCount: Int = Int.MaxValue): Unit =
        log.debug("$instanceName starting, init registers: $initReg")
        var pc: Int = 0
        var outputCount: Int = 0
        registers.clear()
        initReg.foreach( (reg, v) => registers(reg) = v )
        boundary:
            while (pc <= instructionList.size && outputCount < maxCount)
                val (instr, params) = instructionList(pc)
                val mappedParams = mapParams(params, instr.paramMode, instr.numberOfParams)
                log.debug("$instanceName pc: $pc instruction: ${instr.code} $mappedParams")
                val (resCode, values) = instr.execute(mappedParams)
                log.debug("$instanceName     result: $resCode $values")
                resCode match
                    case SET_MEMORY => registers(values.head.asInstanceOf[String]) = valueOf(values(1)).asInstanceOf[Long]
                    case INCR_PC => pc += valueOf(values.head.asInstanceOf[Int]).asInstanceOf[Int] - 1
                    case OUTPUT =>
                        log.debug("AocProg {} writing to output {}", instanceName, values.head)
                        ioChannel(1).send(valueOf(values.head))
                        outputCount += 1
                    case INPUT =>
                        programState = WAIT
                        log.debug("AocProg {} waiting for input will be stored in {}", instanceName, values.head)
                        // will be suspended below and the value in retCode may change
                        val inputValue = ioChannel.head.receive()
                        registers(values.head.asInstanceOf[String]) = inputValue
                        programState = RUNNING
                        log.debug("AocProg {} received input {} to be stored in {}", instanceName, inputValue, values.head)
                    case CUSTOM => values.head.asInstanceOf[CustomOpCode].execute(instructionList, pc, values)
                    case EXIT => break()
                    case NONE => ;
            pc += 1

        programState = COMPLETED


    def getRegister(reg: String): Long = registers(reg)
    def setRegister(reg: String, value: Long): Unit = registers(reg) = value

    def getMemory(address: Int): Long = getRegister(address.toString)
    def setMemory(address: Int, value: Long): Unit = setRegister(address.toString, value)

    private def valueOf(x: Any): Any =
        x match
            case i: Int => i
            case l: Long => l
            case s: String => registers(s)

    extension (s: String)
        private def toIntOrString: Any = try
            s.toInt
        catch
            case e: Exception => s

    private def mapParams(params: List[Any], paramMode: List[ParamReadWrite], numberOfParams: Int): List[Any] =
        val newParams = ArrayBuffer[Any]()
        // if the number of params is > than the number if the input params then the first param is added as last as well (to be used as output)
        val thisParams = if (numberOfParams > params.size) params :+ params.head else params
        for (i <- thisParams.indices) do
            // R params are translated to value - W params are left as they are
            newParams += (if (paramMode(i) == ParamReadWrite.R) valueOf(thisParams(i)) else thisParams(i))
        newParams.toList

}

enum ProgramState {
    case READY
    case RUNNING
    case WAIT
    case COMPLETED
}