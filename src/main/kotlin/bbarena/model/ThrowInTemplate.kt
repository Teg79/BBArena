package bbarena.model

import bbarena.model.pitch.Square.SquareType

class ThrowInTemplate(val arena: Arena) {

    /**
     * Determines the Direction of a Throw In
     *
     * @param direction From 1 to 6
     * @param xy Last Square crossed by the ball
     * @return The Direction of the Throw In
     */
    fun getThrowInDirection(direction: Int, xy: Coordinate): Direction {
        val res: Direction
        val dir = halfDirection(direction)

        val n = checkBorder(xy, Direction.N)
        val e = checkBorder(xy, Direction.E)
        val s = checkBorder(xy, Direction.S)
        val w = checkBorder(xy, Direction.W)

        if (n && !e && !s && !w) { // North border
            res = Direction.N.next(2 + dir)
        } else if (!n && e && !s && !w) { // East border
            res = Direction.E.next(2 + dir)
        } else if (!n && !e && s && !w) { // South border
            res = Direction.S.next(2 + dir)
        } else if (!n && !e && !s && w) { // West border
            res = Direction.W.next(2 + dir)
        } else if (n && e && !s && !w) { // North East corner
            res = Direction.NE.next(2 + dir)
        } else if (n && !e && !s && w) { // North West corner
            res = Direction.NW.next(2 + dir)
        } else if (!n && e && s && !w) { // South East corner
            res = Direction.SE.next(2 + dir)
        } else if (!n && !e && s && w) { // South West corner
            res = Direction.SW.next(2 + dir)
        } else {
            throw RuntimeException("Unexpected direction $direction for coordinate $xy")
        }

        return res
    }

    private fun checkBorder(xy: Coordinate, direction: Direction): Boolean {
        val s = arena.pitch.getNextSquare(xy, direction)
        return s == null || s.type == SquareType.OUT
    }

    private fun halfDirection(direction: Int): Int {
        return Math.ceil(direction.toDouble() / 2).toInt()
    }

}
