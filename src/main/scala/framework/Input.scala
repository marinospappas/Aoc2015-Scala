package org.mpdev.scala.aoc2015
package framework

import java.io.FileNotFoundException
import scala.io.Source.fromFile


def readInput(day: Int, pattern: String = "src/main/resources/input"): List[String] = {
    val name = pattern + f"$day%02d" + ".txt"
    val source = fromFile(name)
    try
        source.getLines().toList
    catch case ex: FileNotFoundException =>
        throw AoCException("Could not process file" + name)
    finally
        source.close()
}
 
