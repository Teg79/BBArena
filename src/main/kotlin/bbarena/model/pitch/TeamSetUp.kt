package bbarena.model.pitch

import bbarena.model.Choice
import bbarena.model.Coordinate
import java.util.*

class TeamSetUp : Choice {

    private val _playersPlacement: MutableMap<Long, Coordinate>

    val setUp: Map<Long, Coordinate>
        get() = _playersPlacement

    init {
        _playersPlacement = HashMap()
    }

    fun placePlayer(playerId: Long, x: Int?, y: Int?) {
        _playersPlacement[playerId] = Coordinate(x!!, y!!)
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        return if (o == null || javaClass != o.javaClass) false else true

    }

    override fun hashCode(): Int {
        return javaClass.hashCode()
    }
}
