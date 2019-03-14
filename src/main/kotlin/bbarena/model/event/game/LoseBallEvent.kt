package bbarena.model.event.game

import bbarena.model.Arena

class LoseBallEvent(ballId: Int, val playerId: Long) : BallEvent(ballId) {

    override fun doEvent(arena: Arena) {
        val player = arena.playerManager.getPlayer(playerId)
        val ball = getBall(arena)

        arena.pitch.ballLose(ball, player)
    }

    override fun undoEvent(arena: Arena) {
        val pitch = arena.pitch
        pitch.ballPickUp(getBall(arena), arena.playerManager.getPlayer(playerId))
    }

    override fun getString(): String {
        val res = StringBuilder()
        res.append(javaClass.simpleName)
                .append("[ballId:")
                .append(ballId)
                .append(",playerId:")
                .append(playerId)
                .append("]")
        return res.toString()
    }

}
