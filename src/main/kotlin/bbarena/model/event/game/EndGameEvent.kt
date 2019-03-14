package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.Match
import bbarena.model.util.Concat

class EndGameEvent(var prevStatus: Match.Status = Match.Status.STARTING) : GameEvent() {

    override val string: String
        get() = Concat.buildLog(javaClass)

    override fun doEvent(arena: Arena) {
        prevStatus = arena.match?.status!!
        arena.match.status = Match.Status.ENDING
    }

    override fun undoEvent(arena: Arena) {
        arena.match?.status = prevStatus
    }
}
