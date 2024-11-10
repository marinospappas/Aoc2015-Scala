package org.mpdev.scala.aoc2015
package utils

import scala.collection.{immutable, mutable}

/**
 * graph class
 * nodes: map of node_id to (map connected_node_id to weight)
 */
//TODO replace the Int weight with Weight<U> to be able to implement custom compare
open class Graph[T](
                        val nodes: mutable.Map[T, mutable.Map[T, Int]] = mutable.Map(),
                        val useCustomGetConnectedFunction: Boolean = false,
                        val customGetConnected: Option[T => Set[(T, Int)]] = Option.empty,
                        val heuristic: Option[T => Int] = Option.empty
                    ) {

    private val customFunction: T => Set[(T, Int)] = if (customGetConnected.nonEmpty) customGetConnected.get else { T => Set() }

    def getConnected(id: T): Set[(T, Int)] =
        if (!nodes.contains(id))
            Set()
        else if (customGetConnected.nonEmpty)
            customFunction(id)
        else
            nodes(id).map(e => (e._1, e._2)).toSet

    def get(id: T): mutable.Map[T, Int] = nodes(id)

    def getNodes: List[T] = nodes.keys.toList

    def getNodesAndConnections: List[(T, mutable.Map[T, Int])] = nodes.toList

    def addNode(id: T, connected: T, connectBothWays: Boolean = false): Unit =
        addNodesWithCost(id, Set((connected, 1)), connectBothWays)

    def addNodes(id: T, connected: Set[T], connectBothWays: Boolean = false): Unit =
        addNodesWithCost(id, connected.map((_, 1)), connectBothWays)

    def addNodesWithCost(id: T, connected: Set[(T, Int)], connectBothWays: Boolean = false): Unit =
        if (!nodes.contains(id))
            nodes += (id -> mutable.Map())
        connected.foreach( e => nodes(id) += e._1 -> e._2 )
        if (connectBothWays)
            connected.foreach( e => addNodesWithCost(e._1, Set((id, e._2))) )

    def removeConnected(a: T, b: T): Unit = removeConnection((a, b))

    def removeConnection(edge: (T, T)): Unit =
        nodes(edge._1) -= edge._2
        nodes(edge._2) -= edge._1

    def getCost(a: T, b: T): Int = nodes(a)(b)

    def setCost(a: T, b: T, cost: Int): Unit = nodes(a) += b -> cost

    def getAllConnectedPairs: Set[Set[T]] =
        nodes.flatMap(n => n._2.map(c => Set(n._1, c._1))).toSet

    def printIt(): Unit =
        var count = 1
        println("SGraph")
        nodes.foreach( e =>
                count += 1
                println(s"node $count: ${e._1} connected to: ${e._2}")
        )

    /*
        fun longestPathDfs(start: T, includeAllNodes: Boolean = true, isAtEnd: (T) -> Boolean): Int {
            return dfsMaxPath(start, isAtEnd, mutableMapOf(), includeAllNodes)
        }

        //TODO: refactor the below function to use Stack instead of recursion
        private fun dfsMaxPath(cur: T, isAtEnd: (T) -> Boolean, visited: MutableMap<T, Int>, includeAllNodes: Boolean): Int {
            if (isAtEnd(cur) &&
                if (includeAllNodes) visited.size == nodes.size else visited.isNotEmpty()) {
                return visited.values.sum()
            }
            var maxPath = Int.MIN_VALUE
            getConnected(cur).forEach { (neighbor, steps) ->
                if (neighbor !in visited) {
                    visited[neighbor] = steps
                    val res = dfsMaxPath(neighbor, isAtEnd, visited, includeAllNodes)
                    if (res > maxPath) {
                        maxPath = res
                    }
                    visited.remove(neighbor)
                }
            }
            return maxPath
     */
    }

    object Graph {

        var aStarAlgorithm = false // flag used to distinguish between A* and Dijkstra algorithm for min cost path

        //def from(g: SGraph[T]): SGraph[T] =
        //    val newGraph = SGraph[T]()
        //    for ((id, connxns) <- g.nodes) do newGraph.addNode(id, connxns.toMap())
        //    newGraph

}
