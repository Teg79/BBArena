package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Coordinate
import bbarena.model.team.Player
import bbarena.model.util.Concat

class PutPlayerInDugoutEvent(playerId: Long, dugoutRoom: String) : PlayerEvent(playerId) {

    private var _coach: Int? = null
    private var _player: Player? = null
    val dugoutRoom: String? = null
    private var _fromPitch: Coordinate? = null
    private var _fromDugout: DugoutRoom? = null

    init {
        this.dugoutRoom = dugoutRoom
    }

    public override fun doEvent(arena: Arena) {
        _player = getPlayer(arena)
        _coach = arena.teams.indexOf(_player!!.team)
        val to = arena.pitch.getDugout(_player!!.team)!!.getRoom(dugoutRoom)

        val pitch = arena.pitch
        if (_player!!.isInDugout) {
            val dugout = pitch.getDugout(_player!!.team)
            _fromDugout = dugout!!.getRoom(_player)
            _fromPitch = null
        } else {
            val square = _player!!.square
            _fromPitch = square.coords
            _fromDugout = null
        }
        pitch.putPlayer(_player, to)
    }

    public override fun undoEvent(arena: Arena) {
        val pitch = arena.pitch
        if (_fromPitch != null) {
            pitch.putPlayer(_player, _fromPitch)
        } else {
            pitch.putPlayer(_player, _fromDugout)
        }
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("playerId", getPlayerId()),
                Pair("dugoutRoom", dugoutRoom))
    }

    companion object {

        private val serialVersionUID = -3430391401528941474L
    }

}
