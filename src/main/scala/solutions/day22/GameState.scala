package org.mpdev.scala.aoc2015
package solutions.day22

case class GameState(playerHitPoints: Int, playerCash: Int, playerArmour: Int, bossHitPoints: Int, activeEffects: List[Effect], playerTurn: Boolean)

object GameState {
    val emptyState: GameState = GameState(0, 0, 0, 0, List[Effect](), true)
}

case class GameStateBuilder(state: GameState = GameState.emptyState) {
    
    def withPlayerHitPoints(playerHitPoints: Int): GameStateBuilder =
        copy(GameState(playerHitPoints, state.playerCash, state.playerArmour, state.bossHitPoints, state.activeEffects, state.playerTurn) )

    def withPlayerCash(playerCash: Int): GameStateBuilder =
        copy(GameState(state.playerHitPoints, playerCash, state.playerArmour, state.bossHitPoints, state.activeEffects, state.playerTurn))

    def withPlayerArmour(playerArmour: Int): GameStateBuilder =
        copy(GameState(state.playerHitPoints, state.playerCash, playerArmour, state.bossHitPoints, state.activeEffects, state.playerTurn))

    def withBossHitPoints(bossHitPoints: Int): GameStateBuilder =
        copy(GameState(state.playerHitPoints, state.playerCash, state.playerArmour, bossHitPoints, state.activeEffects, state.playerTurn))

    def withActiveSpells(activeEffects: List[Effect]): GameStateBuilder =
        copy(GameState(state.playerHitPoints, state.playerCash, state.playerArmour, state.bossHitPoints, List() ::: activeEffects, state.playerTurn))

    def withPlayerTurn(playerTurn: Boolean): GameStateBuilder =
        copy(GameState(state.playerHitPoints, state.playerCash, state.playerArmour, state.bossHitPoints, state.activeEffects, playerTurn))

    def build(): GameState = GameState(state.playerHitPoints, state.playerCash, state.playerArmour, state.bossHitPoints, state.activeEffects, state.playerTurn)
}
