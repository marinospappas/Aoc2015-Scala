package org.mpdev.scala.aoc2015
package solutions.day07

import scala.collection.mutable.ArrayBuffer

case class DigitalNode(id: String = "", gate: DigitalGate = DigitalGate.VALUE, var inputs: List[Any] = List[Any]()) {

    override def toString: String = s"Node[id: $id, function: $gate (${inputs.map(inputToStr).mkString(", ")})"

    private def inputToStr(in: Any): String =
        in match
            case node: DigitalNode => node.id
            case _ => in.toString
}
