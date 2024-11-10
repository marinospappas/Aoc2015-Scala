package org.mpdev.scala.aoc2015
package utils

import scala.collection.mutable

case class GraphBuilder[T](
                               nodes: Map[T, mutable.Map[T, Int]] = Map(),
                               useCustomGetConnectedFunction: Boolean = false,
                               customGetConnected: Option[T => Set[(T, Int)]] = Option.empty,
                               heuristic: Option[T => Int] = Option.empty
                           ) {

    def withNodesMap(graphNodes: Map[T, mutable.Map[T, Int]]): GraphBuilder[T] = copy(nodes = nodes)

    def withNodesList(nodesList: List[(T, Set[T])]): GraphBuilder[T] = copy(nodes =
        nodesList.map( n =>
            val connected = n._2.map( c => c -> 1 )
            n._1 -> mutable.Map(connected.toSeq*)
        ).toMap
    )

    def withCustomGetConnected(customGetConnected: Option[T => Set[(T, Int)]]): GraphBuilder[T] = copy(customGetConnected = customGetConnected)

    def withHeuristic(heuristic: Option[T => Int]): GraphBuilder[T] = copy(heuristic = heuristic)

    def build(): Graph[T] = Graph[T](mutable.Map(nodes.toSeq*), useCustomGetConnectedFunction, customGetConnected, heuristic)

}
