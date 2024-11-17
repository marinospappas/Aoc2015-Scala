package org.mpdev.scala.aoc2015
package solutions.day22

import scala.collection.mutable
import scala.collection.mutable.ArrayBuffer

case class GameState(player1: Player, spell: Spell | Null, player2: Player, effects: ArrayBuffer[Effect])

object GameState {
    def newState(state: GameState): GameState = 
        GameState(Player.newPlayer(state.player1), state.spell, Player.newPlayer(state.player2), state.effects.map(Effect.newEffect).to(ArrayBuffer))
}
