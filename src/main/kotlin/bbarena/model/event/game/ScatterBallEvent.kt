package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Coordinate
import bbarena.model.Direction
import bbarena.model.pitch.Ball
import bbarena.model.pitch.BallMove
import bbarena.model.pitch.SquareDestination
import bbarena.model.util.Concat

open class ScatterBallEvent : BallEvent {

    var direction: Direction? = null
    var distance: Int? = 1
    var origin: Coordinate? = null
        private set
    var destination: SquareDestination? = null
        private set
    var type: BallMove.BallMoveType = BallMove.BallMoveType.SCATTER

    constructor(ballId: Int) : super(ballId) {}
    @JvmOverloads
    constructor(ballId: Int, direction: Int, distance: Int = 1) : super(ballId) {
        this.direction = Direction.getDirection(direction)
        this.distance = distance
    }

    fun setBall(ball: Ball) {
        var ball = ball
        ball = ball
    }

    override fun doEvent(arena: Arena) {
        val ball = getBall(arena)

        origin = ball.square.coords
        destination = arena.pitch.getDestination(origin, direction, distance!!)
        arena.pitch.moveBall(ball, direction, destination!!.effectiveDistance!!, type)
    }

    override fun undoEvent(arena: Arena) {
        val ball = getBall(arena)
        arena.pitch.moveBall(ball, direction!!.inverse(), destination!!.effectiveDistance!!, type)
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("ballId", ballId),
                Pair("direction", direction),
                Pair("distance", distance))
    }

    companion object {

        private val serialVersionUID = -6262949311066708660L
    }

}
