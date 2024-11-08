package org.mpdev.scala.aoc2015
package framework

import java.io.FileNotFoundException
import scala.io.Source.fromFile

object InputReader {
    
    def read(day: Int, pattern: String = "src/main/resources/input", extension: String = "txt"): List[String] = {
        val name = pattern + f"$day%02d" + "." + extension
        val source = fromFile(name)
        try
            source.getLines().toList
        catch case ex: FileNotFoundException =>
            throw AoCException("Could not process file [" + name + "]" + ex.getMessage)
        finally
            source.close()
    }

}
