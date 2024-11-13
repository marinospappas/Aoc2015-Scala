package org.mpdev.scala.aoc2015
package solutions.day07

enum DigitalGate(val function: Seq[Int] => Int) {
    case AND    extends DigitalGate( input =>  input.head & input(1) )
    case OR     extends DigitalGate( input =>  input.head | input(1) )
    case NOT    extends DigitalGate( input => (input.head ^ 0xffff) & 0xffff )
    case LSHIFT extends DigitalGate( input =>  input.head << input(1) )
    case RSHIFT extends DigitalGate( input => (input.head >> input(1)) & 0xffff )
    case VALUE  extends DigitalGate( input =>  input.head )
}
    
