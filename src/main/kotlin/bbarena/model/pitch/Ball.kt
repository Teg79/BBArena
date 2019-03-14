package bbarena.model.pitch

import bbarena.model.pitch.BallMove.BallMoveType
import bbarena.model.team.Player
import java.io.Serializable
import java.util.*

/**
 * In different Pitch/RuleSet you can have one ore more Ball in the pitch with different attributes by extending this class
 *
 */
open class Ball(var id: Int = 0) : Observable(), Serializable {

    var owner: Player? = null
        private set
    var square: Square? = null
        private set

    fun setOwner(owner: Player, moveType: BallMoveType) {
        val move = BallMove(this, moveType)
        this.owner = owner
        this.square = null
        setChanged()
        notifyObservers(move)
    }

    fun setSquare(square: Square, moveType: BallMoveType) {
        val move = BallMove(this, moveType)
        this.square = square
        this.owner = null
        setChanged()
        notifyObservers(move)
    }

    fun remove(moveType: BallMoveType) {
        val move = BallMove(this, moveType)
        this.square = null
        this.owner = null
        setChanged()
        notifyObservers(move)
    }

}
