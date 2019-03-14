package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.util.Concat

class FansEvent(val team: Int, val fans: Int = 0) : GameEvent() {

    override fun doEvent(arena: Arena) {
        arena.getScoreBoard(team).fans += fans
    }

    override fun undoEvent(arena: Arena) {
        arena.getScoreBoard(team).fans -= fans
    }

    override val string: String
        get() = Concat.buildLog(javaClass,
                Pair("team", team),
                Pair("fans", fans))

}
