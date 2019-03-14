package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Coordinate
import bbarena.model.pitch.Ball
import bbarena.model.team.Player
import bbarena.model.util.Concat

class RemoveBallEvent(ballId: Int) : BallEvent(ballId) {

    private var _ball: Ball? = null
    private var _fromSquare: Coordinate? = null
    private var _fromPlayer: Player? = null

    public override fun doEvent(arena: Arena) {
        _ball = getBall(arena)
        _fromPlayer = _ball!!.owner
        if (_ball!!.square != null) {
            _fromSquare = _ball!!.square.coords
        }

        val pitch = arena.pitch
        pitch.ballRemove(_ball)
    }

    public override fun undoEvent(arena: Arena) {
        val pitch = arena.pitch

        if (_fromPlayer != null) {
            pitch.ballPickUp(_ball, _fromPlayer)
        } else if (_fromSquare != null) {
            pitch.ballKickOff(_ball, _fromSquare)
        }
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("ballId", ballId))
    }

    companion object {

        private val serialVersionUID = 3975130415257010810L
    }

}
