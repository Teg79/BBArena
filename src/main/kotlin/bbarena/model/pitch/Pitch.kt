package bbarena.model.pitch

import bbarena.model.Coordinate
import bbarena.model.Direction
import bbarena.model.RangeRuler
import bbarena.model.exception.PitchException
import bbarena.model.pitch.BallMove.BallMoveType
import bbarena.model.pitch.Square.SquareType
import bbarena.model.team.Player
import bbarena.model.team.Team
import java.util.*

/**
 * The Pitch is the class to represent any BB Pitch. It is possible to
 * create a PitchFactory for classic BB Pitch, DungeonBowl, DeathBowl
 * and so on. This class is the only responsible of the consistency of the pitch
 * model, each change in players location, ball location and so on must be done
 * with this class and not modifing the single Square or Player objects.
 *
 */
class Pitch
/**
 * Constructor for a general pitch The pitch MUST be rectangular or a
 * square, each row must have the same number of squares. Fill every square
 * out of bounds with a SquareType.OUT
 *
 * @param name   Name of the pitch
 * @param width  Width of the pitch
 * @param height Height of the pitch
 */
(val pitchName: String, width: Int, height: Int) {
    private val squares: Array<Array<Square>>
    private val squareSet: MutableSet<Square>
    val dugouts: MutableList<Dugout>
    private val balls: MutableList<Ball>
    private val players: MutableSet<Player>

    val width: Int
        get() = squares.size

    val height: Int
        get() = squares[0].size

    /**
     * Returns the first (default one) ball.
     *
     * @return Default Ball, null if there are no balls.
     */
    val ball: Ball
        get() = getBall(0)

    val rangeRuler: RangeRuler
        get() = RangeRuler(this)

    init {
        squareSet = LinkedHashSet()
        squares = Array(width) { x ->
            (0 until height).map { y ->
                val square = Square(this, Coordinate(x, y), SquareType.OUT, null)
                squareSet.add(square)
                square
            }.toTypedArray()
        }
        dugouts = mutableListOf()
        balls = mutableListOf()
        players = linkedSetOf()
    }

    fun addDugout(dugout: Dugout?): Boolean {
        var res = false
        if (dugout != null) {
            res = dugouts.add(dugout)
        }
        return res
    }

    /**
     * Return the Square in a Coordinate
     *
     * @param xy Square Coordinate from [0,0]
     * @return The Square in the Coordinate
     */
    fun getSquare(xy: Coordinate): Square? {
        var res: Square? = null

        val x = xy.x
        val y = xy.y

        if (x >= 0 && squares.size > x && y >= 0 && squares[x].size > y) {
            res = squares[x][y]
        }

        return res
    }

    fun getTeamSquares(team: Team): Set<Square> =
            squareSet.filter { square -> square.owner == team }.toSet()

    fun getEmptyTeamSquares(team: Team): Set<Square> =
        squareSet.filter { square -> square.owner == team && !square.hasPlayer() }.toSet()

    fun getBall(ballId: Int): Ball = balls[ballId]

    fun addBall(ball: Ball): Int {
        val res = balls.size

        ball.id = res
        balls.add(ball)

        return res
    }

    /**
     * Counts the number of active Tackle Zones in a Square
     *
     * @param xy   Square Coordinate
     * @param team Team of the player in the square
     * @return the number of Tackle Zones
     */
    fun getOpponentTZCount(xy: Coordinate, team: Team): Int = Direction.values()
            .map { d -> getNextSquare(xy, d) }
            .count { s ->
                s != null && s.type != SquareType.OUT && s.hasPlayer()
                    && s.player?.team != team && true == s.player?.hasTZ() }

    /**
     * Search the next Square in a specified Direction
     *
     * @param xy        Start Coordinate
     * @param direction Direction
     * @return The Square near the start Coordinate
     */
    fun getNextSquare(xy: Coordinate, direction: Direction): Square? {
        return getSquare(xy.getNext(direction))
    }

    fun getAvailableDirections(xy: Coordinate): Set<Direction> {
        val res = LinkedHashSet<Direction>()

        for (direction in Direction.values()) {
            val nextSquare = getNextSquare(xy, direction)
            if (nextSquare != null && !nextSquare.hasPlayer() && nextSquare.type != SquareType.OUT) {
                res.add(direction)
            }
        }

        return res
    }

    /**
     * Move a player to a Direction. If the Player will move out of the pitch a
     * PitchException will be thrown (this doesn't apply for SquareType.OUT)
     *
     * @param player    Player to move
     * @param direction Direction of the movement
     * @param squares   Number of squares
     * @return The new Square occupied by the Player
     */
    @JvmOverloads
    fun movePlayer(player: Player, direction: Direction, squares: Int = 1): Square {
        if (squares < 0) {
            throw PitchException(
                    "Cannot move a negative number of squares!")
        }
        val res: Square
        val origin = player.square
        if (origin != null) {
            var pc = origin.coords
            var next = origin
            for (i in 0 until squares) {
                next = getNextSquare(pc, direction)

                if (next != null && next.type != SquareType.OUT) {
                    pc = next.coords
                } else {
                    throw PitchException("Player moved out of the pitch!")
                }
            }

            origin.removePlayer()
            next!!.player = player
            player.square = next
            res = next

        } else {
            throw PitchException("Player is not on the pitch!")
        }
        return res
    }

    /**
     * Scatter the Ball in a Direction by one square
     *
     * @param ball      Ball to scatter
     * @param direction Direction
     * @return The new Square occupied by the Ball
     */
    fun ballScatter(ball: Ball, direction: Direction): Square {
        return moveBall(ball, direction, 1, BallMoveType.SCATTER)
    }

    /**
     * Pass the Ball to a destination
     *
     * @param ball        Ball to pass
     * @param destination Destination of the throw
     * @return The new Square occupied by the Ball
     */
    fun ballPass(ball: Ball, destination: Coordinate): Square {
        return putBall(ball, destination, BallMoveType.PASS)
    }

    /**
     * Kick Off the Ball to a destination
     *
     * @param ball        Ball to kick
     * @param destination Destination of the kick
     * @return The new Square occupied by the Ball
     */
    fun ballKickOff(ball: Ball, destination: Coordinate): Square {
        return putBall(ball, destination, BallMoveType.KICK_OFF)
    }

    /**
     * Throw-In the Ball to a destination
     *
     * @param ball      Ball to kick
     * @param direction Direction of the throw-in
     * @param range     Number of squares
     * @return The new Square occupied by the Ball
     */
    fun ballThrowIn(ball: Ball, direction: Direction, range: Int): Square {
        return moveBall(ball, direction, range, BallMoveType.THROW_IN)
    }

    /**
     * Assign the Ball to a Player via Pick Up
     *
     * @param ball   Ball to Pick Up
     * @param player Player that gets the Ball
     */
    fun ballPickUp(ball: Ball, player: Player) {
        setBallOwner(ball, player, BallMoveType.PICK_UP)
    }

    /**
     * Deprive a Player the Ball. The Ball will be assigned to the Player
     * Square, waiting to be scattered
     *
     * @param ball   Ball
     * @param player Player to deprive
     * @return The new Square of the Ball
     */
    fun ballLose(ball: Ball, player: Player): Square = putBall(ball, player.square!!.coords, BallMoveType.LOSE)

    /**
     * Assign the Ball to a Player via Catch
     *
     * @param ball   Ball to Catch
     * @param player Player that gets the Ball
     */
    fun ballCatch(ball: Ball, player: Player) = setBallOwner(ball, player, BallMoveType.CATCH)

    /**
     * Move a Ball to a Direction. If the Ball will move out of the pitch a
     * PitchException will be thrown (this doesn't apply for SquareType.OUT)
     *
     * @param ball      Ball to move
     * @param direction Direction of the movement
     * @param squares   Number of squares
     * @param moveType  Type of the Ball movement, default if range == 1 or == 0 is
     * SCATTER, if range > 1 is THROW_IN
     * @return The new Square occupied by the Ball
     */
    fun moveBall(ball: Ball, direction: Direction, squares: Int,
                 moveType: BallMoveType): Square {
        if (squares < 0) {
            throw PitchException(
                    "Cannot move a negative number of squares!")
        }

        val res: Square
        val origin = ball.square
        if (origin != null) {
            var bc = origin.coords
            var next = origin
            for (i in 0 until squares) {
                next = getNextSquare(bc, direction)

                if (next != null && next.type != SquareType.OUT) {
                    bc = next.coords
                } else {
                    throw PitchException("Ball moved out of the pitch!")
                }
            }

            removeBall(ball)
            next!!.ball = ball
            ball.setSquare(next, moveType)
            res = next

        } else {
            throw PitchException("Ball is not on the pitch!")
        }
        return res
    }

    fun getDestination(coordinate: Coordinate, direction: Direction, squares: Int): SquareDestination {
        if (squares < 0) {
            throw PitchException(
                    "Cannot move a negative number of squares!")
        }

        var out = false
        var destination = coordinate
        var i: Int?
        i = 0
        while (i < squares) {
            val nextSquare = getNextSquare(destination, direction)

            if (nextSquare != null && nextSquare.type != SquareType.OUT) {
                destination = nextSquare.coords
            } else {
                out = true
                break
            }
            i++
        }

        return SquareDestination(destination, out, i)
    }

    /**
     * Assign the Ball owner
     *
     * @param ball     Ball to assign
     * @param player   Player that will have the Ball
     * @param moveType Type of the Ball movement, default is PICK_UP
     */
    private fun setBallOwner(ball: Ball, player: Player, moveType: BallMoveType?) {
        var moveType = moveType
        if (player.square == null || player.isInDugout) {
            throw PitchException("Player is not on the pitch!")
        } else if (player.hasBall()) {
            throw PitchException("Player has already a Ball!")
        }

        if (moveType == null) {
            moveType = BallMoveType.PICK_UP
        }

        removeBall(ball)
        ball.setOwner(player, moveType)
        player.ball = ball
    }

    /**
     * Put a Player in a specified Coordinate on the pitch. If the Player is in
     * the Dugout it will be removed.
     *
     * @param player Player to put in the pitch
     * @param xy     Coordinate where to put the Player
     * @return The new Square for the Player
     */
    fun putPlayer(player: Player, xy: Coordinate): Square {
        val res = getSquare(xy)
        if (res == null || res.type == SquareType.OUT) {
            throw PitchException("Square " + xy.toString()
                    + " is out of the pitch!")
        }

        if (player.isInDugout) {
            val dugout = player.team?.let { getDugout(it) }
            dugout?.removePlayer(player)
            players.add(player)
        }

        val origin = player.square
        if (origin != null) {
            origin.removePlayer()
            player.square = null
        }

        res.player = player
        player.square = res

        return res
    }

    /**
     * Put a Ball in a specified Coordinate on the pitch.
     *
     * @param ball     Ball to put in the pitch
     * @param xy       Coordinate where to put the Ball
     * @param moveType Type of the Ball movement, default is KICK_OFF
     * @return The new Square for the Ball
     */
    private fun putBall(ball: Ball, xy: Coordinate, moveType: BallMoveType?): Square {
        var moveType = moveType
        val res = getSquare(xy)
        if (res == null || res.type == SquareType.OUT) {
            throw PitchException("Square " + xy.toString()
                    + " is out of the pitch!")
        }

        if (moveType == null) {
            moveType = BallMoveType.KICK_OFF
        }

        removeBall(ball)
        ball.setSquare(res, moveType)
        res.ball = ball

        return res
    }

    /**
     * Remove the Ball from the pitch
     *
     * @param ball Ball to remove
     */
    private fun removeBall(ball: Ball) {
        val origin = ball.square
        origin?.removeBall()
        val player = ball.owner
        player?.removeBall()
    }

    fun ballRemove(ball: Ball) {
        removeBall(ball)
        ball.remove(BallMoveType.OUT)
    }

    /**
     * Put a Player in a specified Room of his Team Dugout. If the player is in
     * a Square it will be removed.
     *
     * @param player Player to put in Dugout
     * @param room   The DugoutRoom
     * @return true if the player is successfully added, false otherwise
     */
    fun putPlayer(player: Player, room: DugoutRoom): Boolean {
        val res: Boolean

        val dugout = player.team?.let { getDugout(it) }
        res = dugout?.addPlayer(room, player) ?: false
        if (res) {
            val square = player.square
            if (square != null) {
                square.removePlayer()
                player.square = null
                players.remove(player)
            }
        }

        return res
    }

    fun getDugout(team: Team): Dugout? {
        var res: Dugout? = null
        for (dugout in dugouts) {
            if (dugout.team == team) {
                res = dugout
                break
            }
        }
        return res
    }

    /**
     * Given a coordinate and a range return every square that is in the range
     * from that coordinate.
     *
     * @param ref         Coordinate from where will be calculated the range
     * @param squareRange Number of squares for the range
     * @return The list of Squares within range
     */
    fun getSquaresInRange(ref: Coordinate, squareRange: Int): List<Square> {
        val res = ArrayList<Square>()

        val x1 = Math.max(0, ref.x - squareRange)
        val x2 = Math.min(squares.size - 1, ref.x + squareRange)
        val y1 = Math.max(0, ref.y - squareRange)
        val y2 = Math.min(squares[x2].size - 1, ref.y + squareRange)

        for (x in x1..x2) {
            for (y in y1..y2) {
                val square = getSquare(Coordinate(x, y))
                if (square != null && square.type != SquareType.OUT) {
                    res.add(square)
                }
            }
        }

        return res
    }

    /**
     * Given a coordinate and a range return every player that is in the range
     * from that coordinate. This will be useful when testing which players are,
     * for example, 3 squares away from another player. Here there are no
     * controls over player skills or if the players have active tackle zones.
     *
     * @param ref         Coordinate from where will be calculated the range
     * @param squareRange Number of squares for the range
     * @return The list of Players within range
     */
    fun getPlayersInRange(ref: Coordinate, squareRange: Int): List<Player> =
            getSquaresInRange(ref, squareRange).mapNotNull { it.player }

    internal fun setSquare(xy: Coordinate, type: SquareType, teamOwner: Team? = null) {
        val s = Square(this, xy, type, teamOwner)
        val oldSquare = squares[xy.x][xy.y]
        squareSet.remove(oldSquare)
        squareSet.add(s)
        squares[xy.x][xy.y] = s
    }

}
