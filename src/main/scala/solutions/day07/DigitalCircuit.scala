package org.mpdev.scala.aoc2015
package solutions.day07

import framework.{InputReader, PuzzleSolver}
import solutions.day07.DigitalGate.VALUE

import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable
import scala.language.postfixOps

class DigitalCircuit extends PuzzleSolver{

    var circuit: DigitalNode = DigitalNode()
    var gatesMap: Map[String, DigitalNode] = Map[String, DigitalNode]()

    {   // initialisation code
        val inputData = InputReader.read(7).transform
        val connectionsMap: Map[String, List[String]] = inputData
            .map( g => g._4 -> List(g._2, g._3) ).toMap
        gatesMap = inputData.map( g => g._4 -> DigitalNode(id = g._4, gate = g._1)).toMap
        gatesMap.values.foreach( node =>
            val connections = connectionsMap(node.id)
            val inputs = ArrayBuffer[Any]()
            for (c: String <- connections.filter(_.nonEmpty)) do
                if (c.forall(Character.isDigit))
                    inputs += c.toInt
                else
                    inputs += gatesMap(c)
            node.inputs = inputs.toList
        )
        circuit = gatesMap(DigitalCircuit.circuitRoot)
    }

    private def calculateOutput(node: DigitalNode, calculatedOutput: mutable.Map[String, Int]): Int =
        if (calculatedOutput.contains(node.id))
            return calculatedOutput(node.id)    // this output has already been calculated and is in the cache
        val inputValues = ArrayBuffer[Int]()
        for (in <- node.inputs)
            inputValues += (in match
                case digitalNode: DigitalNode => calculateOutput(digitalNode, calculatedOutput)
                case i: Int => i
                case _ => - 1
            )
        val output = node.gate.function(inputValues.toList)
        calculatedOutput += node.id -> output
        output

    override def solvePart1: Any = calculateOutput(circuit, mutable.Map[String, Int]())

    override def solvePart2: Any =
        gatesMap(DigitalCircuit.overrideNode).inputs = List(solvePart1)
        calculateOutput(circuit, mutable.Map[String, Int]())

    extension (l: List[String])
        // 123 -> x
        // NOT x -> h
        // gf OR ge -> gg
        // 1 AND io -> ip
        private def transform: List[(DigitalGate, String, String, String)] =
            l.map[(DigitalGate, String, String, String)]( s =>
                val a = s.split(" ")
                a.length match
                    case 3 => (DigitalGate.VALUE, a(0), "", a(2))
                    case 4 => (DigitalGate.valueOf(a(0)), a(1), "", a(3))
                    case _ => (DigitalGate.valueOf(a(1)), a(0), a(2), a(4))
            )

    object DigitalCircuit {
        var circuitRoot = "a"
        var overrideNode = "b"
    }

}
