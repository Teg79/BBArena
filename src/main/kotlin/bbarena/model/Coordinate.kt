/**
 *
 */
package bbarena.model

import java.io.Serializable

/**
 * @author f.bellentani
 */
class Coordinate : Cloneable, Serializable {

    var x = -1
    var y = -1

    constructor() {

    }

    constructor(x: Int, y: Int) {
        this.x = x
        this.y = y
    }

    fun getNext(direction: Direction): Coordinate {
        var x = this.x
        var y = this.y

        if (direction == Direction.NW) {
            y--
            x--
        } else if (direction == Direction.N) {
            y--
        } else if (direction == Direction.NE) {
            y--
            x++
        } else if (direction == Direction.W) {
            x--
        } else if (direction == Direction.E) {
            x++
        } else if (direction == Direction.SW) {
            y++
            x--
        } else if (direction == Direction.S) {
            y++
        } else if (direction == Direction.SE) {
            y++
            x++
        }

        return Coordinate(x, y)
    }

    fun sqDistanceFrom(c: Coordinate): Int {
        val dx = x - c.x
        val dy = y - c.y
        return dx * dx + dy * dy
    }

    fun distanceFrom(c: Coordinate): Float {
        return Math.sqrt(sqDistanceFrom(c).toDouble()).toFloat()
    }

    fun roundedDistanceFrom(c: Coordinate): Int {
        return distanceFrom(c).toInt()
    }

    override fun clone(): Any {
        return Coordinate(x, y)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false

        val that = o as Coordinate?

        return if (x != that!!.x) false else y == that.y

    }

    override fun hashCode(): Int {
        var result = x
        result = 31 * result + y
        return result
    }

    override fun toString(): String {
        return "[$x,$y]"
    }

    companion object {

        private const val serialVersionUID = 834310084349631868L
    }


}
