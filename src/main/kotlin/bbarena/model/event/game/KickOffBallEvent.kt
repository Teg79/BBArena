package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Coordinate
import bbarena.model.util.Concat

class KickOffBallEvent(ballId: Int, val destination: Coordinate) : BallEvent(ballId) {

    override val string: String
        get() = Concat.buildLog(javaClass,
                Pair("ballId", ballId),
                Pair("to", destination))

    override fun doEvent(arena: Arena) {
        val ball = getBall(arena)

        val pitch = arena.pitch
        pitch.ballKickOff(ball, destination)
    }

    override fun undoEvent(arena: Arena) {
        val ball = getBall(arena)
        val pitch = arena.pitch
        pitch.ballRemove(ball)
    }

}
