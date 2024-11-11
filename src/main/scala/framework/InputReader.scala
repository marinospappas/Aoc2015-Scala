package org.mpdev.scala.aoc2015
package framework

import java.io.FileNotFoundException
import scala.io.Source.fromFile

object InputReader {

    private val filePattern = AocMain.environment match
        case "prod" => "src/main/resources/input"
        case _ => "src/test/resources/input"

    def read(day: Int, pattern: String = filePattern, extension: String = "txt"): List[String] = {
        if (AocMain.environment == "none")
            return List()
        val name = pattern + f"$day%02d" + s".$extension"
        val source = fromFile(name)
        try
            source.getLines().toList
        catch case ex: FileNotFoundException =>
            throw AoCException("Could not process file [" + name + "]" + ex.getMessage)
        finally
            source.close()
    }

}
