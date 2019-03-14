package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Coordinate
import bbarena.model.exception.EventException
import bbarena.model.team.Player
import bbarena.model.util.Concat

class PassBallEvent(ballId: Int, val destination: Coordinate) : BallEvent(ballId) {

    private var oldOwner: Player? = null

    override fun doEvent(arena: Arena) {
        val ball = getBall(arena)

        oldOwner = ball.owner

        arena.pitch.ballPass(ball, destination)
    }

    override fun undoEvent(arena: Arena) {
        val pitch = arena.pitch
        val owner = oldOwner ?: throw EventException("PassBallEvent with no player")
        pitch.ballCatch(getBall(arena), owner)
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("ballId", ballId),
                Pair("playerId", oldOwner?.id ?: -1),
                Pair("to", destination))
    }

}
