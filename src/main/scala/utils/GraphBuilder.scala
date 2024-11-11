package org.mpdev.scala.aoc2015
package utils

import scala.collection.mutable

case class GraphBuilder[T](
                               nodes: Map[T, mutable.Map[T, Int]] = Map[T, mutable.Map[T, Int]](),
                               customGetConnected: Option[T => Set[(T, Int)]] = Option.empty[T => Set[(T, Int)]],
                               heuristic: Option[T => Int] = Option.empty[T => Int]
                           ) {

    def fromNodesListWithCost(nodesList: List[(T, Set[(T, Int)])]): GraphBuilder[T] = copy(nodes =
        nodesList.map( n => n._1 -> mutable.Map(n._2.map( c => c._1 -> c._2 ).toSeq*) ).toMap
    )

    def fromNodesList(nodesList: List[(T, Set[T])]): GraphBuilder[T] = copy(nodes =
        nodesList.map( n => n._1 -> mutable.Map(n._2.map( c => c -> 1 ).toSeq*) ).toMap
    )

    def fromGraph(graph: Graph[T]): GraphBuilder[T] = 
        copy(nodes = graph.nodes.toMap, customGetConnected = graph.customGetConnected, heuristic = graph.heuristic)
        
    def withCustomGetConnected(customGetConnected: Option[T => Set[(T, Int)]]): GraphBuilder[T] = copy(customGetConnected = customGetConnected)

    def withHeuristic(heuristic: Option[T => Int]): GraphBuilder[T] = copy(heuristic = heuristic)

    def build(): Graph[T] = Graph[T](mutable.Map(nodes.toSeq*), customGetConnected, heuristic)

}
