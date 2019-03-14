package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Match
import java.util.*

class MatchStatusChangeEvent(val matchStatus: Match.Status) : GameEvent() {

    private var prevMatchStatus: Match.Status? = null

    override fun doEvent(arena: Arena) {
        prevMatchStatus = arena.match.status
        arena.match.status = matchStatus

        // TODO: move in event listener
        if (Match.Status.STARTING == matchStatus) {
            arena.match.start = Date()
        } else if (Match.Status.FINISHED == matchStatus) {
            arena.match.end = Date()
        }
    }

    override fun undoEvent(arena: Arena) {
        arena.match.status = prevMatchStatus ?: Match.Status.STARTING
    }

    override fun getString(): String {
        val res = StringBuilder()
        res.append(javaClass.simpleName)
                .append("[match:")
                .append(matchStatus)
                .append("]")
        return res.toString()
    }
}
