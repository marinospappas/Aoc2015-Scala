package org.mpdev.scala.aoc2015
package utils

class MinCostPath[T] {

    var path: List[(T,Int)] = List()
    var minCost: Int = Int.MaxValue
    var numberOfIterations: Int = 0

    def printPath(minCostPath: MinCostPath[T]): Unit =
        println(s"path,cost: ${minCostPath.path}")
        println(s"min cost: ${minCostPath.minCost}")
    
}
