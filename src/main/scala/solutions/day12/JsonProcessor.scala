package org.mpdev.scala.aoc2015
package solutions.day12

import framework.{InputReader, PuzzleSolver}

class JsonProcessor extends PuzzleSolver(InputReader.read(12, extension = "json")) {

    private val inputData: ujson.Value = input.transform

    def isRedObject(jsonNode: ujson.Value): Boolean =
        jsonNode.isInstanceOf[ujson.Obj] && jsonNode.obj.values.map(_.value).toList.contains("red")

    private var numericSum = 0

    private def processJsonIntFields (jsonNode: ujson.Value, ignoreRed: Boolean): Unit =
        if (isRedObject(jsonNode) && ignoreRed)
            return
        for (node <- if (jsonNode.isInstanceOf[ujson.Arr]) jsonNode.arr else jsonNode.obj.values)
            if (node.isInstanceOf[ujson.Num])
                numericSum = numericSum + node.value.asInstanceOf[Double].intValue
            if (node.isInstanceOf[ujson.Arr] || node.isInstanceOf[ujson.Obj])
                processJsonIntFields(node, ignoreRed)

    override def solvePart1: Any =
        numericSum = 0
        processJsonIntFields(inputData, false)
        numericSum

    override def solvePart2: Any =
        numericSum = 0
        processJsonIntFields(inputData, true)
        numericSum

    extension (l: List[String])
        private def transform: ujson.Value = ujson.read(l.flatten.mkString(""))

    def inputValue(l: List[String]): Any = l.transform
}
