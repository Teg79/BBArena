package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Coordinate
import bbarena.model.util.Concat

class PutPlayerInPitchEvent(playerId: Long, toX: Int, toY: Int) : PlayerEvent(playerId) {

    var destination: Coordinate? = null
    private var _fromDugout: DugoutRoom? = null
    private var _fromSquare: Coordinate? = null

    init {
        destination = Coordinate(toX, toY)
    }

    public override fun doEvent(arena: Arena) {
        val player = getPlayer(arena)
        val pitch = arena.pitch

        if (player.isInDugout) {
            val dugout = pitch.getDugout(player.team)
            _fromDugout = dugout!!.getRoom(player)
        } else if (player.square != null) {
            _fromSquare = player.square.coords
        }
        pitch.putPlayer(player, destination)
    }

    public override fun undoEvent(arena: Arena) {
        val pitch = arena.pitch
        val player = getPlayer(arena)

        if (_fromDugout != null) {
            pitch.putPlayer(player, _fromDugout)
        } else if (_fromSquare != null) {
            pitch.putPlayer(player, _fromSquare)
        }
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("playerId", getPlayerId()),
                Pair("to", destination))
    }

    companion object {

        private val serialVersionUID = 4021761692648543307L
    }

}
