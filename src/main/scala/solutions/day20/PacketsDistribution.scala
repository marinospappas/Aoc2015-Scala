package org.mpdev.scala.aoc2015
package solutions.day20

import framework.{InputReader, PuzzleSolver}
import utils.PrimeNumbers
import scala.math.{sqrt, ceil}

class PacketsDistribution extends PuzzleSolver {

    private final val target = InputReader.read(20).head.toInt
    {
        PrimeNumbers.eratosthenesSieve(target)
    }
    
    private def findSumOfDivisors(number: Int) = 
        PrimeNumbers.divisors2(number).sum

    def findTotalPackets2(number: Int): Int = {
        var result = 0
        val n = ceil(sqrt(number.toDouble)).toInt
        for (i <- 1 to n) {
            if (number % i == 0) {
                if (i <= 50) {
                    result += number / i
                }
                if (number / i <= 50) {
                    result += i
                }
            }

        }
        result * 11
    }

    def solvePart1: Any = {
        var n = 1
        val upperLimit = target / 10
        while findSumOfDivisors(n) < upperLimit do
            n += 1
        n
    }

    def solvePart2: Any = {
        var n = 1
        while findTotalPackets2(n) < target do
            n += 1
        n
    }
}
