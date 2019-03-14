package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.util.Concat

class CatchBallEvent(ballId: Int = 0, val playerId: Long, private var failed: Boolean = false) : BallEvent(ballId) {

    override val string: String
        get() = Concat.buildLog(javaClass,
                Pair("ballId", ballId),
                Pair("playerId", playerId),
                Pair("catched", !failed))

    override fun doEvent(arena: Arena) {
        val ball = getBall(arena)
        val player = arena.playerManager.getPlayer(playerId)

        if (!failed) {
            arena.pitch.ballCatch(ball!!, player!!)
        }
    }

    override fun undoEvent(arena: Arena) {
        if (!failed) {
            val pitch = arena.pitch
            val ball = getBall(arena)
            val player = arena.playerManager.getPlayer(playerId)
            pitch.ballLose(ball!!, player!!)
        }
    }

}
