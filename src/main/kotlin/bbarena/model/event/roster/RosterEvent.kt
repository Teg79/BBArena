package bbarena.model.event.roster

import bbarena.model.Arena
import bbarena.model.event.Event
import bbarena.model.team.Player

abstract class RosterEvent(playerId: Long) : Event() {
    var playerId: Long = 0
        protected set

    init {
        this.playerId = playerId
    }

    fun getPlayer(arena: Arena): Player? {
        return arena.playerManager.getPlayer(playerId)
    }

    companion object {

        private val serialVersionUID = 8447866046452336969L
    }

}