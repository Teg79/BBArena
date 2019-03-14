package bbarena.model.event.game

import bbarena.model.Arena
import bbarena.model.util.Concat

class EndDriveEvent(var oldDrive: Int = 0) : GameEvent() {

    override val string: String
        get() = Concat.buildLog(javaClass)

    override fun doEvent(arena: Arena) {
        oldDrive = arena.match?.currentDrive!!
        arena.match.currentDrive = oldDrive + 1
    }

    override fun undoEvent(arena: Arena) {
        arena.match?.currentDrive = oldDrive
    }

}
