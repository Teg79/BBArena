package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.util.Concat

class EndTurnEvent : GameEvent() {

    override val string: String
        get() = Concat.buildLog(javaClass)

    override fun doEvent(arena: Arena) {}

    override fun undoEvent(arena: Arena) {}

}
