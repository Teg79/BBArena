package bbarena.model

import bbarena.model.pitch.Pitch
import bbarena.model.pitch.SQUARE_WIDTH
import bbarena.model.pitch.Square
import bbarena.model.pitch.Square.SquareType
import bbarena.model.team.Player
import bbarena.model.team.Team
import java.util.*

val RULER_WIDTH = 4.25

val RULER_WIDTH_RATIO = RULER_WIDTH / SQUARE_WIDTH

/**
 * This class is responsible to calculate pass ranges and available interception
 * zones. Double precision used for calculation, not as perfect as with
 * BigDecimal but faster.
 *
 */
data class RangeRuler(val pitch: Pitch) {

    enum class Range : Choice {
        NO_RANGE, QUICK, SHORT, LONG, BOMB;

        val symbol: Char
            get() {
                var res = 'O'
                if (this == NO_RANGE) {
                    res = 'N'
                } else if (this == QUICK) {
                    res = 'Q'
                } else if (this == SHORT) {
                    res = 'S'
                } else if (this == LONG) {
                    res = 'L'
                } else if (this == BOMB) {
                    res = 'B'
                }
                return res
            }
    }

    fun getRange(from: Coordinate, to: Coordinate): Range {
        var res = Range.NO_RANGE

        var x = Math.abs(from.x - to.x)
        var y = Math.abs(from.y - to.y)

        if (y > x) {
            val z = x
            x = y
            y = z
        }

        if (x > 0 || y > 0) {
            if (isQuick(x, y)) {
                res = Range.QUICK
            } else if (isShort(x, y)) {
                res = Range.SHORT
            } else if (isLong(x, y)) {
                res = Range.LONG
            } else if (isBomb(x, y)) {
                res = Range.BOMB
            }
        }

        return res
    }

    /**
     * Calculate all players available for an interception
     *
     * @param from
     * Thrower coordinate
     * @param to
     * Catcher coordinate
     * @return All players that can try an interception
     */
    fun getInterceptionPlayers(from: Coordinate, to: Coordinate): List<Player> {
        return getInterceptionOpponents(from, to, null)
    }

    /**
     * Calculate all opponent players available for an interception
     *
     * @param from
     * Thrower coordinate
     * @param to
     * Catcher coordinate
     * @param throwingTeam
     * Team that is throwing the ball, only players of opponent team
     * will be returned
     * @return All opponent players that can try an interception
     */
    fun getInterceptionOpponents(from: Coordinate,
                                 to: Coordinate, throwingTeam: Team?): List<Player> {
        val res = ArrayList<Player>()

        val dugouts = pitch.dugouts
        for (dugout in dugouts) {

            val team = dugout.team
            if (team !== throwingTeam) {

                val players = team.players
                for (p in players) {

                    val square = p.square
                    if (square != null) {
                        val inter = square.coords

                        if (isInterceptable(from, to, inter)) {
                            res.add(p)
                        }
                    }
                }
            }
        }

        return res
    }

    /**
     * Calculate all available squares for an interception
     *
     * @param from
     * Thrower coordinate
     * @param to
     * Catcher coordinate
     * @param throwingTeam
     * Team that is throwing the ball, only players of opponent team
     * will be returned
     * @return All opponent players that can try an interception
     */
    fun getInterceptionSquares(from: Coordinate, to: Coordinate): List<Square> {
        val res = ArrayList<Square>()

        for (x in 0 until pitch!!.width) {
            for (y in 0 until pitch.height) {
                val inter = Coordinate(x, y)
                if (isInterceptable(from, to, inter)) {
                    val s = pitch.getSquare(inter)
                    if (s!!.type != SquareType.OUT) {
                        res.add(s)
                    }
                }
            }
        }

        return res
    }

    /**
     * Seach for players within a range from the interception zones. No filter
     * applied, to get only opponent players try getOpponentsInInterceptionRange
     *
     * @param from
     * Thrower coordinates
     * @param to
     * Catcher coordinates
     * @param squareRange
     * Range for the search
     * @return All the players within the range from the interception zones
     */
    fun getPlayersInInterceptionRange(from: Coordinate,
                                      to: Coordinate, squareRange: Int): Set<Player> {
        return getOpponentsInInterceptionRange(from, to, squareRange, null)
    }

    /**
     * Seach for opponent players within a range from the interception zones.
     *
     * @param from
     * Thrower coordinates
     * @param to
     * Catcher coordinates
     * @param squareRange
     * Range for the search
     * @return All the players within the range from the interception zones
     */
    fun getOpponentsInInterceptionRange(from: Coordinate,
                                        to: Coordinate, squareRange: Int, throwingTeam: Team?): Set<Player> {
        val res = HashSet<Player>()

        val interZones = getInterceptionSquares(from, to)
        for (square in interZones) {
            val xy = square.coords
            val players = pitch!!.getPlayersInRange(xy, squareRange)

            for (player in players) {
                if (player.team !== throwingTeam) {
                    res.add(player)
                }
            }
        }

        return res
    }

    /**
     * Seach for squares within a range from the interception zones.
     *
     * @param from
     * Thrower coordinates
     * @param to
     * Catcher coordinates
     * @param squareRange
     * Range for the search
     * @return All the Squares within the range from the interception zones
     */
    fun getSquaresInInterceptionRange(from: Coordinate,
                                      to: Coordinate, squareRange: Int, throwingTeam: Team): Set<Square> {
        val res = HashSet<Square>()

        val interZones = getInterceptionSquares(from, to)
        for (square in interZones) {
            val xy = square.coords
            val squares = pitch!!.getSquaresInRange(xy, squareRange)

            res.addAll(squares)
        }

        return res
    }

    /**
     * Determines if a coordinate is a valid interception coordinate
     *
     * @param from
     * Thrower coordinates
     * @param to
     * Catcher coordinates
     * @param inter
     * Interception coordinates
     * @return true if the interception coordinate is valid, false otherwise
     */
    private fun isInterceptable(from: Coordinate, to: Coordinate,
                                inter: Coordinate): Boolean {
        var res = false

        val a = getLength(from, to)
        val b = getLength(from, inter)
        val c = getLength(inter, to)

        if (b < a && c < a
                && getDistanceFromThrowLine(a, b, c) <= RULER_WIDTH_RATIO) {
            res = true
        }

        return res
    }

    /**
     * Given the 3 distances between a, b, c calculates the distance from ab
     * line to c
     *
     * @param throwLength
     * AB
     * @param fromThrowerDistance
     * AC
     * @param fromCatcherDistance
     * CB
     * @return AB to C distance
     */
    private fun getDistanceFromThrowLine(throwLength: Double,
                                         fromThrowerDistance: Double, fromCatcherDistance: Double): Double {
        var res = 0.0

        val a = throwLength
        val b = fromThrowerDistance
        val c = fromCatcherDistance

        val s = (a + b + c) / 2

        // If the difference between half-perimeter and the throw length is
        // lower than 0.01 square than we assume that the intercepting player is
        // over the throw path
        if (Math.abs(s - a) > 0.01) {
            val area = Math.sqrt(s * (s - a) * (s - b) * (s - c))
            res = area * 2 / a
        }

        return res
    }

    private fun getLength(from: Coordinate, to: Coordinate): Double {
        var res = 0.0

        val x = Math.abs(from.x - to.x)
        val y = Math.abs(from.y - to.y)

        res = Math.sqrt((x * x + y * y).toDouble())

        return res
    }

    private fun isQuick(x: Int, y: Int): Boolean {
        var res = false

        if (x < 4 && y < 2 || x < 3 && y < 3) {
            res = true
        }

        return res
    }

    private fun isShort(x: Int, y: Int): Boolean {
        var res = false

        if (x < 7 && y < 4 || x < 6 && y < 5) {
            res = true
        }

        return res
    }

    private fun isLong(x: Int, y: Int): Boolean {
        var res = false

        if (x < 11 && y < 3 || x < 10 && y < 5 || x < 9 && y < 7
                || x < 8 && y < 8) {
            res = true
        }

        return res
    }

    private fun isBomb(x: Int, y: Int): Boolean {
        var res = false

        if (x < 14 && y < 2 || x < 13 && y < 5 || x < 12 && y < 7
                || x < 11 && y < 9 || x < 10 && y < 10) {
            res = true
        }

        return res
    }

}
