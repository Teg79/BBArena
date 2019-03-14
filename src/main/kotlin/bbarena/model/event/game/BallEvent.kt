package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.event.Event
import bbarena.model.pitch.Ball

abstract class BallEvent(val ballId: Int = 0) : Event() {

    fun getBall(arena: Arena): Ball {
        return arena.pitch.getBall(ballId)
    }

}