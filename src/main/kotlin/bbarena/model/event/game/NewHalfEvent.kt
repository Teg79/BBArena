package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.TurnMarker

class NewHalfEvent() : GameEvent() {

    private val oldMarkers = mutableListOf<TurnMarker>()

    override fun doEvent(arena: Arena) {
        arena.half += 1

        // TODO: move turnmarker reset to a different event
        val turnMarkers = arena.turnMarkers
        for (turnMarker in turnMarkers) {
            val oldMarker = TurnMarker(turnMarker.team)
            oldMarker.rerolls = turnMarker.rerolls
            oldMarker.isUsedReroll = turnMarker.isUsedReroll
            oldMarker.turn = turnMarker.turn
            oldMarker.status = turnMarker.status
            oldMarkers.add(oldMarker)

            turnMarker.turn = 0
            turnMarker.isUsedReroll = false
            turnMarker.rerolls = turnMarker.team.reRolls
        }
    }

    override fun undoEvent(arena: Arena) {
        arena.half -= 1

        for (i in 0 until arena.turnMarkers.size) {
            val oldMarker = oldMarkers[i]
            val turnMarker = arena.turnMarkers[i]
            turnMarker.status = oldMarker.status
            turnMarker.turn = oldMarker.turn
            turnMarker.isUsedReroll = oldMarker.isUsedReroll
            turnMarker.rerolls = oldMarker.rerolls
        }
    }

}
