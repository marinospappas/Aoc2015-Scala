package org.mpdev.scala.aoc2015
package solutions.day09

import framework.{InputReader, PuzzleSolver}
import utils.{Graph, GraphBuilder, Tsp}

class ShortestAndLongestRoute extends PuzzleSolver {
    
    private val START = "Start"
    val graph: Graph[String] = Graph[String]()
    InputReader.read(9).transform.foreach( e => graph.addNodesWithCost(e._1, e._2, true))
    // add "Start" node at distance 0 from each node (means the route can start anywhere)
    graph.addNodesWithCost(START, graph.getNodes.map( _ -> 0 ).toSet, true)
    
    override def solvePart1: Any = Tsp(graph).tspMinPath(START).minCost

    override def solvePart2: Any = graph.longestPathDfs(START, true, (id: String) => id == START)

    extension (l: List[String])
        // London to Dublin = 464
        private def transform: List[(String, Set[(String, Int)])] =
            l.map( s => {
                val a = s.split("""to|=""")
                ( a(0).trim, Set((a(1).trim, a(2).trim.toInt)) )
            } )
}
