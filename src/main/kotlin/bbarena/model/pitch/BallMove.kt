package bbarena.model.pitch

import bbarena.model.team.Player

import java.io.Serializable

data class BallMove(val ball: Ball, val moveType: BallMoveType) : Serializable {

    val fromSquare: Square? = ball.square

    val fromPlayer: Player? = ball.owner

    val isFromPlayer: Boolean
        get() = this.fromPlayer != null

    val isFromSquare: Boolean
        get() = this.fromSquare != null

    enum class BallMoveType {
        SCATTER, PASS, KICK_OFF, THROW_IN, PICK_UP, CATCH, OUT, LOSE
    }

}
