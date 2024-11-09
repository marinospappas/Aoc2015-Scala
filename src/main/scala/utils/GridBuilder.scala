package org.mpdev.scala.aoc2015
package utils

import scala.collection.mutable
import scala.language.postfixOps


case class GridBuilder[T](gridData: Map[Point,T] = Map(),
                          mapper: Map[Char,T] = Map(),
                          border: Int = 0,
                          defaultChar: Char = '.',
                          defaultSize: (Int, Int) = (-1,-1)) {

    def withGridData(gridData: Map[Point,T]): GridBuilder[T] = copy(gridData = gridData)

    // IMPORTANT!! requires the mapper to have been set
    def fromVisualGrid(inputGridVisual: List[String]): GridBuilder[T] = 
        copy(gridData = processInputVisual(inputGridVisual, mapper))

    def fromPointsArray(inputPointsArr: Array[Point]): GridBuilder[T] = copy(gridData = processInputXY(inputPointsArr))

    def fromXYSetVisual(inputXYSet: Set[String]): GridBuilder[T] = copy(gridData = processInputXY(inputXYSet))

    def withMapper(mapper: Map[Char,T]): GridBuilder[T] = copy(mapper = mapper)

    def withBorder(border: Int): GridBuilder[T] = copy(border = border)

    def withDefaultChar(defaultChar: Char): GridBuilder[T] = copy(defaultChar = defaultChar)

    def withDefaultSize(defaultSize: (Int, Int)): GridBuilder[T] = copy(defaultSize = defaultSize)

    def build(): Grid[T] = Grid[T](gridData, mapper, border, defaultChar, defaultSize)

    // conversion of input data to Map[Point,T]
    private def processInputVisual(input: List[String], mapper: Map[Char, T]): Map[Point, T] =
        val thisMap = mutable.Map[Point, T]()
        for (y <- input.indices)
            for (x <- input(y).indices)
                if (mapper contains input(y)(x))
                    thisMap += (Point(x, y) -> mapper(input(y)(x)))
        thisMap.toMap
        
    private def processInputXY(input: Array[Point]): Map[Point, T] =
        val thisMap = mutable.Map[Point, T]()
        for (p <- input)
            thisMap += p -> mapper.values.head
        thisMap.toMap

    private def processInputXY(input: Set[String]): Map[Point, T] =
        val thisMap = mutable.Map[Point, T]()
        for (s <- input)
            val coords = s.split(",")
            thisMap += Point(coords(0).trim().toInt, coords(1).trim().toInt) -> mapper.values.head
        thisMap.toMap
}

