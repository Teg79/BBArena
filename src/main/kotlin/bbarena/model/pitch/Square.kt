package bbarena.model.pitch

import bbarena.model.Choice
import bbarena.model.Coordinate
import bbarena.model.exception.SquareAlreadyOccupiedException
import bbarena.model.team.Player
import bbarena.model.team.Team
import bbarena.model.util.Concat

val SQUARE_WIDTH = 2.90

data class Square (val pitch: Pitch, val coords: Coordinate, val type: SquareType = SquareType.NORMAL, val owner: Team?) : Choice {

    var ball: Ball? = null
        /**
         * Set the ball in the Square if the Square has no ball
         *
         * @param ball Ball to place in the Square, if null the ball in
         * the Square (if exists) will be removed
         * @throws SquareAlreadyOccupiedException if the Square already has a Ball
         */
    set(ball) {
        if (ball == null) {
            removeBall()
        } else {
            if (field != null) {
                throw SquareAlreadyOccupiedException("The square ${this.coords} is already occupied by another ball!")
            } else {
                field = ball
            }
        }
    }

    var player: Player? = null
        /**
         * Set the player in the Square if the Square is empty
         *
         * @param player Player to place in the Square, if null the current Player in
         * the Square (if exists) will be removed
         * @throws SquareAlreadyOccupiedException if the Square already has a Player
         */
    set(value) {
        if (value == null) {
            removePlayer()
        } else {
            if (field != null) {
                throw SquareAlreadyOccupiedException("The square ${this.coords} is already occupied by another player!")
            } else {
                field = value
            }
        }
    }

    var special: String? = null

    val isSpecial: Boolean
        get() = special != null

    enum class SquareType {
        NORMAL, RIGHT_WIDE_ZONE, LEFT_WIDE_ZONE, LOS, END_ZONE, OUT;

        override fun toString(): String {
            return when (this) {
                NORMAL -> "N"
                RIGHT_WIDE_ZONE, LEFT_WIDE_ZONE -> "W"
                LOS -> "L"
                END_ZONE -> "E"
                OUT -> "O"
            }
        }
    }

    fun hasBall(): Boolean {
        return this.ball != null
    }

    fun hasPlayer(): Boolean {
        return this.player != null
    }

    fun removePlayer() {
        this.player = null
    }

    fun removeBall() {
        this.ball = null
    }

    override fun toString(): String {
        return Concat.buildLog(javaClass,
                Pair("xy", coords))
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || other !is Square) return false

        val square = other as Square?

        return coords == square?.coords
    }

    override fun hashCode(): Int {
        return coords.hashCode()
    }

}
