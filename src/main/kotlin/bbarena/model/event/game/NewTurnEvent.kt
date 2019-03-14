package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.TurnMarker
import bbarena.model.util.Concat

class NewTurnEvent : GameEvent() {

    var coachPos: Int = 0
    private var rerolls: Int = 0
    private var turn: Int = 0
    private var usedReroll: Boolean = false

    override fun doEvent(arena: Arena) {
        val turnMarker = arena.turnMarkers[coachPos]
        turnMarker.status = TurnMarker.TurnStatus.PLAYING
        rerolls = turnMarker.rerolls
        turn = turnMarker.turn
        usedReroll = turnMarker.isUsedReroll

        turnMarker.turn = turn + 1
        turnMarker.isUsedReroll = false
    }

    override fun undoEvent(arena: Arena) {
        val turnMarker = arena.turnMarkers[coachPos]
        turnMarker.status = TurnMarker.TurnStatus.DONE
        turnMarker.isUsedReroll = usedReroll
        turnMarker.turn = turn
        turnMarker.rerolls = rerolls
    }

    override fun getString(): String {
        return Concat.buildLog(javaClass,
                Pair("coach", coachPos),
                Pair("turn", turn + 1))
    }

}
