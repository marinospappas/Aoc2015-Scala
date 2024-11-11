package org.mpdev.scala.aoc2015
package utils

private var iterations = 0

class Tsp[T](g: Graph[T]) {

    def tspMinPath(start: T, returnToStart: Boolean = false): MinCostPath[T] =
        iterations = 0
        val minCostPath = MinCostPath[T]()
        minCostPath.path = tsp(start, g.getNodes.filter(_ != start), List((start, 0)), returnToStart)
        minCostPath.minCost = minCostPath.path.map(_._2).sum
        minCostPath.numberOfIterations = iterations
        minCostPath

    private def tsp(start: T,
                           destinations: List[T],
                           path: List[(T, Int)],
                           returnToStart: Boolean = false): List[(T, Int)] =
        var curPath = path
        if (destinations.size == 1)
            curPath = curPath :+ (destinations.head, g.getCost(start, destinations.head))
            if (returnToStart) // add cost to return to start
                curPath = curPath :+ (curPath.head._1, g.getCost(destinations.head, curPath.head._1))
            return curPath
        var minCost = (curPath, Int.MaxValue)
        for (dest <- destinations) do
            iterations += 1
            val thisPath = tsp(dest, destinations.filter(_ != dest), curPath :+ (dest, g.getCost(start, dest)), returnToStart)
            if (thisPath.map(_._2).sum < minCost._2)
                minCost = (thisPath, thisPath.map(_._2).sum)
        minCost._1

}
