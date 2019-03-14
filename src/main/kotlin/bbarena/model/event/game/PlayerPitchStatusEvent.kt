package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.team.PlayerPitchStatus
import bbarena.model.util.Concat

class PlayerPitchStatusEvent : PlayerEvent {

    var pitchStatus: PlayerPitchStatus? = null
    private var _oldPitchStatus: PlayerPitchStatus? = null

    constructor(playerId: Long) : super(playerId) {}

    constructor(playerId: Long, pitchStatus: PlayerPitchStatus) : super(playerId) {
        this.pitchStatus = pitchStatus
    }

    public override fun doEvent(arena: Arena) {
        val player = getPlayer(arena)
        _oldPitchStatus = player.pitchStatus
        player.pitchStatus = pitchStatus
    }

    public override fun undoEvent(arena: Arena) {
        val player = getPlayer(arena)
        player.pitchStatus = _oldPitchStatus
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("playerId", getPlayerId()),
                Pair("pitchStatus", pitchStatus))
    }

    companion object {

        private val serialVersionUID = 1790786618254181381L
    }

}
