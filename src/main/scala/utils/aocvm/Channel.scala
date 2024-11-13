package org.mpdev.scala.aoc2015
package utils.aocvm

import scala.+:
import scala.collection.mutable.ArrayBuffer

class Channel[T] {

    private val buffer: ArrayBuffer[T] = ArrayBuffer[T]()
    
    def send(x: Any): Unit = buffer :+ x.asInstanceOf[T]
    
    def receive(): T
        //while (buffer.isEmpty)
        //    Thread.sleep(5)
        buffer.remove(0)
}
