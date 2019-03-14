package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Direction
import bbarena.model.util.Concat

class MovePlayerEvent (playerId: Long, val direction: Direction, val range: Int = 1) : PlayerEvent(playerId) {

    override fun doEvent(arena: Arena) {
        val player = getPlayer(arena)

        val pitch = arena.pitch
        pitch.movePlayer(player, direction, range)
    }

    override fun undoEvent(arena: Arena) {
        val pitch = arena.pitch
        val player = getPlayer(arena)
        pitch.movePlayer(player, direction.inverse(), range)
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("playerId", playerId),
                Pair("direction", direction),
                Pair("range", range))
    }

}
