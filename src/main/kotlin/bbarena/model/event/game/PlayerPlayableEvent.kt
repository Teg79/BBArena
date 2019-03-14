package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.team.Player
import bbarena.model.util.Concat

class PlayerPlayableEvent(playerId: Long, private val _playable: Boolean) : PlayerEvent(playerId) {

    private var _player: Player? = null
    private var _oldPlayable = true

    public override fun doEvent(arena: Arena) {
        _player = getPlayer(arena)
        _oldPlayable = _player!!.isPlayable
        _player!!.isPlayable = _playable
    }

    public override fun undoEvent(arena: Arena) {
        _player!!.isPlayable = _oldPlayable
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("playerId", getPlayerId()),
                Pair("playable", _playable))
    }
}
