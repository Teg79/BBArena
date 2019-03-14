package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.event.Event
import bbarena.model.team.Player

abstract class PlayerEvent(val playerId: Long) : Event() {

    fun getPlayer(arena: Arena): Player {
        return arena.playerManager.getPlayer(playerId)
    }

}