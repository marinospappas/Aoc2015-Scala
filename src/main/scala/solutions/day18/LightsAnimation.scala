package org.mpdev.scala.aoc2015
package solutions.day18

import utils.{Grid, GridBuilder, Point}
import framework.{InputReader, PuzzleSolver}
import solutions.day18.LightState.ON

class LightsAnimation extends PuzzleSolver {

    var grid: Grid[LightState] = InputReader.read(18).transform()
    var repeatAnimation = 100
    var debug = false

    private def ruleOn (neighboursOn: Int) = neighboursOn == 3

    private def ruleOff (neighboursOn: Int) = neighboursOn < 2 || neighboursOn > 3

    def animate (grid: Grid[LightState], part: Int = 1): Grid[LightState] =
        val (minX, maxX, minY, maxY) = grid.getMinMaxXY
        if (part == 2)
            for (p <- grid.getCorners) do grid.setDataPoint(p, LightState.ON)

        val newGrid = Grid(grid.getDataPoints, LightState.mapper, defaultSize = (maxX - minX + 1, maxY - minY + 1))
        for (x <- minX to maxX)
            for (y <- minY to maxY)
                val p = Point(x, y)
                val neighboursOn: Int = grid.getAdjacent(p, true).count( p => grid.getDataPointOptional(p).contains(ON) )
                if (grid.getDataPointOptional(p).contains(LightState.ON))
                    if (ruleOff(neighboursOn))
                        newGrid.removeDataPoint(p)
                else if (ruleOn(neighboursOn))
                    newGrid.setDataPoint(p, LightState.ON)

        if (part == 2)
            for (p <- newGrid.getCorners) do newGrid.setDataPoint(p, LightState.ON)
        newGrid

    override def solvePart1: Any =
        var currentGrid = Grid(grid.getDataPoints, LightState.mapper)
        for (_ <- 1 to repeatAnimation)
            currentGrid = animate(currentGrid)
        if (debug)
            currentGrid.printIt()
        currentGrid.countOf(LightState.ON)

    override def solvePart2: Any =
        var currentGrid = Grid(grid.getDataPoints, LightState.mapper)
        for (_ <- 1 to repeatAnimation)
            currentGrid = animate(currentGrid, 2)
        if (debug)
            currentGrid.printIt()
        currentGrid.countOf(LightState.ON)

    extension (l: List[String])
        private def transform() =
            GridBuilder[LightState]()
                .withMapper(LightState.mapper)
                .fromVisualGrid(l)
                .withBorder(0)
                .withDefaultSize((l.head.length, l.size))
                .build()
}

