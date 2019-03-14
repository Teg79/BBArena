package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.util.Concat

class StartDriveEvent : GameEvent() {

    public override fun doEvent(arena: Arena) {}

    override fun undoEvent(arena: Arena) {}

    override fun getString(): String {
        return Concat.buildLog(javaClass)
    }

    companion object {

        private val serialVersionUID = -1919966604189789283L
    }

}
