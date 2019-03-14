package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.pitch.Ball
import bbarena.model.team.Player
import bbarena.model.util.Concat

class PickUpBallEvent(ballId: Int, playerId: Long) : BallEvent(ballId) {

    val playerId: Long = 0
    private var _ball: Ball? = null
    private var _player: Player? = null
    private var _failed = false

    init {
        this.playerId = playerId
    }

    public override fun doEvent(arena: Arena) {
        _player = arena.playerManager.getPlayer(playerId)
        _ball = getBall(arena)

        if (!_failed) {
            arena.pitch.ballPickUp(_ball, _player)
        }
    }

    public override fun undoEvent(arena: Arena) {
        if (!_failed) {
            val pitch = arena.pitch
            pitch.ballLose(_ball, _player)
        }
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("ballId", ballId),
                Pair("playerId", playerId),
                Pair("catched", !_failed))
    }

    fun setFailed(failed: Boolean) {
        _failed = failed
    }

    companion object {

        private val serialVersionUID = 8477756281960807889L

        val PICK_UP_BALL_ROLL = "PICK_UP_BALL"
    }
}
