package org.mpdev.scala.aoc2015.utils.aocvm

import org.slf4j.Logger
import org.slf4j.LoggerFactory

import scala.collection.mutable.ArrayBuffer

abstract class AbstractAocVm(instructionList: List[String], val instanceNamePrefix: String) {

    val log: Logger = LoggerFactory.getLogger(classOf[AbstractAocVm])

    // the AoCVM "process table"
    private val instanceTable: ArrayBuffer[AocInstance] = ArrayBuffer[AocInstance]()

    {
        // clears the instance table and creates the first instance of the AocCode program
        instanceTable.clearAndShrink()
        setupNewInstance(instructionList)
    }

    object AbstractAocVm {
        val DEF_PROG_INSTANCE_PREFIX = "aocprog"
    }

    protected def setupNewInstance(instructionList: List[String]): Int =
        val ioChannels = ArrayBuffer[Channel[Long]](Channel[Long](), Channel[Long]())
        instanceTable += AocInstance(Program(instructionList, ioChannels), ioChannels)
        val programId = instanceTable.size
        instanceTable(programId).program.instanceName = s"$instanceNamePrefix-$programId"
        log.info(s"AocCode instance [$programId] configured")
        programId

    /*protected def aocCtl(programId: Int, cmd: AocCmd, value: Any): Unit
        cmd match
            case AocCmd.SET_OUTPUT_BUFFER_SIZE => instanceTable(progarmId).ioChannels(1) = Channel(value.asInstanceOf[Int])*/

    /// protected / internal functions
    //suspend
    def runAocProgram(programId: Int, initReg: Map[String, Long] = Map()): Unit =
        instanceTable(programId).program.run(initReg)

    protected def aocProgramIsRunning(programId: Int) =
        instanceTable(programId).program.programState != COMPLETED

    protected /*suspend*/ def waitAocProgram(/*job: Job*/): Unit =
        // TODO job.join()
        return

    protected /*suspend*/ def setProgramInput(data: List[Long], programId: Int): Unit =
        log.debug("set program input to {}", data)
        setInputValues(data, instanceTable(programId).ioChannels[0])

    protected /*suspend*/ def getProgramFinalOutputLong(programId: Int): List[Long] =
        log.debug("getProgramFinalOutputLong called")
        delay(1)      // required in case the program job is still waiting for input
        while (instanceTable[programId].program.programState == RUNNING) {     // job active = still producing output
            delay(1)
        }
        val output = getOutputValues(instanceTable[programId].ioChannels[1])
        log.debug("returning output: {}", output)
        output

    protected /*suspend*/ def getProgramAsyncOutputLong(programId: Int): List[Long] =
        log.debug("getProgramAsyncOutputLong called")
        val outputValues = ArrayBuffer[Long]()
        outputValues.add(instanceTable[programId].ioChannels[1].receive())
        while (!instanceTable[programId].ioChannels[1].isEmpty) {
            outputValues.add(instanceTable[programId].ioChannels[1].receive())
        }
        log.debug("returning output: {}", outputValues)
        outputValues

    private /*suspend*/ def setInputValues(values: List[Long], inputChannel: Channel[Long]) =
        values.forEach(inputChannel.+= _)

    private /*suspend*/ def getOutputValues(outputChannel: Channel[Long]): List[Long] =
        val outputValues = ArrayBuffer[Long]()
        outputValues.+=(outputChannel.receive())
        do {
            val nextItem = outputChannel.tryReceive().getOrNull()
            if (nextItem != null)
                outputValues.add(nextItem)
        } while(nextItem != null)
        outputValues
    }

    protected def setProgramMemory(programId: Int, address: Int, data: Long): Unit =
        instanceTable(programId).program.setMemory(address, data)

    protected def setProgramMemory(programId: Int, address: Int, data: Int): Unit =
        setProgramMemory(programId, address, data.toLong)

    protected def getProgramMemoryLong(programId: Int, address: Int): Long =
        instanceTable(programId).program.getMemory(address)

    protected def getProgramMemory(programId: Int, address: Int): Int =
        getProgramMemoryLong(programId, address).toInt

    protected def getProgramRegisterLong(programId: Int, reg: String): Long =
        instanceTable(programId).program.getRegister(reg)

    protected def getProgramRegister(programId: Int, reg: String): Int =
        getProgramRegisterLong(programId, reg).toInt

    case class AocInstance(program: Program, ioChannels: ArrayBuffer[Channel[Long]])

    enum AocCmd {
        case SET_OUTPUT_BUFFER_SIZE
    }
}